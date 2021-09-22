package com.saturn.commons.http;

import com.google.common.net.UrlEscapers;
import com.saturn.commons.http.impl.DefaultHttpHeader;
import com.saturn.commons.http.impl.DefaultHttpRequest;
import com.saturn.commons.http.util.HttpParamUtil;
import com.saturn.commons.utils.time.TimeValue;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;


/**
 * HttpRequest Builder helper
 */
public class HttpRequestBuilder {

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

    /** TCP request timeout (seconds) */
    private int timeout;

    /** HttpHeader list */
    private Map<String,HttpHeader> headers;

    /** HttpHeader list */
    private Map<String,Object> params;

    /** Cookies */
    private Map<String,String> cookies;

    /** Parameters name's total length */
    private int paramsLength;

    /** Send unmatched parameters in request? */
    private boolean sendAllParams;

    /** Fetch HTTP Response Headers? */
    private boolean fetchHeaders;

    /** Skip SSL validation ? */
    private boolean skipCertValidation;

    /** Send params in URL? */
    private boolean sendPostParamsAsGet;

    /** Follow redirects */
    private boolean followRedirects;



    /**
     * Constructor
     */
    public HttpRequestBuilder() {
        this.method= HttpRequestMethod.GET;
        this.headers= new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.cookies= new LinkedHashMap();
        this.params= new LinkedHashMap();
        this.sendAllParams= true;
        this.followRedirects=true;
        this.timeout=30;
    }



    /**
     * Sets the destination URL for the request
     * @param url Destination URL
     * @return
     */
    public HttpRequestBuilder setUrl(String url) {
        Validate.notNull(url,"Invalid URL");
        this.url = url;
        return this;
    }



    /**
     * Sets the HTTP Request Method
     * @param method
     * @return
     */
    public HttpRequestBuilder setRequestMethod(HttpRequestMethod method) {
        Validate.notNull(method,"Invalid request method");
        this.method = method;
        if (method==HttpRequestMethod.HEAD) {
            this.fetchHeaders=true;
        }
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
    public HttpRequestBuilder setContentType(HttpContentType contentType) {
        return setContentType(contentType,contentType.getCharset());
    }



    /**
     * Set the content's type
     * @param contentType
     * @param charset Content charset
     * @return
     */
    public HttpRequestBuilder setContentType(HttpContentType contentType,String charset) {
        this.contentType = contentType;
        return setContentCharset(charset);
    }



    /**
     * Sets the content's charset
     * @param contentCharset
     * @return
     */
    public HttpRequestBuilder setContentCharset(String contentCharset) {
        Validate.notBlank(contentCharset,"Invalid content charset");
        this.contentCharset = contentCharset;
        return this;
    }



    /**
     * Sets the TCP request timeout
     * @param timeValue TimeValue instance
     * @return
     */
    public HttpRequestBuilder setTimeout(TimeValue timeValue) {
        Validate.notNull(timeValue,"Invalid TimeValue");
        return setTimeout((int)timeValue.toSeconds());
    }



    /**
     * Sets the TCP request timeout
     * @param timeout Timeout in seconds
     * @return
     */
    public HttpRequestBuilder setTimeout(int timeout) {
        Validate.isTrue(timeout>0,"Invalid timeout value");
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
     * Send POST parameters as GET in the URL ?
     * @param sendPostParamsAsGet
     * @return
     */
    public HttpRequestBuilder setSendPostParamsAsGet(boolean sendPostParamsAsGet) {
        this.sendPostParamsAsGet = sendPostParamsAsGet;
        return this;
    }


    /**
     * Automatically follow redirects?
     * @param followRedirects
     * @return
     */
    public HttpRequestBuilder setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }



    /**
     * Skip SSL validation?
     * @return
     */
    public boolean isSkipCertValidation() {
        return skipCertValidation;
    }



    /**
     * Set skip SSL validation flag
     * @param skipCertValidation
     * @return
     */
    public HttpRequestBuilder setSkipCertValidation(boolean skipCertValidation) {
        this.skipCertValidation = skipCertValidation;
        return this;
    }



    /**
     * Sets the BASIC authentication credentials
     * @param user User
     * @param pass Password
     * @return
     */
    public HttpRequestBuilder setBasicCredentials(String user,String pass) {
        Validate.notBlank(user,"Invalid user");
        Validate.notBlank(pass,"Invalid password");
        String auth= (user+":"+pass);
        String base64$= Base64.getEncoder().encodeToString(auth.getBytes());
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
        Validate.notBlank(id,"Invalid header id");
        Validate.notNull(value,"Invalid header values");

        HttpHeader header= headers.get(id);
        if (header!=null) {
            header.addValue(value);
        } else {
            headers.put(id,new DefaultHttpHeader(id,value));
        }

        return this;
    }



    /**
     * Adds given header value to the request only if it's NOT already present.
     * @param id HttpHeader ID
     * @param value HttpHeader value
     * @return
     */
    public HttpRequestBuilder addHeaderIfMissing(String id,String ... value) {
        Validate.notBlank(id,"Invalid header id");
        Validate.notNull(value,"Invalid header values");

        if (!headers.containsKey(id)) {
            headers.put(id,new DefaultHttpHeader(id,value));
        }

        return this;
    }



    /**
     * Add User-Agent header string value
     * @param value
     * @return
     */
    public HttpRequestBuilder setUserAgent(String value) {
        return addHeader("User-Agent",value);
    }


    /**
     * Add referer header string value
     * value Referer string value
     * @return
     */
    public HttpRequestBuilder setReferer(String value) {
        return addHeader("Referer",value);
    }



    /**
     * Add given cookie to the request
     * @param id Cookie name
     * @param value Cookie value
     * @return
     */
    public HttpRequestBuilder addCookie(String id,String value) {
        Validate.notBlank(id,"Invalid cookie name");
        Validate.notBlank(value,"Invalid cookie value");
        this.cookies.put(id, value);
        return this;
    }



    /**
     * Adds all cookie entries to this request
     * @param cookies Collection of cookies
     * @return
     */
    public HttpRequestBuilder setCookies(Map cookies) {
        Validate.notNull(cookies,"Invalid cookie collection");
        this.cookies.putAll(cookies);
        return this;
    }



    /**
     * Add the given paramater to the request
     * @param id Parameter ID
     * @param value Parameter value
     * @return
     */
    public HttpRequestBuilder addParam(String id,Object value) {
        Validate.notBlank(id,"Invalid parameter id");
        Object prev= params.put(id,value);
        // Add length only if it's new parameter
        if (prev==null)
            paramsLength+= id.length()+2;
        return this;
    }



    /**
     * Add the given paramater list to the request
     * @param pars List of parameters
     * @return
     */
    public HttpRequestBuilder addParams(Map pars) {
        Validate.notNull(pars,"Invalid parameter map");
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
            String charset= contentCharset==null? "UTF-8" : contentCharset;

            // URL contains '{params}' ?
            if (url.indexOf('{')>0) {
                // Replace param values!
                HttpParamUtil.replaceAndCopy($url,url,params,charset);
            } else {
                $url.append(url);
            }

            // Add params to URL ?
            if (sendAllParams && (sendPostParamsAsGet || method==HttpRequestMethod.GET) && !params.isEmpty()) {
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
                HttpParamUtil.params2Url($url,params);
            }
//            return $url.toString();
            return UrlEscapers.urlFragmentEscaper().escape($url.toString());
        } else
//            return url;
            return UrlEscapers.urlFragmentEscaper().escape(url);
    }



