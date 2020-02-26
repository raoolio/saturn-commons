package com.saturn.commons.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;


/**
 * ApiResponse Builder
 * @author raoolio
 */
public class ApiResponseBuilder {

    /** HTTP Status code */
    private int status;

    /** HTTP content body (optional) */
    private Object content;

    /** JSON params */
    private Map params;



    /**
     * Successful response
     */
    public ApiResponseBuilder() {
        this(200);
    }


    /**
     * Instance with given response code
     * @param status HTTP status code
     */
    public ApiResponseBuilder(int status) {
        this.status = status;
    }


    public ApiResponseBuilder setStatus(int status) {
        this.status = status;
        return this;
    }

    public ApiResponseBuilder setContent(Object content) {
        this.content = content;
        return this;
    }

    public ApiResponseBuilder addParam(String id,Object value) {
        if (params==null)
            params= new HashMap();

        params.put(id, value);
        return this;
    }



    public ApiResponse build() {
        ApiResponse ar= new ApiResponse();
        Validate.isTrue(status>=100 && status<600, "Invalid HTTP status code");
        Validate.isTrue(content!=null || params!=null, "Invalid content");

        ar.setStatus(status);

        if (content!=null) {
            ar.setContent(content);
        } else {

            if (params!=null) {
                try {
                    ObjectMapper om= new ObjectMapper();
                    ar.setContent(om.writeValueAsString(params));
                } catch (Exception e) {
                }
            }
        }

        return ar;
    }


}
