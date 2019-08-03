package com.saturn.common.http.dto;


/**
 * HTTP Response Bean
 */
public class HTTPResponse
{
    /** HTTP response code */
    private int responseCode;

    /** HTTP respone message */
    private String responseMessage;

    /** Content type */
    private String contentType;

    /** HTTP content */
    private String content;

    /** Content encoding */
    private String encoding;

    /** Content length */
    private int length;


    /**
     * Returns the HTTP response code
     * @return
     */
    public int getResponseCode() {
        return responseCode;
    }


    /**
     * Returns the HTTP response message
     * @return
     */
    public String getResponseMessage() {
        return responseMessage;
    }


    /**
     * Returns the response content if any, <b>NULL</b> otherwise.
     * @return
     */
    public String getContent() {
        return content;
    }


    /**
     * Tells whether this response object has a content payload.
     * @return
     */
    public boolean hasContent() {
        return content!=null && content.length()>0;
    }


    /**
     * Returns the content's type
     * @return
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content encoding
     * @return
     */
    public String getEncoding() {
        return encoding;
    }


    /**
     * Returns the content length
     * @return
     */
    public int getLength() {
        return length;
    }


    /**
     * Is HTTP response code between 200 and 299
     *
     * @return
     */
    public boolean isSuccess() {
        return responseCode >= 200 && responseCode < 300;
    }


    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setLength(int length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "HTTP[" + responseCode + "," + responseMessage + "] TYPE["+ contentType +"] LENGHT["+length+"] CONTENT["+content+"] ENCODING["+encoding+']';
    }

}
