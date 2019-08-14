package com.saturn.commons.http.impl;

import com.saturn.commons.http.HttpHeader;
import com.saturn.commons.http.HttpResponse;
import java.util.List;


/**
 * HTTP Response Bean
 */
public class DefaultHttpResponse implements HttpResponse
{
    /** HTTP response status */
    private int status;

    /** HTTP respone message */
    private String message;

    /** HTTP content */
    private String content;

    /** Content type */
    private String contentType;

    /** Content Encoding */
    private String contentEncoding;

    /** Content Length */
    private int contentLength;

    /** Response Headers */
    private List<HttpHeader> headers;



    /**
     * Returns the HTTP response status
     * @return
     */
    @Override
    public int getStatus() {
        return status;
    }


    /**
     * Returns the HTTP response message
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }


    /**
     * Returns the response content if any, <b>NULL</b> otherwise.
     * @return
     */
    @Override
    public String getContent() {
        return content;
    }


    /**
     * Tells whether this response object has a content payload.
     * @return
     */
    @Override
    public boolean hasContent() {
        return content!=null && content.length()>0;
    }


    /**
     * Returns the content's type
     * @return
     */
    @Override
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content contentEncoding
     * @return
     */
    @Override
    public String getContentEncoding() {
        return contentEncoding;
    }


    /**
     * Returns the content contentLength
     * @return
     */
    @Override
    public int getContentLength() {
        return contentLength;
    }


    /**
     * Is HTTP response status between 200 and 299
     * @return
     */
    @Override
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }


    /**
     * Returns response headers
     * @return
     */
    @Override
    public List<HttpHeader> getHeaders() {
        return headers;
    }


    /**
     * Sets the HTTP response status
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Sets the HTTP response message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Sets response content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }


    @Override
    public String toString() {
        return "HTTP[" + status + "," + message + "] TYPE["+ contentType +"] LENGHT["+contentLength+"] CONTENT["+content+"] ENCODING["+contentEncoding+']';
    }

}
