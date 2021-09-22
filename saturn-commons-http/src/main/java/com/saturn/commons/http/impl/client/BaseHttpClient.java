package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpClient;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.utils.time.TimeUtils;
import java.io.Closeable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * HTTP Client helper methods
 * @author rdelcid
 */
public abstract class BaseHttpClient implements HttpClient {

    /** Logger */
    public static final Logger LOG=LogManager.getLogger(HttpClient.class);

    /** Default User-Agent string */
    protected static final String USER_AGENT_PREFIX="saturn-commons-http-1.6";



    /**
     * Relocate to given destination URL ?
     * @param req Http request instance
     * @param url Destination URL
     * @return <b>True</b> if URL was relocated. <b>False</b> otherwise.
     */
    private boolean relocate(HttpRequest req,String url) {
        boolean done=false;
        if (StringUtils.isNotBlank(url)) {
            LOG.warn("Target moved TO["+url+"]! requesting...");
            req.setUrl(url);
            done=true;
        }
        return done;
    }



    /**
     * Perform the HTTP send
     * @param req HTTP send bean
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse send(HttpRequest req) throws Exception {
        HttpResponse res=null;
        long t=System.nanoTime();
        Validate.notNull(req, "Request can't be null");
        int redirects=5;

        do {
            res= sendRequest(req);
        } while (req.isFollowRedirects() && res.isRedirect() && --redirects>=0 && relocate(req,res.getLocation()) );

        t=System.nanoTime()-t;
        LOG.info("{}[{}] HEADERS{} CONT[{}] -> RESP[{}] in {}",req.getMethod(),req.getUrl(),req.getHeaders(),req.getContent(),res,TimeUtils.nanoToString(t));

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
