package com.saturn.common.http;

import javax.xml.bind.DatatypeConverter;


/**
 * HTTP GET/POST sendRequest handler
 * @author rdelcid
 */
public class HTTPUtil
{



    /**
     * Performs the given HTTP sendRequest via GET
     * @param url Destination URL
     * @param timeout TCP sendRequest timeout (seconds)
     * @return
     * @throws Exception If the sendRequest can't be completed
     * @deprecated replaced by {@link #get(HTTPRequest req)}
     */
    public static HTTPResponse get(String url,int timeout) throws Exception {
        return get(url,null,timeout);
    }



    /**
     * Performs the given HTTP sendRequest via GET
     * @param url Destination URL
     * @param user Authentication user (BASIC)
     * @param pass Authentication pass (BASIC)
     * @param timeout TCP sendRequest timeout (seconds)
     * @return
     * @throws Exception If the sendRequest can't be completed
     * @deprecated replaced by {@link #get(HTTPRequest req)}
     */
    public static HTTPResponse get(String url,String user,String pass,int timeout) throws Exception {
        String userpass = user+":"+pass;
        String base64$ = DatatypeConverter.printBase64Binary(userpass.getBytes());
        String basicAuth = "Basic ".concat(base64$);
        return get(url,basicAuth,timeout);
    }



    /**
     * Performs the given HTTP sendRequest via GET
     * @param url Destination URL
     * @param basicAuth BASIC Authentication string (optional)
     * @param timeout TCP sendRequest timeout (seconds)
     * @return
     * @throws Exception If the sendRequest can't be completed
     * @deprecated replaced by {@link #get(HTTPRequest req)}
     */
    public static HTTPResponse get(String url,String basicAuth,int timeout) throws Exception {

        HTTPRequest req= new HTTPRequest();
        req.setUrl(url);
        req.setBasicAuth(basicAuth);
        req.setTimeout(timeout);

        return get(req);
    }



    /**
     * Performs the given HTTP sendRequest via GET
     * @param req Request bean
     * @return
     * @throws Exception If the sendRequest can't be completed
     */
    public static HTTPResponse get(HTTPRequest req) throws Exception {
        req.setMethod(RequestMethod.GET);
        return sendRequest(req);
    }



    /**
     * Performs the given HTTP sendRequest via POST
     * @param url Destination URL
     * @param basicAuth BASIC Authentication string (optional)
     * @param timeout TCP sendRequest timeout (seconds)
     * @param content Request content (optional)
     * @param type Request content type (optional)
     * @param charset Request content charset (optional)
     * @return
     * @throws Exception If the sendRequest can't be completed
     * @deprecated replaced by {@link #post(HTTPRequest req)}
     */
    public static HTTPResponse post(String url,String basicAuth,String content,String type,String charset,int timeout) throws Exception {

        HTTPRequest req= new HTTPRequest();
        req.setUrl(url);
        req.setBasicAuth(basicAuth);
        req.setContent(content);
        if (type!=null)
            req.setContentType(ContentType.valueOf(type));
        req.setContentCharset(charset);
        req.setTimeout(timeout);

        return post(req);
    }



    /**
     * Performs the given HTTP sendRequest via POST
     * @param req Request bean
     * @return
     * @throws Exception If the sendRequest can't be completed
     */
    public static HTTPResponse post(HTTPRequest req) throws Exception {
        req.setMethod(RequestMethod.POST);
        return sendRequest(req);
    }



    /**
     * Performs the given HTTP Request.
     * @param req Request bean
     * @return
     * @throws Exception If the sendRequest can't be completed
     */
    public static HTTPResponse sendRequest(HTTPRequest req) throws Exception {
        return new HTTPClientNative().request(req);
    }


}
