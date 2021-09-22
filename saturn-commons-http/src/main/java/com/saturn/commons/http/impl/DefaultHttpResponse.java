package com.saturn.commons.http.impl;

import com.saturn.commons.http.HttpException;
import com.saturn.commons.http.HttpHeader;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.utils.io.InputStreamUtils;
import com.saturn.commons.utils.io.CloseUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * HTTP Response Bean
 */
public class DefaultHttpResponse implements HttpResponse
{
    /** Logger */
    public static final Logger LOG=LogManager.getLogger(DefaultHttpResponse.class);

    /** HTTP response status */
    private int status;

    /** HTTP respone message */
    private String message;

    /** HTTP content */
    private String content;

    /** Content type */
    private String contentType;

    /** Content Encoding */
    private String contentEncoding;

    /** Content Length */
    private int contentLength;

    /** Response Headers */
    private List<HttpHeader> headers;

    /** Response cookies */
    private Map<String,String> cookies;

    /** Underlying HTTP connection (for retrieving content) */
    private HttpURLConnection con;

    /** Redirect location */
    private String location;



    /**
     * Returns the HTTP response status
     * @return
     */
    @Override
    public int getStatus() {
        return status;
    }


    /**
     * Returns the HTTP response message
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }


    /**
     * Returns the response content if any, <b>NULL</b> otherwise.
     * @return
     */
    @Override
    public synchronized String getContent() {
        if (content==null)
            content=readContent();
        return content;
    }


    /**
     * Tells whether this response object has a content payload.
     * @return
     */
    @Override
    public boolean hasContent() {
        return contentLength !=0;
    }


    /**
     * Returns the content's type
     * @return
     */
    @Override
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content contentEncoding
     * @return
     */
    @Override
    public String getContentEncoding() {
        return contentEncoding;
    }


    /**
     * Returns the content contentLength
     * @return
     */
    @Override
    public int getContentLength() {
        return contentLength;
    }


    /**
     * Is HTTP response status between 200 and 299
     * @return
     */
    @Override
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }


    /**
     * Returns response headers
     * @return
     */
    @Override
    public List<HttpHeader> getHeaders() {
        return headers;
    }


    /**
     * Sets the HTTP response status
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Sets the HTTP response message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Sets response content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public Map<String, String> getCookies() {
        return cookies;
    }



    /**
     * Sets the connection instance
     * @param con
     */
    public void setConnection(HttpURLConnection con) {
        this.con = con;
    }



    @Override
    public InputStream getContentStream() throws IOException {
        InputStream in=null;

        if (con!=null) {
            // GZip content?
            if (isSuccess()) {
                in= "gzip".equalsIgnoreCase(getContentEncoding()) ? new GZIPInputStream(con.getInputStream()) : con.getInputStream();
            } else {
                in= con.getErrorStream();
            }
        }

        return in;
    }



    /**
     * Returns the HTTP content response
     * @return
     * @throws HttpException
     */
    private String readContent() {
        String body="";

        if (con!=null) {
            InputStream in=null;
            try {
                in= getContentStream();
                if (in!=null) {
                    body= InputStreamUtils.readAsString(in,getContentLength());
                }
            } catch (Exception e) {
                // Consume error stream to reuse connection
                in= con.getErrorStream();
                if (!hasContent()) {
                    body= InputStreamUtils.readAsString(in);
                }

            } finally {
                CloseUtil.close(in);
            }
        }
        return body;
    }


    @Override
    public boolean isRedirect() {
        return status >= 300 && status <= 308;
    }

    @Override
    public String getLocation() {
        return location;
    }



    @Override
    public String toString() {
        return "HTTP[" + status + "," + message + "] TYPE["+ contentType +"] LENGHT["+contentLength+"] CONTENT["+content+"] ENCODING["+contentEncoding+']';
    }




}
