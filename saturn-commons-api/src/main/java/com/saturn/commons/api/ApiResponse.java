package com.saturn.commons.api;


/**
 * API Response object
 */
public class ApiResponse {

    /** HTTP response status */
    private int status;

    /** HTTP response content */
    private Object content;



    /**
     * Constructor
     */
    public ApiResponse() {
    }



    /**
     * Constructor
     * @param status
     * @param content
     */
    public ApiResponse(int status, Object content) {
        this.status = status;
        this.content = content;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public Object getContent() {
        return content;
    }


    /**
     * Tells if the request was successful, HTTP status between 200 and 299
     * @return
     */
    public boolean isSuccess() {
        return status>=200 && status<300;
    }


    @Override
    public String toString() {
        return "ApiResponse: STATUS[" + status + "] CONTENT[" + content + ']';
    }

}
