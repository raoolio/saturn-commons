package com.saturn.common.http.test;

import com.saturn.common.http.HTTPClient;
import com.saturn.common.http.HTTPClientFactory;
import com.saturn.common.http.dto.HTTPRequest;
import com.saturn.common.http.dto.HTTPRequestBuilder;
import com.saturn.common.http.dto.HTTPResponse;
import com.saturn.common.http.type.ContentType;
import com.saturn.common.http.type.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


/**
 * HTTP Client test
 */
public class HTTPClientTest {
    
    /** Logger */
    private static Logger LOG= LogManager.getLogger(HTTPClientTest.class);

    /** Endpoint ECHO service */
    private static final String GET_URL="https://postman-echo.com/?source=echo-collection-app-onboarding";
    
    /** Endpoint ECHO service */
    private static final String POST_URL="https://postman-echo.com/post";
    
    
    @Test
    public void runGetTest() throws Exception {

        HTTPRequest req= new HTTPRequestBuilder()
                .setMethod(RequestMethod.GET)
                .setContentType(ContentType.APPLICATION_JSON)
                .setUrl(GET_URL)
                .addParam("par1", "val1")
                .build();

        HTTPClient client= HTTPClientFactory.getHTTPClient();
        HTTPResponse res=client.send(req);
    }

    
    @Test
    public void runPostTest() throws Exception {

        HTTPRequest req= new HTTPRequestBuilder()
                .setMethod(RequestMethod.POST)
                .setContentType(ContentType.APPLICATION_JSON)
                .setUrl(POST_URL)
                .addParam("par1", "val1")
                .build();

        HTTPClient client= HTTPClientFactory.getHTTPClient();
        HTTPResponse res=client.send(req);
    }
    
    
}
