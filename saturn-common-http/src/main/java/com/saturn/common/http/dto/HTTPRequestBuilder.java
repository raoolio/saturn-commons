package com.saturn.common.http.dto;

import com.saturn.common.http.type.ContentType;
import com.saturn.common.http.type.RequestMethod;
import com.saturn.common.http.util.HTTPParamUtil;
import com.saturn.common.http.util.HTTPRequestUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.Validate;


/**
 * HTTPRequest Builder helper
 */
public class HTTPRequestBuilder {

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

    /** Header list */
    private List<Header> headers;

    /** Header list */
    private Map<String,Object> params;

    /** Parameters name's total length */
    private int paramsLength;

    /** Send unmatched parameters in request? */
    private boolean sendAllParams;



    /**
     * Constructor
     */
    public HTTPRequestBuilder() {
        this.headers= Collections.EMPTY_LIST;
        this.params= Collections.EMPTY_MAP;
        this.sendAllParams= true;
    }



    /**
     * Sets the destination URL for the request
     * @param url Destination URL
     * @return
     */
    public HTTPRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }


    /**
     * Sets the HTTP Request Method
     * @param method
     * @return
     */
    public HTTPRequestBuilder setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }


    /**
     * Sets the HTTP request content
     * @param content
     * @return
     */
    public HTTPRequestBuilder setContent(String content) {
        this.content = content;
        return this;
    }


    /**
     * Set the content's type
     * @param contentType
     * @return
     */
    public HTTPRequestBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }


    /**
     * Sets the content's charset
     * @param contentCharset
     * @return
     */
    public HTTPRequestBuilder setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
        return this;
    }



    /**
     * Sets the TCP request timeout
     * @param timeout Timeout in seconds
     * @return
     */
    public HTTPRequestBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }



    /**
     * Send unmatched parameters in request ? (default is TRUE)
     * @param sendAllParams
     * @return
     */
    public HTTPRequestBuilder setSendAllParams(boolean sendAllParams) {
        this.sendAllParams = sendAllParams;
        return this;
    }



    /**
     * Sets the BASIC authentication credentials
     * @param user User
     * @param pass Password
     * @return
     */
    public HTTPRequestBuilder setBasicCredentials(String user,String pass) {
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
    public HTTPRequestBuilder setBasicAuth(String basicAuth) {
        addHeader("Authorization",basicAuth);
        return this;
    }



    /**
     * Adds the given header parameter to the request
     * @param id Header ID
     * @param value Header value
     * @return
     */
    public HTTPRequestBuilder addHeader(String id,String value) {
        // Lazy initialization...
        if (Collections.EMPTY_LIST.equals(headers))
            headers= new LinkedList();

        headers.add(new Header(id,value));
        return this;
    }



    /**
     * Add the given paramater to the request
     * @param id Parameter ID
     * @param value Parameter value
     * @return
     */
    public HTTPRequestBuilder addParam(String id,Object value) {
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
    public HTTPRequestBuilder addParamAll(Map pars) {
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
                HTTPParamUtil.replaceAndCopy($url,url,params,contentCharset);
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
                HTTPParamUtil.params2UrlEncoded($url,params,contentCharset);
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
            if (content!=null && contentType!=null && content.indexOf('{')>0) {
                // Replace content's variables with provided params
                return HTTPParamUtil.replaceAndCopy(content,params);
            }
            else
                return HTTPParamUtil.encodeParams(params,contentType,contentCharset).toString();
        } else
            return content;
    }



    /**
     * Prepare the content
     * @return
     */
    public HTTPRequest build() throws Exception {

        // Validate input parameters
        Validate.notBlank(url, "Invalid request URL");
        Validate.notNull(method,"Invalid request method");
        if (method==RequestMethod.POST)
            Validate.notNull(contentType,"ContentType can't be null when method is POST");

        // Content Charset...
        if (contentCharset==null) {
            contentCharset= contentType!=null? contentType.getCharset() : "UTF-8";
        }

        // Create HTTPRequest bean
        HTTPRequest req= new HTTPRequest();
        req.setMethod(method);
        req.setUrl(buildURL());
        req.setHeaders(headers);
        req.setContent(buildContent());
        req.setContentType(contentType);
        req.setContentCharset(contentCharset);
        req.setTimeout(timeout);

        return req;
    }






}
