package com.saturn.commons.http.impl;

import com.saturn.commons.http.*;
import com.saturn.commons.http.HttpContentType;
import com.saturn.commons.http.HttpRequestMethod;
import java.util.List;


/**
 * HTTP Request Bean
 * @author rdelcid
 */
public class DefaultHttpRequest implements HttpRequest
{
    /** HTTP request method */
    private HttpRequestMethod method;

    /** Destination URL */
    private String url;

    /** Content to send */
    private String content;

    /** Content type */
    private HttpContentType contentType;

    /** Content charset */
    private String contentCharset;

    /** TCP request timeout */
    private int timeout;

    /** HttpHeader list */
    private List<HttpHeader> headers;

    /** Fetch HTTP response headers? */
    private boolean fetchHeaders;



    /**
     * Constructor
     */
    public DefaultHttpRequest() {
    }


    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public HttpContentType getContentType() {
        return contentType;
    }

    public String getContentCharset() {
        return contentCharset;
    }

    public int getTimeout() {
        return timeout;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public boolean isFetchHeaders() {
        return fetchHeaders;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(HttpRequestMethod method) {
        this.method = method;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(HttpContentType contentType) {
        this.contentType = contentType;
    }

    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public void setFetchHeaders(boolean fetchHeaders) {
        this.fetchHeaders = fetchHeaders;
    }


    @Override
    public String toString() {
        return "HTTPRequest{" + "method=" + method + ", url=" + url + ", content=" + getContent() + ", contentType=" + contentType + ", contentCharset=" + contentCharset + ", timeout=" + timeout + ", headers=" + headers + '}';
    }


}
