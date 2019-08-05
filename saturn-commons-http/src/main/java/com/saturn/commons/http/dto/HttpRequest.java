package com.saturn.commons.http.dto;

import com.saturn.commons.http.type.ContentType;
import com.saturn.commons.http.type.RequestMethod;
import java.util.List;


/**
 * HTTP Request Bean
 * @author rdelcid
 */
public class HttpRequest
{
    /** HTTP request method */
    private RequestMethod method;

    /** Destination URL */
    private String url;

    /** Content to send */
    private String content;

    /** Content type */
    private ContentType contentType;

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
    public HttpRequest() {
    }


    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getContentCharset() {
        return contentCharset;
    }

    public int getTimeout() {
        return timeout;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public boolean isFetchHeaders() {
        return fetchHeaders;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setMethod(RequestMethod method) {
        this.method = method;
    }

    void setContent(String content) {
        this.content = content;
    }

    void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    void setHeaders(List<HttpHeader> headers) {
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
