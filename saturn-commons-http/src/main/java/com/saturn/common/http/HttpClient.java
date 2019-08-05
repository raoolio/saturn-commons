package com.saturn.common.http;

import com.saturn.common.http.dto.HttpRequest;
import com.saturn.common.http.dto.HttpResponse;


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
