package com.saturn.commons.http;

import com.saturn.commons.http.dto.HttpRequest;
import com.saturn.commons.http.dto.HttpResponse;


/**
 * HTTP Client interface
 * @author rdelcid
 */
public interface HttpClient {


    /**
     * Performs the given HTTP send
     * @param req HTTP send bean
     * @return
     * @throws java.lang.Exception
     */
    public HttpResponse send(HttpRequest req) throws Exception;

}
