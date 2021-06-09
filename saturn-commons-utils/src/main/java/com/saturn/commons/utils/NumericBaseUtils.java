package com.saturn.commons.utils;

import java.math.BigInteger;


/**
 * Method for converting numbers between bases 10 and 36
 * @author raoolio
 */
public class NumericBaseUtils {

    // Default charset symbols for base conversion.
    public final static String DEFAULT_CHARSET= "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * No instance constructor
     */
    private NumericBaseUtils() {
    }



    /**
     * Converts one number from one numeric base representation to another one.
     * @param num Number to convert
     * @param inRadix Input numeric base corresponding to given number
     * @param outRadix Output numeric base for desired conversion.
     * @return
     */
    public static String convert(String num, int inRadix, int outRadix) {
        String cnv= "";

        if (inRadix <= 36 && outRadix <= 36) {
            try {
                BigInteger bi = new BigInteger(num, inRadix);
                cnv= bi.toString(outRadix);
            } catch (Exception e) {
            }
        } else {
            String decimal= toDecimal(num,inRadix);
            cnv= toBaseN(decimal,outRadix);
        }

        return cnv;
    }



    /**
     * Converts given decimal number to given target numeric base using the class
     * default's charset.
     * @param decimalNumber Decimal number to convert
     * @param radix Target numeric base
     * @return
     */
    public static final String toBaseN(String decimalNumber,int radix) {
        return toBaseN(DEFAULT_CHARSET,decimalNumber,radix);
    }



    /**
     * Converts given decimal number to given target numeric base using the class
     * default's charset.
     * @param charset Character set for encoding the number
     * @param decimalNumber Decimal number to convert
     * @param radix Target numeric base
     * @return
     */
    public static final String toBaseN(String charset, String decimalNumber, int radix) {

        // Valid decimal number?
        if (!decimalNumber.matches("[0-9]+"))
            throw new NumberFormatException("Invalid decimal number");

        // Radix ok?
        if (radix<2 || radix>charset.length())
            throw new NumberFormatException("Radix out of range");

        StringBuilder num= new StringBuilder();
        BigInteger quotient = new BigInteger(decimalNumber);
        BigInteger bigRadix= new BigInteger(String.valueOf(radix));

        while (quotient.intValue() != 0) {
            BigInteger remainder = quotient.mod(bigRadix);
            int pos=  remainder.intValue();
            num.insert(0, charset.charAt(pos) );
            quotient= quotient.subtract(remainder).divide(bigRadix);
        }

        return num.toString();
    }



    /**
     * Returns the given char's position
     * @param c Character to look
     * @return
     */
    private static int getCharPos(String charset,char c) {
        for (int i=0; i< charset.length(); i++) {
            if (c== (char)charset.charAt(i)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid character");
    }



    /**
     * Converts the number in given radix to it's decimal representation
     * @param baseNumber Source number
     * @param radix Numerical base of number
     * @return
     */
    public static final String toDecimal(String baseNumber,int radix) {
        return toDecimal(DEFAULT_CHARSET,baseNumber,radix);
    }



    /**
     * Converts the number in given radix to it's decimal representation
     * @param charset
     * @param baseNumber Source number
     * @param radix Numerical base of number
     * @return
     */
    public static final String toDecimal(String charset, String baseNumber,int radix) {
        BigInteger bigRadix= new BigInteger(String.valueOf(radix));
        BigInteger res= new BigInteger("0");
        int digitPos=0;

        for (int i=baseNumber.length(); --i>=0 ;) {
            char c= baseNumber.charAt(i);
            int charPos= getCharPos(charset,c);
            BigInteger decimalVal= BigInteger.valueOf(charPos);
            decimalVal= decimalVal.multiply( bigRadix.pow(digitPos) );
            res= res.add(decimalVal);
            digitPos++;
        }

        return res.toString();
    }




}
