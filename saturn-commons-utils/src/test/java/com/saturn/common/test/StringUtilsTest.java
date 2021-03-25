package com.saturn.common.test;

import com.saturn.commons.utils.string.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * String test
 */
public class StringUtilsTest {
    
    
    @Test
    public void parentTest() {
        String s="/request-limiter/sendpinbr/cwp_PA/143/";
        
        String p= StringUtils.getParent(s, '/');
        Assert.assertEquals("First parent failed", "/request-limiter/sendpinbr/cwp_PA/", p);
        
        p= StringUtils.getParent(p, '/');
        Assert.assertEquals("Second parent failed", "/request-limiter/sendpinbr/", p);
        
        p= StringUtils.getParent(p, '/');
        Assert.assertEquals("Third parent failed", "/request-limiter/", p);
        
        p= StringUtils.getParent(p, '/');
        Assert.assertEquals("Fourth parent failed", "", p);
        
    }
}
