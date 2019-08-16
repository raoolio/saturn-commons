package com.saturn.common.http.test.client;

import com.saturn.commons.http.HttpClientFactory;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpRequestBuilder;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.HttpContentType;
import com.saturn.commons.http.HttpRequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.saturn.commons.http.HttpClient;
import com.saturn.commons.http.HttpClientType;


/**
 * HTTP Client test
 */
public abstract class HttpClientTest {

    /** Logger */
    private static Logger LOG= LogManager.getLogger(HttpClientTest.class);

    /** Endpoint ECHO service */
    private static final String GET_URL="https://postman-echo.com/get";

    /** Endpoint ECHO service */
    private static final String POST_URL="https://postman-echo.com/post";

    /** HttpClientType */
    private HttpClientType clientType;



    /**
     * Constructor
     * @param clientType
     */
    public HttpClientTest(HttpClientType clientType) {
        this.clientType = clientType;
    }



    @Test
    public void runGetTest() throws Exception {

        HttpRequest req= new HttpRequestBuilder()
                .setRequestMethod(HttpRequestMethod.GET)
                .setContentType(HttpContentType.APPLICATION_JSON)
                .setUrl(GET_URL)
                .addParam("par1", "val1")
                .build();

        HttpClient client= HttpClientFactory.getHttpClient(clientType);
        HttpResponse res=client.send(req);
        Assert.assertTrue("["+clientType+"] GET Request failed!",res.isSuccess());
    }


    @Test
    public void runPostTest() throws Exception {

        HttpRequest req= new HttpRequestBuilder()
                .setRequestMethod(HttpRequestMethod.POST)
                .setContentType(HttpContentType.APPLICATION_JSON)
                .setUrl(POST_URL)
                .addParam("par1", "val1")
                .build();

        HttpClient client= HttpClientFactory.getHttpClient(clientType);
        HttpResponse res=client.send(req);
        Assert.assertTrue("["+clientType+"] POST Request failed!",res.isSuccess());
    }


}
