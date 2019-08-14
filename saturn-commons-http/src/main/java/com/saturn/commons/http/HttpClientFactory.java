package com.saturn.commons.http;

import com.saturn.commons.http.impl.client.ApacheHttpClient;
import com.saturn.commons.http.impl.client.NativeHttpClient;


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
     * Returns a native HttpClient instance
     * @return
     */
    public static final HttpClient getHTTPClient() {
        return getHTTPClient(HttpClientType.NATIVE);
    }



    /**
     * Returns HttpClient instance
     * @param type HttpClientType value
     * @return
     */
    public static final HttpClient getHTTPClient(HttpClientType type) {
        return type==HttpClientType.NATIVE? new NativeHttpClient() : new ApacheHttpClient();
    }




}
