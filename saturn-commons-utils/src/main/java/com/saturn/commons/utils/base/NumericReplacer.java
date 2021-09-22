package com.saturn.commons.utils.base;

import com.saturn.commons.utils.base.NumericBaseUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


/**
 * Numeric Variable replacer
 */
public class NumericReplacer {

    /** Logger */
    private final static Logger LOG = Logger.getLogger(NumericReplacer.class.getName());

    /** Variable name for numeric base conversion radix(2-62) */
    private static final String VAR_RADIX="{celRadix";

    /** Variable name for Msisdn hashed with <Month><Date><MSISDN> */
    private static final String VAR_TIME_RADIX="{celMDRadix";



    /**
     * Numeric base variable replacer, it supports the following variables:<br>
     * <b>{celularHex3}:</b> Number to Hexatridecimal<br>
     * <b>{celRadix2-62}:</b> Number to numeric base 2-62<br>
     * <b>{celMDRadix2-62}:</b> Sames as celRadix but adds Month and Date as prefix to the number before converting it.<br>
     * @param msg Message with supported variables
     * @param number Number to convert
     * @param charset Charset used to make the conversion, if no charset is given, it uses a default one.
     * @return
     */
    public static String replaceVars(String msg,String number,String charset) {

        try {
            // variable de celular en base Hexatrigesimal (base 36) ?
            if (msg.contains("{celularHex3}")) {
                String msisdnHex3= convert(number,36,charset);
                msg = msg.replace("{celularHex3}", msisdnHex3);
            }

            // celular en base numerica?
            int tries=3;
            while (msg.contains(VAR_RADIX) && tries-- > 0) {
                msg= replaceRadixVar(msg,VAR_RADIX,number,charset);
            }

            // celular con fecha y base numerica?
            tries=3;
            while (msg.contains(VAR_TIME_RADIX) && tries-- > 0) {
                SimpleDateFormat sdf= new SimpleDateFormat("MMdd");
                String time= sdf.format(new Date());
                String num= time.concat(number);
                msg= replaceRadixVar(msg,VAR_TIME_RADIX,num,charset);
            }
        } catch (Exception e) {
            LOG.warning("Error replacing vars -> "+e.getMessage());
        }

        return msg;
    }



    /**
     * Converts one number from one numeric base representation to another one.
     *
     * @param decimalNumber Number to convert
     * @param outRadix Output numeric base for desired conversion.
     * @param charset Character set for conversion
     * @return
     */
    public static String convert(String decimalNumber, int outRadix, String charset) {
        String cnv = "";
        try {
            if (charset!=null && charset.length()>0) {
                cnv= NumericBaseUtils.toBaseN(decimalNumber, outRadix, charset);
            } else {
                cnv= NumericBaseUtils.convert(decimalNumber,10,outRadix);
            }
        } catch (Exception e) {
            LOG.warning("Error converting ["+decimalNumber+"] to radix["+outRadix+"] -> "+e.getMessage());
        }
        return cnv;
    }



    /**
     * Replaces the variable that starts with given prefix in the message
     * @param msg Message with variable to replace
     * @param varPrefix Variable prefix
     * @param number Decimal number to transform
     * @param token Token information
     * @return
     */
    private static String replaceRadixVar(String msg, String varPrefix,String number,String charset) {
        int idx= msg.indexOf(varPrefix);
        if (idx >= 0) {

            // Find end char
            int end= msg.indexOf('}',idx);
            if (end>0) {
                String varname= msg.substring(idx, end+1);
                String radix= varname.substring(varPrefix.length(),varname.length()-1);
                String code="";

                // Valid radix?
                if (radix.matches("[0-9]{1,2}")) {
                    code= convert(number, Integer.valueOf(radix), charset);
                }
                msg = msg.replace(varname, code);
            }
        }

        return msg;
    }




}
