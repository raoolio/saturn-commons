package com.saturn.common.http;

import com.saturn.common.http.impl.NativeHttpClient;


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
