package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpException;
import com.saturn.commons.http.HttpHeader;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.impl.DefaultHttpHeader;
import com.saturn.commons.http.impl.DefaultHttpResponse;
import com.saturn.commons.utils.io.InputStreamUtils;
import com.saturn.commons.http.util.SSLUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;



/**
 * Java URLConnection based HTTP Client implementation
 * @author rdelcid
 */
public class NativeHttpClient extends BaseHttpClient
{


    /**
     * Tries to fetch the HTTP response code
     * @param con HTTP connection object
     * @param httpResp Response bean
     * @return <b>True</b> if response retrieved, false otherwise.
     */
    private boolean fetchResponse(HttpURLConnection con,DefaultHttpResponse httpResp) {
        boolean done=false;
        try {
            httpResp.setStatus(con.getResponseCode());
            httpResp.setMessage(con.getResponseMessage());
            done=true;
        } catch (IOException e) {
            LOG.warn("Error reading HTTP response code, parsing from Exception [{}]",e);
            String error=e.getMessage();
            int ci=error.indexOf("code: ");

            if (ci>0) {
                ci+=6;
                int ce= error.indexOf(" ", ci);
                httpResp.setStatus(Integer.parseInt( error.substring(ci,ce) ));
            }
        }
        return done;
    }



    /**
     * Retrieve response headers
     * @param req Request instance
     * @param con Connection instance
     */
    private List fetchHeaders(HttpRequest req,HttpURLConnection con) {
        List hl= Collections.EMPTY_LIST;

        if (req.isFetchHeaders()) {
            // Retrieve HTTP response headers
            Map<String,List<String>> m= con.getHeaderFields();

            // Has headers?
            if (m!=null && !m.isEmpty()) {
                hl= new ArrayList(m.size());
                Iterator<String> it= m.keySet().iterator();
                while (it.hasNext()) {
                    String id= it.next();
                    if (!"set-cookie".equalsIgnoreCase(id)) {
                        List<String> values= m.get(id);
                        hl.add(new DefaultHttpHeader(id,values) );
                    }
                }
            }
        }

        return hl;
    }



    /**
     * Retrieve the cookies from the http response
     * @param con Connection instance
     * @return
     */
    private Map<String, String> fetchCookies(HttpURLConnection con) {
        Map cm= Collections.EMPTY_MAP;

        // Retrieve HTTP response headers
        Map<String,List<String>> m= con.getHeaderFields();

        // Has headers?
        if (m!=null && !m.isEmpty()) {
            List<String> l= m.get("Set-Cookie");

            if (l!=null && !l.isEmpty()) {
                cm= new LinkedHashMap();

                for (String c:l) {
                    String[] cArry= c.split(";");
                    String[] idVal= cArry.length>0? cArry[0].split("=") : new String[0];
                    if (idVal.length==2) {
                       cm.put(idVal[0], idVal[1]);
                    }
                }
            }
        }

        return cm;
    }



    @Override
    public HttpResponse sendRequest(HttpRequest req) throws HttpException {

        DefaultHttpResponse res= new DefaultHttpResponse();
        HttpURLConnection con=null;
        InputStream in=null;

        try {
            if (req.isSkipCertValidation())
                SSLUtil.registerTrustingSSLManager();

            URL link= new URL(req.getUrl());
            con= (HttpURLConnection) link.openConnection();
            con.setRequestMethod(req.getMethod().name());
            con.setUseCaches(false);
            con.setInstanceFollowRedirects(false);

            // Set TCP timeouts
            int milis=1000*req.getTimeout();
            con.setConnectTimeout(milis);
            con.setReadTimeout(milis);

            //<editor-fold defaultstate="collapsed" desc=" Set Headers ">
            con.setRequestProperty("User-Agent", USER_AGENT_PREFIX+" Native URLConnection");
            con.setRequestProperty("Accept-Encoding", "gzip");

            // Set additional headers ?
            Collection<HttpHeader> reqHeaders= req.getHeaders();
            if (!reqHeaders.isEmpty()) {
                for (HttpHeader h: reqHeaders) {
                    con.setRequestProperty(h.getId(), String.join(", ", h.getValues()) );
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc=" Set Content ">
            if (StringUtils.isNotEmpty(req.getContent())) {

                // Content header is added in HttpRequestBuilder (2020-09-21)
//                HttpContentType contentType= req.getContentType();
//                con.setRequestProperty("Content-Type", contentType.getType()+"; charset="+req.getContentCharset());

                con.setDoOutput(true);
                OutputStream os= con.getOutputStream();

                // Encode content
                byte[] toSend= req.getContent().getBytes( req.getContentCharset() );
                os.write(toSend);
                os.flush();
                close(os);
            }
            //</editor-fold>

            // execute request!
            con.connect();

            // Fetch response headers?
            res.setHeaders( fetchHeaders(req,con) );

            // Fetch cookies
            res.setCookies( fetchCookies(con) );

            // Retrieve response code & message
            boolean retrieved= fetchResponse(con,res);

            // Fetch redirect location?
            if (res.isRedirect()) {
                String loc=con.getHeaderField("Location");
//                if (loc==null)
//                    loc=con.getHeaderField("Location");

                res.setLocation(loc);
            }

            res.setContentLength(con.getContentLength());
            res.setContentType(con.getHeaderField("Content-Type"));
            res.setContentEncoding(con.getHeaderField("Content-Encoding"));

            if (res.getContentLength() != 0) {
                res.setConnection(con);
            }

        } catch (Exception ex) {
            // Consume error stream to reuse connection
            if (con!=null) {
                in= con.getErrorStream();
                if (!res.hasContent()) {
                    res.setContent(InputStreamUtils.readAsString(in) );
                }
            }
            throw new HttpException(ex);
        } finally {
            close(in);
        }

        return res;
    }



}
