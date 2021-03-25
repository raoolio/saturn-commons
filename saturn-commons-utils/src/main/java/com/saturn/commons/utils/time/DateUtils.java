package com.saturn.commons.utils.time;

import java.util.Calendar;

/**
 * Date generating utils
 * @author raoolio
 */
public class DateUtils {


    /**
     * No instance constructor
     */
    private DateUtils() {
    }



    /**
     * Converts the given int to 2-digit string
     * @param v
     * @return
     */
    private static String twoDigitStr(int v) {
        String s=String.valueOf(v);
        return v<10? "0".concat(s) : s;
    }



    /**
     * Returns current date as String YYYY-MM-DD
     * @return
     */
    public static String getCurrentDate() {
        Calendar now= Calendar.getInstance();
        return Integer.toString(now.get(Calendar.YEAR))
                + '-' + twoDigitStr(1+now.get(Calendar.MONTH))
                + '-' + twoDigitStr(now.get(Calendar.DATE));
    }

}
