package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.utils.TimeUtils;
import java.io.Closeable;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.saturn.commons.http.HttpClient;
import org.apache.commons.lang3.Validate;


/**
 * HTTP Client helper methods
 * @author rdelcid
 */
public abstract class BaseHttpClient implements HttpClient {

    /** Logger */
    protected static Logger LOG=LogManager.getLogger(HttpClient.class);



    /**
     * Perform the HTTP send
     * @param req HTTP send bean
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse send(HttpRequest req) throws Exception {
        long t=System.nanoTime();

        Validate.notNull(req, "Request bean can't be null");
        HttpResponse res= sendRequest(req);

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
    public abstract HttpResponse sendRequest(HttpRequest req) throws Exception;



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
