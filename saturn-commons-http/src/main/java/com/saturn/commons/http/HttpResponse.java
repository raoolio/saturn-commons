package com.saturn.commons.http;

import java.io.IOException;
import java.util.List;
import java.io.InputStream;
import java.util.Map;


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
     * Returns the response content as a String if any, <b>NULL</b> otherwise.
     * @return
     */
    public String getContent();


    /**
     * Returns an InputStream for reading raw bytes
     * @return
     */
    public InputStream getContentStream() throws IOException;


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
    public int getContentLength();


    /**
     * Returns response headers
     * @return
     */
    public List<HttpHeader> getHeaders();


    /**
     * Returns the cookies from the response as a Map.
     * @return
     */
    public Map<String,String> getCookies();


    /**
     * Tells if response is a redirect
     * @return
     */
    public boolean isRedirect();


    /**
     * Ger redirect location
     * @return
     */
    public String getLocation();

}
