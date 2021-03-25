package com.saturn.common.http.test.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saturn.commons.http.HttpContentType;
import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpRequestBuilder;
import com.saturn.commons.http.HttpRequestMethod;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


/**
 * HTTP Request Builder Testing
 */
public class HttpRequestBuilderTest {


    @Test
    public void postJsonTest() throws Exception {
        HttpRequest req= new HttpRequestBuilder()
                .setRequestMethod(HttpRequestMethod.POST)
                .setContentType(HttpContentType.APPLICATION_JSON)
                .setUrl("http://localhost:45/{name}/{id}")
                .addParam("name", "junior")
                .addParam("id", 10)
                .addParam("name1", "val1")
                .addParam("name2", "val2")
                .build();

        // URL validation
        String url= "http://localhost:45/junior/10";
        Assert.assertEquals("URL params failed", url, req.getUrl());

        // Content validation
        ObjectMapper om= new ObjectMapper();
        Map actual= om.readValue(req.getContent(), Map.class);

        Map expected= new HashMap();
        expected.put("name1", "val1");
        expected.put("name2", "val2");
        Assert.assertEquals("JSON content formation failed", expected, actual);
    }



    @Test
    public void postFormParamTest() throws Exception {
        HttpRequest req= new HttpRequestBuilder()
                .setRequestMethod(HttpRequestMethod.POST)
                .setContentType(HttpContentType.APPLICATION_FORM_URLENCODED)
                .setUrl("http://localhost:45/{name}/{id}")
                .addParam("name", "junior")
                .addParam("id", 10)
                .addParam("name1", "val1")
                .addParam("name2", "val2")
                .build();

        // URL validation
        String url= "http://localhost:45/junior/10";
        Assert.assertEquals("URL params failed", url, req.getUrl());

        // Content validation
        String content= "name1=val1&name2=val2";
        Assert.assertEquals("Form Url-Encoded params failed", content, req.getContent());
    }



}
