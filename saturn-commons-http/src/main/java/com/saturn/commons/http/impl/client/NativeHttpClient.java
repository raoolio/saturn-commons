package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpException;
import com.saturn.commons.http.HttpHeader;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.impl.DefaultHttpHeader;
import com.saturn.commons.http.impl.DefaultHttpResponse;
import com.saturn.commons.http.util.HttpHeaderUtil;
import com.saturn.commons.http.util.SSLUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.commons.lang3.StringUtils;



/**
 * Java URLConnection based HTTP Client implementation
 * @author rdelcid
 */
public class NativeHttpClient extends BaseHttpClient
{
    /** Content buffer size */
    private static final int BUFFER_SIZE=2048;






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
     * @param con
     * @param res
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
                    List<String> values= m.get(id);
                    hl.add(new DefaultHttpHeader(id,values) );
                }
            }
        }

        return hl;
    }



    /**
     * Reads content from given input stream
     * @param in InputStream instance
     * @return
     */
    private String readContent(InputStream in) {
        return readContent(in,0);
    }



    /**
     * Reads content from given input stream
     * @param in InputStream instance
     * @param size Content length
     * @return
     */
    private String readContent(InputStream in,int size) {
        StringBuilder bf=new StringBuilder(size>0? size : 100);
        byte[] array=new byte[BUFFER_SIZE];
        int bytes=-1;

        try {
            while ( (bytes=in.read(array)) > 0 ) {
                for (int i=0;i<bytes;i++) {
                    bf.append((char)array[i]);
                }
            }
        } catch (IOException e) {
            LOG.warn("Error reading InputStream -> "+e.getMessage());
        }

        return bf.toString();
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

            // Set TCP timeouts
            int milis=1000*req.getTimeout();
            con.setConnectTimeout(milis);
            con.setReadTimeout(milis);

            //<editor-fold defaultstate="collapsed" desc=" Set Headers ">
            con.setRequestProperty("User-Agent", USER_AGENT_PREFIX+" Native URLConnection");
            con.setRequestProperty("Accept-Encoding", "gzip");

            // Set additional headers ?
            List<HttpHeader> reqHeaders= req.getHeaders();
            if (!reqHeaders.isEmpty()) {
                for (HttpHeader h: reqHeaders) {
                    con.setRequestProperty(h.getId(), HttpHeaderUtil.valuesToString(h));
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

            // Retrieve response code & message
            boolean retrieved= fetchResponse(con,res);
            int cntLen=con.getContentLength();
            res.setContentLength(cntLen);

            if (cntLen!=0) {
                //<editor-fold defaultstate="collapsed" desc=" Read response content ">

                // GZip content?
                String encode= con.getHeaderField("Content-Encoding");
                res.setContentEncoding(encode);
                res.setContentType(con.getHeaderField("Content-Type"));

                if (retrieved && res.isSuccess()) {
                    in= "gzip".equalsIgnoreCase(encode) ? new GZIPInputStream(con.getInputStream()) : con.getInputStream();
                } else {
                    in= con.getErrorStream();
                }

                if (in!=null) {
                    String resCont= readContent(in,cntLen);
                    res.setContent( resCont );
                }
                //</editor-fold>
            } else
                res.setContent("");

        } catch (Exception ex) {
            // Consume error stream to reuse connection
            if (con!=null) {
                in= con.getErrorStream();
                if (!res.hasContent()) {
                    res.setContent( readContent(in) );
                }
            }
            throw new HttpException(ex);
        } finally {
            close(in);
        }

        return res;
    }


}
