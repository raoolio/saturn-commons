package com.saturn.common.http.test;

import com.saturn.commons.http.util.HttpParamUtil;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


/**
 * HTTPParamUtil test
 */
public class HttpParamUtilTest {

    @Test
    public void runTest1() {
        Map pars= new HashMap();

        // Plain id replacement
        pars.put("a", "test1");
        String res= HttpParamUtil.replaceAndCopy("1{a}2", pars);
        Assert.assertEquals("Plain id replacement failed", "1test12", res);

        // Default id replacement
        pars.put("a", "test1");
        res= HttpParamUtil.replaceAndCopy("1{a:3}2", pars);
        Assert.assertEquals("Default id replacement failed", "1st12", res);

        // Left id replacement
        pars.put("a", "test1");
        res= HttpParamUtil.replaceAndCopy("1{a:L3}2", pars);
        Assert.assertEquals("Left id replacement failed", "1tes2", res);

        // Right id replacement
        pars.put("a", "test1");
        res= HttpParamUtil.replaceAndCopy("1{a:R4}2", pars);
        Assert.assertEquals("Right id replacement failed", "1est12", res);

        // No value id replacement
        pars.put("a", "test1");
        res= HttpParamUtil.replaceAndCopy("1{b}2", pars);
        Assert.assertEquals("No value id replacement failed", "1{b}2", res);
    }

    
    @Test
    public void runTest2() {
        Map pars= new HashMap();
        pars.put("id", 5);
        
        String res= HttpParamUtil.replaceAndCopy("http://www.ejemplo.com/{id}", pars);
        Assert.assertEquals("URL replacement failed", "http://www.ejemplo.com/5", res);
    }
    
    
}
