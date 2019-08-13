package com.saturn.commons.http;

import java.util.List;


/**
 * HTTP Response Bean
 */
public interface HttpResponse
{

    /**
     * Checks if the HTTP response status is between 200 and 299
     * @return
     */
    public boolean isSuccess();


    /**
     * Returns the HTTP response code
     * @return
     */
    public int getStatus();



    /**
     * Returns the HTTP response message
     * @return
     */
    public String getMessage();


    /**
     * Tells whether this response object has a content payload.
     * @return
     */
    public boolean hasContent();


    /**
     * Returns the response content if any, <b>NULL</b> otherwise.
     * @return
     */
    public String getContent();


    /**
     * Returns the content's type
     * @return
     */
    public String getContentType();


    /**
     * Returns the content encoding
     * @return
     */
    public String getContentEncoding();


    /**
     * Returns the content length
     * @return
     */
    public int getLength();


    /**
     * Returns response headers
     * @return
     */
    public List<HttpHeader> getHeaders();


}
