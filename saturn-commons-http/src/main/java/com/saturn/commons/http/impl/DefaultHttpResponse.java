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

    /** Content type */
    private String contentType;

    /** HTTP content */
    private String content;

    /** Content encoding */
    private String encoding;

    /** Content length */
    private int length;

    /** Response Headers */
    private List<HttpHeader> headers;



    /**
     * Returns the HTTP response status
     * @return
     */
    public int getStatus() {
        return status;
    }


    /**
     * Returns the HTTP response message
     * @return
     */
    public String getMessage() {
        return message;
    }


    /**
     * Returns the response content if any, <b>NULL</b> otherwise.
     * @return
     */
    public String getContent() {
        return content;
    }


    /**
     * Tells whether this response object has a content payload.
     * @return
     */
    public boolean hasContent() {
        return content!=null && content.length()>0;
    }


    /**
     * Returns the content's type
     * @return
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content encoding
     * @return
     */
    public String getContentEncoding() {
        return encoding;
    }


    /**
     * Returns the content length
     * @return
     */
    public int getLength() {
        return length;
    }


    /**
     * Is HTTP response status between 200 and 299
     * @return
     */
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }


    /**
     * Returns response headers
     * @return
     */
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

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }


    @Override
    public String toString() {
        return "HTTP[" + status + "," + message + "] TYPE["+ contentType +"] LENGHT["+length+"] CONTENT["+content+"] ENCODING["+encoding+']';
    }

}
