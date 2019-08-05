package com.saturn.common.http.impl;


import com.saturn.common.http.HttpException;
import com.saturn.common.http.type.ContentType;
import com.saturn.common.http.dto.HttpHeader;
import com.saturn.common.http.dto.HttpResponse;
import com.saturn.common.http.dto.HttpRequest;
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


    /**
     * Retrieve response headers
     * @param con
     * @param res
     */
    private void fetchHeaders(HttpRequest req,HttpURLConnection con,HttpResponse res) {
        List headers= Collections.EMPTY_LIST;

        if (req.isFetchHeaders()) {
            // Retrieve HTTP response headers
            Map<String,List<String>> m= con.getHeaderFields();

            // Has headers?
            if (m!=null && !m.isEmpty()) {
                headers= new ArrayList(m.size());
                Iterator<String> it= m.keySet().iterator();
                while (it.hasNext()) {
                    String id= it.next();
                    List<String> values= m.get(id);
                    headers.add(new HttpHeader(id,values) );
                }
            }
        }

        res.setHeaders(headers);
    }




    @Override
    public HttpResponse sendRequest(HttpRequest req) throws HttpException {

        HttpResponse httpResp= new HttpResponse();
        HttpURLConnection con=null;
        InputStream in=null;

        try {
            URL link= new URL(req.getUrl());
            con= (HttpURLConnection) link.openConnection();
            con.setRequestMethod(req.getMethod().name());
            con.setUseCaches(false);

            // Set TCP timeouts
            int milis=1000*req.getTimeout();
            con.setConnectTimeout(milis);
            con.setReadTimeout(milis);

            //<editor-fold defaultstate="collapsed" desc=" Set Headers ">
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:59.0) Gecko/20100101 Firefox/59.0");
            con.setRequestProperty("Accept-Encoding", "gzip");

            // Set additional headers ?
            List<HttpHeader> reqHeaders= req.getHeaders();
            if (!reqHeaders.isEmpty()) {
                for (HttpHeader h: reqHeaders) {
                    con.setRequestProperty(h.getId(), h.getValues().toString());
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc=" Set Content ">
            if (StringUtils.isNotEmpty(req.getContent())) {
                ContentType contentType= req.getContentType();
                //                con.setRequestProperty("Content-Type", contentType.getType().concat("; ").concat(req.getContentCharset()));
                con.setRequestProperty("Content-Type", contentType.getType()+"; charset="+req.getContentCharset());
                con.setDoOutput(true);
                OutputStream os= con.getOutputStream();

                // Encode content
                byte[] toSend= req.getContent().getBytes( req.getContentCharset() );
                os.write(toSend);
                os.flush();
                close(os);
            }
            //</editor-fold>

            con.connect();

            // Fetch response headers?
            fetchHeaders(req,con,httpResp);

            // Retrieve response code & message
            boolean retrieved= fetchResponse(con,httpResp);
            int cntLen=con.getContentLength();
            httpResp.setLength(cntLen);

//            if (LOG.isTraceEnabled())
//                LOG.trace("HTTP Content-Length: "+cntLen);

            if (cntLen!=0) {
                //<editor-fold defaultstate="collapsed" desc=" Read response content ">
                //                System.out.println(StringUtils.map2PrettyString(con.getHeaderFields(), "HEADER FIELDS"));

                StringBuilder buff=new StringBuilder(cntLen>0? cntLen : 100);

                // GZip content?
                String encode= con.getHeaderField("Content-Encoding");
                httpResp.setEncoding(encode);
                httpResp.setContentType(con.getHeaderField("Content-Type"));

                if (retrieved && httpResp.isSuccess()) {
                    in= "gzip".equalsIgnoreCase(encode) ? new GZIPInputStream(con.getInputStream()) : con.getInputStream();
                } else {
                    in= con.getErrorStream();
                }

                byte[] byteArr=new byte[2048];
                int bytes=-1;

                if (in!=null) {
                    while ((bytes=in.read(byteArr) )>0) {
                        for (int i=0;i<bytes;i++) {
                            buff.append((char)byteArr[i]);
                        }
                    }
                }

                httpResp.setContent( buff.toString() );
                //</editor-fold>
            } else
                httpResp.setContent("");

        } catch (Exception ex) {
            // Consume error stream to reuse connection
            if (con!=null) {
                InputStream es= con.getErrorStream();
                close(es);
            }
            throw new HttpException(ex);
        } finally {
            close(in);
        }

        return httpResp;
    }



    /**
     * Tries to fetch the HTTP response code
     * @param con HTTP connection object
     * @param httpResp Response bean
     * @return <b>True</b> if response retrieved, false otherwise.
     */
    private boolean fetchResponse(HttpURLConnection con,HttpResponse httpResp) {
        boolean done=false;
        try {
            httpResp.setCode(con.getResponseCode());
            httpResp.setMessage(con.getResponseMessage());
            done=true;
        } catch (IOException e) {
            LOG.warn("Error reading HTTP response code, parsing from Exception [{}]",e);
            String error=e.getMessage();
            int ci=error.indexOf("code: ");

            if (ci>0) {
                ci+=6;
                int ce= error.indexOf(" ", ci);
                httpResp.setCode(Integer.parseInt( error.substring(ci,ce) ));
            }
        }
        return done;
    }
}
