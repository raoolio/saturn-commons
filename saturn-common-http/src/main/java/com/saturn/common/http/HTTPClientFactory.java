package com.saturn.common.http;

import com.saturn.common.http.impl.HTTPClientNative;


/**
 * HTTPClient Factory
 */
public class HTTPClientFactory {


    /**
     * No instance constructor
     */
    private HTTPClientFactory() {
    }



    /**
     * Returns HTTPClient instance
     * @return
     */
    public static final HTTPClient getHTTPClient() {
        return new HTTPClientNative();
    }


}
