package com.saturn.common.http.util;

import com.saturn.common.http.dto.HTTPRequest;
import com.saturn.common.http.type.RequestMethod;
import org.apache.commons.lang3.StringUtils;


/**
 * HTTPRequest helper methods
 */
public class HTTPRequestUtils {

    private HTTPRequestUtils() {
    }



    /**
     * Tells if the given request has content.
     * @param r HTTP request bean
     * @return
     */
    public static boolean hasContent(HTTPRequest r) {
        return StringUtils.isNotEmpty(r.getContent());
    }


    /**
     * Tells if the content has parameter place holders
     * @param r HTTP request bean
     * @return
     */
    public static boolean contentHasParams(HTTPRequest r) {
        String c= r.getContent();
        return !StringUtils.isEmpty(c)? c.contains("{") : false;
    }


    /**
     * Tells if the request contains headers
     * @param r HTTP request bean
     * @return
     */
    public static boolean hasHeaders(HTTPRequest r) {
        return !r.getHeaders().isEmpty();
    }


    /**
     * Tells if given request's method is POST
     * @param r HTTP request bean
     * @return
     */
    public static boolean isPostMethod(HTTPRequest r) {
        return RequestMethod.POST== r.getMethod();
    }


    /**
     * Tells if given request's method is GET
     * @param r HTTP request bean
     * @return
     */
    public static boolean isGetMethod(HTTPRequest r) {
        return RequestMethod.GET== r.getMethod();
    }


}
