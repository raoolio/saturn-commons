package com.saturn.common.test;

import com.saturn.commons.utils.NumericBaseUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 * String test
 */
public class NumericBaseTest {


    @Test
    public void runTest() {

        String msisdn="50600119502";

        // Test all numerica bases
        for (int b=2; b<=36; b++) {
            String conv=  NumericBaseUtils.convert(msisdn, 10, b);
            String rollback= NumericBaseUtils.convert(conv, b, 10);
            System.out.println("Base["+b+"] -> "+conv);
            Assert.assertEquals("Base["+b+"] conversion failed",msisdn, rollback);
        }
    }
}
