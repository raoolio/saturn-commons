package com.saturn.commons.utils;

import java.math.BigInteger;


/**
 * Method for converting numbers between bases 10 and 36
 * @author raoolio
 */
public class NumericBaseUtils {

    /**
     * No instance constructor
     */
    private NumericBaseUtils() {
    }



    /**
     * Converts one number from one numeric base representation to another one.
     * @param num Number to convert
     * @param inBase Input numeric base corresponding to given number
     * @param outBase Output numeric base for desired conversion.
     * @return
     */
    public static String convert(String num, int inBase, int outBase) {
        String cnv= "";
        try {
            BigInteger bi = new BigInteger(num, inBase);
            cnv= bi.toString(outBase);
        } catch (Exception e) {
        }
        return cnv;
    }



}
