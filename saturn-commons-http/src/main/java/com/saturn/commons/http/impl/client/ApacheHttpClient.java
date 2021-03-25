package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpHeader;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.impl.DefaultHttpHeader;
import com.saturn.commons.http.impl.DefaultHttpResponse;
import com.saturn.commons.http.util.HttpHeaderUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.pool.BasicConnFactory;
import org.apache.http.impl.pool.BasicConnPool;
import org.apache.http.impl.pool.BasicPoolEntry;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;



/**
 * Apache HTTP Client implementation
 */
public class ApacheHttpClient extends BaseHttpClient {

    /** Connection pool */
    private static final BasicConnPool pool;

    // Init connection pool
    static {
        pool= new BasicConnPool(new BasicConnFactory());
        pool.setDefaultMaxPerRoute(2);
        pool.setMaxTotal(20);
        pool.setValidateAfterInactivity(60);
    }



    /**
     * Retrieve response headers
     * @param con
     * @param res
     */
    private List fetchHeaders(HttpRequest req, org.apache.http.HttpResponse httpResp) {
        List hl= Collections.EMPTY_LIST;

        if (req.isFetchHeaders()) {
            // Retrieve HTTP response headers
            Header[] headers= httpResp.getAllHeaders();

            // Has headers?
            if (headers!=null && headers.length>0) {
                hl= new ArrayList(headers.length);

                for (Header h: headers) {
                    hl.add(new DefaultHttpHeader(h.getName(),h.getValue()) );
                }
            }
        }

        return hl;
    }


    /**
     * Retrieves the given header's value
     * @param h Header instance
     * @return
     */
    private String getHeaderValue(Header h) {
        return h==null? null : h.getValue();
    }



    @Override
    public HttpResponse sendRequest(HttpRequest req) throws Exception {

        DefaultHttpResponse resp = new DefaultHttpResponse();

        // Prepare Apache HttpRequest...
        HttpProcessor httpProc = HttpProcessorBuilder.create()
                .add(new RequestContent())
                .add(new RequestTargetHost())
                .add(new RequestConnControl())
                .add(new RequestUserAgent(USER_AGENT_PREFIX+" Apache Core"))
                .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor executor = new HttpRequestExecutor();

        ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
        BasicPoolEntry poolEntry=null;
        boolean reuse = false;

        try {
            // Get URL
            URL url = new URL(req.getUrl());
            HttpHost host = new HttpHost(url.getHost(), url.getPort());

            // Get connection from pool...
            Future<BasicPoolEntry> future = pool.lease(host, null);
            poolEntry = future.get();
            HttpClientConnection conn = poolEntry.getConnection();
            HttpCoreContext coreContext = HttpCoreContext.create();
            coreContext.setTargetHost(host);

            conn.setSocketTimeout(1000*req.getTimeout());

            /**
             * Retrieve path http[s]://<domain>:<port>/<path>
             */
            int pathPos = req.getUrl().indexOf('/', 8);
            String path= pathPos<0? "" : req.getUrl().substring(pathPos);

            BasicHttpRequest httpReq;

            // Set content ?
            if (StringUtils.isEmpty(req.getContent())) {
                httpReq = new BasicHttpRequest(req.getMethod().name(), path);
            } else {
                BasicHttpEntityEnclosingRequest enReq = new BasicHttpEntityEnclosingRequest(req.getMethod().name(), path);
                StringEntity strContent= new StringEntity(req.getContent(), ContentType.create(req.getContentType().getType(), req.getContentCharset()));
                enReq.setEntity(strContent);
                httpReq= enReq;
            }

            // Add Headers?
            List<HttpHeader> headers=req.getHeaders();
            for (HttpHeader h: headers) {
                httpReq.addHeader(h.getId(), HttpHeaderUtil.valuesToString(h));
            }

            // Execute request!
            executor.preProcess(httpReq, httpProc, coreContext);
            org.apache.http.HttpResponse httpResp = executor.execute(httpReq, conn, coreContext);
            executor.postProcess(httpResp, httpProc, coreContext);

            // Reuse connection?
            reuse = connStrategy.keepAlive(httpResp, coreContext);

            // Extract headers?
            resp.setHeaders(fetchHeaders(req,httpResp));

            // Extract Status code & message
            StatusLine sl = httpResp.getStatusLine();
            resp.setStatus(sl.getStatusCode());
            resp.setMessage(sl.getReasonPhrase());

            // Extract Content
            HttpEntity respCont=httpResp.getEntity();
            String content = EntityUtils.toString(respCont);
            resp.setContent(content);
            resp.setContentLength(content==null? 0 : content.length());
            resp.setContentType(getHeaderValue(respCont.getContentType()));
            resp.setContentEncoding(getHeaderValue(respCont.getContentEncoding()));

        } finally {
            // Return connection
            if (poolEntry!=null) {
                pool.release(poolEntry, reuse);
            }
        }

        return resp;
    }

}
