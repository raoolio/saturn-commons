package com.saturn.common.http.impl;


import com.saturn.common.http.HTTPClient;
import com.saturn.common.http.dto.HTTPResponse;
import com.saturn.common.http.dto.HTTPRequest;
import com.saturn.common.utils.TimeUtils;
import java.io.Closeable;
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
     * Perform the HTTP send
     * @param req HTTP send bean
     * @return
     * @throws Exception
     */
    @Override
    public HTTPResponse send(HTTPRequest req) throws Exception {
        long t=System.nanoTime();

        if (req==null)
            throw new IllegalArgumentException("Request bean can't be null");

        HTTPResponse res= sendRequest(req);

        t=System.nanoTime()-t;
        LOG.info("{}[{}] HEADERS[{}] CONT[{}] -> RESP[{}] in {}",req.getMethod(),req.getUrl(),req.getHeaders(),req.getContent(),res,TimeUtils.nanoToString(t));

        return res;
    }



    /**
     * Perform the actual HTTP Request method
     * @param req HTTP send bean
     * @return
     * @throws Exception
     */
    public abstract HTTPResponse sendRequest(HTTPRequest req) throws Exception;



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
