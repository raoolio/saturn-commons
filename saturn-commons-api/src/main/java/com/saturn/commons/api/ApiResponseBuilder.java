package com.saturn.commons.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;


/**
 * ApiResponse Builder
 * @author raoolio
 */
public class ApiResponseBuilder {

    private int status;
    private String content;

    /** Fields for building JSON content */
    private String code;
    private String message;
    private String moreInfo;



    /**
     * No instance
     */
    public ApiResponseBuilder(int status) {
        this.status = status;
    }


    public ApiResponseBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public ApiResponseBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public ApiResponseBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponseBuilder setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
        return this;
    }



    public ApiResponse build() {
        ApiResponse ar= new ApiResponse();
        Validate.isTrue(status>=100 && status<600, "Invalid HTTP status code");
        Validate.isTrue(!(StringUtils.isEmpty(content) && StringUtils.isEmpty(message)), "Invalid content");

        ar.setStatus(status);

        if (!StringUtils.isEmpty(content)) {
            ar.setContent(content);
        } else {
            StringBuilder json= new StringBuilder("{");

            if ( !StringUtils.isEmpty(code) ) {
                json.append("\"errorCode\":\"").append(code).append('"');
            }

            if ( !StringUtils.isEmpty(message) ) {
                if (json.length()>1)
                    json.append(", ");
                json.append("\"message\":\"").append(message).append('"');
            }

            if ( !StringUtils.isEmpty(moreInfo) ) {
                if (json.length()>1)
                    json.append(", ");
                json.append("\"moreInfo\":\"").append(moreInfo).append('"');
            }

            json.append("}");
            ar.setContent(json.toString());
        }

        return ar;
    }


}
