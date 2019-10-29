package com.saturn.commons.api;


/**
 * API Response object
 */
public class ApiResponse {

    /** HTTP response status */
    private int status;

    /** HTTP response content */
    private String content;



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
    public ApiResponse(int status, String content) {
        this.status = status;
        this.content = content;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
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
