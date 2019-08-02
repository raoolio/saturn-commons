package com.saturn.common.http;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.StringUtils;


/**
 * HTTP Request Bean
 * @author rdelcid
 */
public class HTTPRequest
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

    /** Header list */
    private List<Header> headers;

    /** Header list */
    private Map<String,Object> params;

    /** Parameters name's total length */
    private int paramsLength;

    /** Send unmatched parameters in request? */
    private boolean sendUnmatchedPars;

    /** Is content built ? */
    private boolean contentBuilt;


    /**
     * Constructor
     */
    public HTTPRequest() {
        this.sendUnmatchedPars = true;
    }


    /**
     * Sets the destination URL for the request
     * @param url Destination URL
     * @return
     */
    public HTTPRequest setUrl(String url) {
        this.url = url;
        return this;
    }


    /**
     * Sets the HTTP Request Method
     * @param method
     * @return
     */
    public HTTPRequest setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }


    /**
     * Sets the BASIC authentication credentials
     * @param user User
     * @param pass Password
     * @return
     */
    public HTTPRequest setBasicCredentials(String user,String pass) {
        String auth= (user+":"+pass);
        String base64$= DatatypeConverter.printBase64Binary(auth.getBytes());
        return setBasicAuth("Basic ".concat(base64$));
    }


    /**
     * Sets the BASIC authentication string
     * @param basicAuth Authentication string to set
     * @return
     */
    public HTTPRequest setBasicAuth(String basicAuth) {
        return addHeader("Authorization",basicAuth);
    }



    /**
     * Sets the HTTP request content
     * @param content
     * @return
     */
    public HTTPRequest setContent(String content) {
        this.content = content;
        return this;
    }


    /**
     * Set the content's type
     * @param contentType
     * @return
     */
    public HTTPRequest setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }


    /**
     * Sets the content's charset
     * @param contentCharset
     * @return
     */
    public HTTPRequest setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
        return this;
    }


    /**
     * Sets the TCP request timeout
     * @param timeout Timeout in seconds
     * @return
     */
    public HTTPRequest setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    /**
     * Adds the given header parameter to the request
     * @param id Header ID
     * @param value Header value
     * @return
     */
    public HTTPRequest addHeader(String id,String value) {
        // Lazy initialization...
        if (headers==null)
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
    public HTTPRequest addParam(String id,Object value) {
        // Lazy initialization...
        if (params==null)
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
    public HTTPRequest addParamAll(Map pars) {
        Iterator it= pars.keySet().iterator();
        while (it.hasNext()) {
            Object id= it.next();
                Object val= pars.get(id);
                addParam(String.valueOf(id),val);
            }
        return this;
    }



    /**
     * Send unmatched parameters in request ? (default is TRUE)
     * @param sendRemainingPars
     * @return
     */
    public HTTPRequest setSendRemainingPars(boolean sendRemainingPars) {
        this.sendUnmatchedPars = sendRemainingPars;
        return this;
    }



    public String getUrl() {
        return url;
    }


    public String getContent() {
        if (!contentBuilt) {
            buildContent();
        }

        return content;
    }

    public ContentType getContentType() {
        if (contentType==null && !StringUtils.isEmpty(content))
            contentType=guessType(content);
        return contentType;
    }

    public String getContentCharset() {
        if (contentCharset==null) {
            return contentType==null? "UTF-8" : contentType.getCharset();
        } else
            return contentCharset;
    }

    public int getTimeout() {
        return timeout;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public boolean isSendUnmatchedPars() {
        return sendUnmatchedPars;
    }



    /**
     * Prepare the content
     * @return
     */
    private void buildContent() {

        try {
            // Send parameters via POST ?
            if (isSendUnmatchedPars() && hasParams() && isPostMethod()) {

                // Already has content?
                if (content!=null && contentType!=null && contentHasParams()) {
                    // Replace content's variables with provided params
                    content=HTTPParamUtil.replaceAndCopy(this.content,getParams());
                }
                else
                    content=HTTPParamUtil.encodeParams(this).toString();
            }
            contentBuilt=true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //<editor-fold defaultstate="collapsed" desc=" Package-access methods ">

    /**
     * Tells if the request bean has content.
     * @return
     */
    boolean hasContent() {
        buildContent();
        return !StringUtils.isEmpty(content);
    }

    /**
     * Tells if the content has parameter place holders
     * @return
     */
    boolean contentHasParams() {
        return !StringUtils.isEmpty(content)? content.contains("{") : false;
    }

    boolean hasHeaders() {
        return headers!=null;
    }

    boolean hasParams() {
        return params!=null && !params.isEmpty();
    }

    boolean isPostMethod() {
        return RequestMethod.POST==method;
    }

    boolean isGetMethod() {
        return RequestMethod.GET==method;
    }


    List<Header> getHeaders() {
        return headers;
    }

    Map<String,Object> getParams() {
        return params;
    }

    int getParamsLength() {
        return paramsLength;
    }


    /**
     * Tries to guess the content type
     * @param content Content
     * @return
     */
    ContentType guessType(String content) {
        char c= content.charAt(0);
        switch (c) {
            case '{': return ContentType.APPLICATION_JSON;
            case '<': return ContentType.APPLICATION_XML;
            default: return ContentType.TEXT_PLAIN;
        }
    }


    //</editor-fold>


    @Override
    public String toString() {
        return "HTTPRequest{" + "method=" + method + ", url=" + url + ", content=" + getContent() + ", contentType=" + contentType + ", contentCharset=" + contentCharset + ", timeout=" + timeout + ", headers=" + headers + ", params=" + params + '}';
    }




}
