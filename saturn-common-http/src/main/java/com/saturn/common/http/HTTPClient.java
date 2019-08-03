package com.saturn.common.http;

import com.saturn.common.http.dto.HTTPRequest;
import com.saturn.common.http.dto.HTTPResponse;


/**
 * HTTP Client interface
 * @author rdelcid
 */
public interface HTTPClient {


    /**
     * Performs the given HTTP send
     * @param req HTTP send bean
     * @return
     * @throws java.lang.Exception
     */
    public HTTPResponse send(HTTPRequest req) throws Exception;

}
