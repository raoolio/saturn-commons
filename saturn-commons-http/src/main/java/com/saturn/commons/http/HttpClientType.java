package com.saturn.commons.http;


/**
 * HTTP Client Types
 */
public enum HttpClientType {

    /**
     * HttpClient implementation based on native Java URLConnection
     */
    NATIVE,

    /**
     * HttpClient implementation based on Apache Http Components
     */
    APACHE

}
