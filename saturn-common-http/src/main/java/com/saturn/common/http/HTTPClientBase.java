package com.saturn.common.http;


import com.saturn.common.utils.data.TimeUtils;
import java.io.Closeable;
import java.net.URL;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



/**
 * HTTP Client helper methods
 * @author rdelcid
 */
public abstract class HTTPClientBase implements HTTPClient {

    /** Logger */
    protected static Logger LOG=LogManager.getLogger(HTTPClient.class);



    /**
     * Perform the HTTP request
     * @param req HTTP request bean
     * @return
     * @throws Exception
     */
    @Override
    public HTTPResponse request(HTTPRequest req) throws Exception {
        long t=System.nanoTime();

        if (req==null)
            throw new IllegalArgumentException("Request bean can't be null");
        if (req.getUrl()==null)
            throw new IllegalArgumentException("Request URL can't be null");
        if (req.getMethod()==null)
            throw new IllegalArgumentException("Request method can't be null");

        URL link= buildURL(req);
        HTTPResponse httpResp= sendRequest(link,req);

        t=System.nanoTime()-t;
        LOG.info("{}[{}] HEADERS[{}] CONT[{}] -> RESP[{}] in {}",req.getMethod(),link,req.getHeaders(),req.getContent(),httpResp,TimeUtils.nanoToString(t));

        return httpResp;
    }



    /**
     * Perform the actual HTTP Request method
     * @param link Destination URL
     * @param req HTTP request bean
     * @return
     * @throws Exception
     */
    public abstract HTTPResponse sendRequest(URL link,HTTPRequest req) throws Exception;



    //<editor-fold defaultstate="collapsed" desc=" URL parameter replacement methods ">
    /**
     * Buils the URL according to method and parameters
     * @param req HTTP Request
     * @return
     */
    private URL buildURL(HTTPRequest req) throws Exception {

        // Request parameters?
        if (req.hasParams()) {
            StringBuilder sb= new StringBuilder(150);
            HTTPParamUtil.replaceAndCopy(sb,req);

            // Add params to URL ?
            if (req.isSendUnmatchedPars() && req.hasParams() && req.isGetMethod()) {

                if (sb.charAt(sb.length()-1)!='?')
                    sb.append('?');

                // Encode remaining params as GET
                HTTPParamUtil.params2UrlEncoded(sb,req);
            }
            return new URL( sb.toString() );
        } else
            return new URL(req.getUrl());
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Resource closing method ">
    /**
     * Closes the given resource
     * @param ob
     */
    protected final void close(Closeable ob) {
        if (ob==null) return;
        try {
            ob.close();
        } catch (Exception e) {
        }
    }
    //</editor-fold>


}
