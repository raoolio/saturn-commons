package com.saturn.common.test;

import com.saturn.commons.utils.NumericBaseUtils;
import java.math.BigInteger;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 * Numeric conversion test
 */
public class NumericBaseTest {


    /**
     * Tests numeric bases between 2 and 36
     */
    @Test
    public void runNormalTest() {

        String msisdn=RandomStringUtils.randomNumeric(11);
        System.out.println("Hashing value["+msisdn+"]");

        // Test all numeric bases
        for (int radix=2; radix<=36; radix++) {
            String conv=  NumericBaseUtils.convert(msisdn, 10, radix);
            String rollback= NumericBaseUtils.convert(conv, radix, 10);
            System.out.println("Base["+radix+"] -> "+conv);
            Assert.assertEquals("Base["+radix+"] conversion failed", new BigInteger(msisdn), new BigInteger(rollback));
        }
    }



    /**
     * Tests numeric bases between 37 and 62
     */
    @Test
    public void runExtendedTest() {

        String msisdn=RandomStringUtils.randomNumeric(11);
        System.out.println("Hashing value["+msisdn+"]");

        // Test extended numeric bases
        for (int radix=37; radix<=62; radix++) {
            String hashed=  NumericBaseUtils.convert(msisdn,10, radix);
            String rollback= NumericBaseUtils.convert(hashed, radix, 10);
            System.out.println("ExBase["+radix+"] -> "+hashed);
            Assert.assertEquals("ExBase["+radix+"] conversion failed", new BigInteger(msisdn), new BigInteger(rollback));
        }
    }



    /**
     * Tests numeric conversion using a custom charset table
     */
    @Test
    public void runCustomTest() {

        String customCharset= com.saturn.commons.utils.string.StringUtils.shuffle(NumericBaseUtils.DEFAULT_CHARSET);
        System.out.println("Using charset["+customCharset+"]");

        String msisdn=RandomStringUtils.randomNumeric(11);
        System.out.println("Hashing value["+msisdn+"]");

        // Test all numeric bases
        for (int radix=2; radix<=62; radix++) {
            String hashed=  NumericBaseUtils.toBaseN(msisdn, radix, customCharset);
            String rollback= NumericBaseUtils.toDecimal(customCharset,hashed, radix);
            System.out.println("CstBase["+radix+"] -> "+hashed);
            Assert.assertEquals("CstBase["+radix+"] conversion failed", new BigInteger(msisdn), new BigInteger(rollback));
        }
    }



    public static void main(String[] args) {

        String susc= "404";
        int len= susc.length();
        String msisdn= RandomStringUtils.randomNumeric(11);
        String usrInfo= len+susc+msisdn;
        String hash= NumericBaseUtils.convert(usrInfo, 10, 36);
        System.out.println("SUSC["+susc+"] MSISDN["+msisdn+"] -> ["+hash+"]");

        String decode= NumericBaseUtils.convert(hash, 36, 10);
        System.out.println("Decoded -> ["+decode+"]");

    }
}
