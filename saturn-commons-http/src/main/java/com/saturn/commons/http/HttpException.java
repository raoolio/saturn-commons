package com.saturn.commons.http;


/**
 * HTTP Exception
 * @author rdelcid
 */
public class HttpException extends Exception {

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }



}
