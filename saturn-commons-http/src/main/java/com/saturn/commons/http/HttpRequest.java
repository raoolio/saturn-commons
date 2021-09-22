package com.saturn.commons.http;

import java.util.Collection;


/**
 * HTTP Request Bean
 * @author rdelcid
 */
public interface HttpRequest
{

    /**
     * Returns the URL endpoint
     * @return
     */
    public String getUrl();


    /**
     * Sets the request URL
     * @param url
     */
    public void setUrl(String url);


    /**
     * Returns the request method
     * @return
     */
    public HttpRequestMethod getMethod();


    /**
     * Returns the content
     * @return
     */
    public String getContent();


    /**
     * Returns the content type
     * @return
     */
    public HttpContentType getContentType();


    /**
     * Returns the content charset
     * @return
     */
    public String getContentCharset();


    /**
     * Returns the request timeout (seconds)
     * @return
     */
    public int getTimeout();


    /**
     * Returns the list of HTTP Headers
     * @return
     */
    public Collection<HttpHeader> getHeaders();


    /**
     * Fetch the response HTTP Headers?
     * @return
     */
    public boolean isFetchHeaders();


    /**
     * Skip SSL validation?
     * @return
     */
    public boolean isSkipCertValidation();


    /**
     * Follow redirect responses ?
     * @return
     */
    public boolean isFollowRedirects();

}
