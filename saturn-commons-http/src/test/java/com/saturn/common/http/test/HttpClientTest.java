package com.saturn.common.http.test;

import com.saturn.commons.http.HttpClientFactory;
import com.saturn.commons.http.dto.HttpRequest;
import com.saturn.commons.http.dto.HttpRequestBuilder;
import com.saturn.commons.http.dto.HttpResponse;
import com.saturn.commons.http.type.ContentType;
import com.saturn.commons.http.type.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.saturn.commons.http.HttpClient;


/**
 * HTTP Client test
 */
public class HttpClientTest {
    
    /** Logger */
    private static Logger LOG= LogManager.getLogger(HttpClientTest.class);

    /** Endpoint ECHO service */
    private static final String GET_URL="https://postman-echo.com/?source=echo-collection-app-onboarding";
    
    /** Endpoint ECHO service */
    private static final String POST_URL="https://postman-echo.com/post";
    
    
    @Test
    public void runGetTest() throws Exception {

        HttpRequest req= new HttpRequestBuilder()
                .setMethod(RequestMethod.GET)
                .setContentType(ContentType.APPLICATION_JSON)
                .setUrl(GET_URL)
                .addParam("par1", "val1")
                .build();

        HttpClient client= HttpClientFactory.getHTTPClient();
        HttpResponse res=client.send(req);
        Assert.assertTrue("HTTP GET Request failed!",res.isSuccess());
    }

    
    @Test
    public void runPostTest() throws Exception {

        HttpRequest req= new HttpRequestBuilder()
                .setMethod(RequestMethod.POST)
                .setContentType(ContentType.APPLICATION_JSON)
                .setUrl(POST_URL)
                .addParam("par1", "val1")
                .build();

        HttpClient client= HttpClientFactory.getHTTPClient();
        HttpResponse res=client.send(req);
        Assert.assertTrue("HTTP POST Request failed!",res.isSuccess());
    }
    
    
}
