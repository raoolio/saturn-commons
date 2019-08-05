package com.saturn.commons.http;

import com.saturn.commons.http.impl.NativeHttpClient;


/**
 * HttpClient Factory
 */
public class HttpClientFactory {


    /**
     * No instance constructor
     */
    private HttpClientFactory() {
    }



    /**
     * Returns HttpClient instance
     * @return
     */
    public static final HttpClient getHTTPClient() {
        return new NativeHttpClient();
    }


}
