package com.saturn.commons.http.dto;

import com.saturn.commons.http.type.ContentType;
import com.saturn.commons.http.type.RequestMethod;
import com.saturn.commons.http.util.HttpParamUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.Validate;


/**
 * HttpRequest Builder helper
 */
public class HttpRequestBuilder {

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

    /** HttpHeader list */
    private Map<String,Object> params;

    /** Parameters name's total length */
    private int paramsLength;

    /** Send unmatched parameters in request? */
    private boolean sendAllParams;

    /** Fetch HTTP Response Headers? */
    private boolean fetchHeaders;



    /**
     * Constructor
     */
    public HttpRequestBuilder() {
        this.headers= Collections.EMPTY_LIST;
        this.params= Collections.EMPTY_MAP;
        this.sendAllParams= true;
    }



    /**
     * Sets the destination URL for the request
     * @param url Destination URL
     * @return
     */
    public HttpRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }


    /**
     * Sets the HTTP Request Method
     * @param method
     * @return
     */
    public HttpRequestBuilder setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }


    /**
     * Sets the HTTP request content
     * @param content
     * @return
     */
    public HttpRequestBuilder setContent(String content) {
        this.content = content;
        return this;
    }


    /**
     * Set the content's type
     * @param contentType
     * @return
     */
    public HttpRequestBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }


    /**
     * Sets the content's charset
     * @param contentCharset
     * @return
     */
    public HttpRequestBuilder setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
        return this;
    }



    /**
     * Sets the TCP request timeout
     * @param timeout Timeout in seconds
     * @return
     */
    public HttpRequestBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }



    /**
     * Send unmatched parameters in request ? (default is TRUE)
     * @param sendAllParams
     * @return
     */
    public HttpRequestBuilder setSendAllParams(boolean sendAllParams) {
        this.sendAllParams = sendAllParams;
        return this;
    }



    /**
     * Fetch HTTP response headers ?
     * @param fetchHeaders
     * @return
     */
    public HttpRequestBuilder setFetchHeaders(boolean fetchHeaders) {
        this.fetchHeaders = fetchHeaders;
        return this;
    }



    /**
     * Sets the BASIC authentication credentials
     * @param user User
     * @param pass Password
     * @return
     */
    public HttpRequestBuilder setBasicCredentials(String user,String pass) {
        String auth= (user+":"+pass);
        String base64$= DatatypeConverter.printBase64Binary(auth.getBytes());
        setBasicAuth("Basic ".concat(base64$));
        return this;
    }



    /**
     * Sets the BASIC authentication string
     * @param basicAuth Authentication string to set
     * @return
     */
    public HttpRequestBuilder setBasicAuth(String basicAuth) {
        addHeader("Authorization",basicAuth);
        return this;
    }



    /**
     * Adds the given header parameter to the request
     * @param id HttpHeader ID
     * @param value HttpHeader value
     * @return
     */
    public HttpRequestBuilder addHeader(String id,String ... value) {
        // Lazy initialization...
        if (Collections.EMPTY_LIST.equals(headers))
            headers= new LinkedList();

        headers.add(new HttpHeader(id,value));
        return this;
    }



    /**
     * Add the given paramater to the request
     * @param id Parameter ID
     * @param value Parameter value
     * @return
     */
    public HttpRequestBuilder addParam(String id,Object value) {
        // Lazy initialization...
        if (Collections.EMPTY_MAP.equals(params))
            params= new LinkedHashMap();

        params.put(id,value);
        paramsLength+= id.length()+2;
        return this;
    }



    /**
     * Add the given paramater list to the request
     * @param pars List of parameters
     * @return
     */
    public HttpRequestBuilder addParamAll(Map pars) {
        Iterator it= pars.keySet().iterator();
        while (it.hasNext()) {
            Object id= it.next();
                Object val= pars.get(id);
                addParam(String.valueOf(id),val);
            }
        return this;
    }



    /**
     * Buils the URL according to method and parameters
     * @param req HTTP Request
     * @return
     */
    private String buildURL() throws Exception {

        // Request parameters?
        if (!params.isEmpty()) {
            StringBuilder $url= new StringBuilder(150);

            // URL contains '{params}' ?
            if (url.indexOf('{')>0) {
                // Replace param values!
                HttpParamUtil.replaceAndCopy($url,url,params,contentCharset);
            } else {
                $url.append(url);
            }

            // Add params to URL ?
            if (sendAllParams && method==RequestMethod.GET && !params.isEmpty()) {
                int p=$url.indexOf("?");
                // url contains '?' char?
                if (p>0) {
                    // Not Last position?
                    if (p!=$url.length()-1)
                        $url.append("&");
                }
                else
                    $url.append('?');

                // Encode remaining params as GET
                HttpParamUtil.params2UrlEncoded($url,params,contentCharset);
            }
            return $url.toString();
        } else
            return url;
    }



    /**
     * Builds the HTTP Content
     * @return
     */
    private String buildContent() throws Exception {

        // Build content!
        if (sendAllParams && method==RequestMethod.POST && !params.isEmpty()) {

            // Already has content?
            if (content!=null && content.indexOf('{')>0) {

                // Replace content's variables with provided params
                return HttpParamUtil.replaceAndCopy(content,params);
            }
            else {
                // Encode all parameters as content
                StringBuilder sb= new StringBuilder(paramsLength*2);
                return HttpParamUtil.encodeParams(sb,params,contentType,contentCharset).toString();
            }
        } else
            return content;
    }



    /**
     * Prepare the content
     * @return
     */
    public HttpRequest build() throws Exception {

        // Validate input parameters
        Validate.notBlank(url, "Invalid request URL");
        Validate.notNull(method,"Invalid request method");
        if (method==RequestMethod.POST)
            Validate.notNull(contentType,"ContentType can't be null when method is POST");

        // Content Charset...
        if (contentCharset==null) {
            contentCharset= contentType!=null? contentType.getCharset() : "UTF-8";
        }

        // Create HttpRequest bean
        HttpRequest req= new HttpRequest();
        req.setMethod(method);
        req.setUrl(buildURL());
        req.setHeaders(headers);
        req.setContent(buildContent());
        req.setContentType(contentType);
        req.setContentCharset(contentCharset);
        req.setTimeout(timeout);
        req.setFetchHeaders(fetchHeaders);

        return req;
    }






}
