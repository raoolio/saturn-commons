package com.saturn.common.http;

/**
 * HTTP Exception
 * @author rdelcid
 */
public class HTTPException extends Exception {

    public HTTPException(String message) {
        super(message);
    }

    public HTTPException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTTPException(Throwable cause) {
        super(cause);
    }
    
    
    
}
