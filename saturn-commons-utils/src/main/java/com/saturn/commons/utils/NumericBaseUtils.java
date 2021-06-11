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
        String out= "";

        if (inRadix <= 36 && outRadix <= 36) {
            BigInteger bi = new BigInteger(num, inRadix);
            out= bi.toString(outRadix);
        } else {
            String dec= toDecimal(num,inRadix);
            out= toBaseN(dec,outRadix);
        }

        return out;
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

        // Charset present
        if (charset==null || charset.trim().length()==0)
            throw new IllegalArgumentException("Invalid charset");

        // Valid decimal number?
        if (!decimalNumber.matches("[0-9]+"))
            throw new NumberFormatException("Invalid decimal number");

        // Radix ok?
        if (radix<2 || radix>charset.length())
            throw new NumberFormatException("Radix out of range");

        StringBuilder out= new StringBuilder();
        BigInteger quotient = new BigInteger(decimalNumber);
        BigInteger bigRadix= new BigInteger(String.valueOf(radix));

        while (quotient.intValue() != 0) {
            BigInteger remainder = quotient.mod(bigRadix);
            int pos=  remainder.intValue();
            out.insert(0, charset.charAt(pos) );
            quotient= quotient.subtract(remainder).divide(bigRadix);
        }

        return out.toString();
    }



    /**
     * Returns the given char's position
     * @param s String where to look
     * @param c Character to look
     * @return
     */
    private static int getCharPos(String s,char c) {
        int p= s.indexOf(c);
        if (c==-1)
            throw new IllegalArgumentException("Invalid character");
        return p;
    }



    /**
     * Converts the number in given radix to it's decimal representation
     * @param number Source number
     * @param radix Numerical base of number
     * @return
     */
    public static final String toDecimal(String number,int radix) {
        return toDecimal(DEFAULT_CHARSET,number,radix);
    }



    /**
     * Converts the number in given radix to it's decimal representation
     * @param charset
     * @param number Source number
     * @param radix Numerical base of number
     * @return
     */
    public static final String toDecimal(String charset, String number,int radix) {
        // Charset present?
        if (charset==null || charset.trim().length()==0)
            throw new IllegalArgumentException("Invalid charset");

        // Number present?
        if (number==null || number.trim().length()==0)
            throw new NumberFormatException("Invalid number");

        // Radix ok?
        if (radix<2 || radix>charset.length())
            throw new NumberFormatException("Radix out of range");

        BigInteger bigRadix= new BigInteger(String.valueOf(radix));
        BigInteger res= new BigInteger("0");
        int digit=0;

        for (int i=number.length(); --i>=0 ;) {
            char c= number.charAt(i);
            int charPos= getCharPos(charset,c);
            BigInteger decimalVal= BigInteger.valueOf(charPos);
            decimalVal= decimalVal.multiply( bigRadix.pow(digit++) );
            res= res.add(decimalVal);
        }

        return res.toString();
    }


}