    /**
     * Builds the HTTP Content
     * @return
     */
    private String buildContent() throws Exception {

        String cont= content;

        // Already has content?
        if (StringUtils.isNotBlank(cont)) {

            Validate.notNull(contentType,"Invalid content type");

            // Content has variables?
            if (content.indexOf('{',0)>-1) {
                // Replace content's variables with provided params
                cont= HttpParamUtil.replaceAndCopy(content,params);
            }
        } else if (!sendPostParamsAsGet) {

            // Build content?
            if (!params.isEmpty() && sendAllParams && (method==HttpRequestMethod.POST || method==HttpRequestMethod.PUT)) {
                Validate.notNull(contentType,"Invalid content type");

                // Encode all parameters as content
                StringBuilder sb= new StringBuilder(paramsLength*2);
                cont= HttpParamUtil.encodeParams(sb,params,contentType,contentCharset).toString();
            }
        }

        // Add Content-Type header if missing
        if (!headers.containsKey("Content-Type") && StringUtils.isNotBlank(cont)) {
            String $contType= contentType.getType();

            if (StringUtils.isNotBlank(contentCharset)) {
                $contType += "; charset="+contentCharset;
            }

            addHeader("Content-Type", $contType);
        }

        return cont;
    }



    /**
     * Build request Cookie header field
     * @return
     */
    private void buildCookies() {
        if (cookies!=null && !cookies.isEmpty()) {
            StringBuilder sb= new StringBuilder();
            Iterator<String> it= cookies.keySet().iterator();
            while (it.hasNext()) {
                String id= it.next();
                String val= cookies.get(id);

                if (sb.length()>0)
                    sb.append("; ");

                sb.append(id).append('=').append(val);
            }
            addHeader("Cookie",sb.toString());
        }
    }



    /**
     * Prepare the content
     * @return
     * @throws Exception
     */
    public HttpRequest build() throws Exception {

        // Validate input parameters
        Validate.notBlank(url, "Invalid request URL");

        // Create HttpRequest bean
        DefaultHttpRequest req= new DefaultHttpRequest();
        req.setMethod(method);
        req.setUrl(buildURL());
        buildCookies();
        req.setHeaders(headers.values());
        req.setContent(buildContent());
        req.setContentType(contentType);
        req.setContentCharset(contentCharset);
        req.setTimeout(timeout);
        req.setFetchHeaders(fetchHeaders);
        req.setSkipCertValidation(skipCertValidation);
        req.setFollowRedirects(followRedirects);

        return req;
    }


}
