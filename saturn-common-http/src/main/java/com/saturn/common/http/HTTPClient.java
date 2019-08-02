package com.saturn.common.http;

import com.saturn.common.http.HTTPRequest;
import com.saturn.common.http.HTTPResponse;


/**
 * HTTP Client interface
 * @author rdelcid
 */
public interface HTTPClient {
    
    /**
     * Performs the given HTTP request
     * @param req HTTP request bean
     * @return 
     * @throws java.lang.Exception 
     */
    public HTTPResponse request(HTTPRequest req) throws Exception;
    
}
