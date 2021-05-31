package com.saturn.commons.utils.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Date generating utils
 * @author raoolio
 */
public class DateUtils {

    /** Stadard time format */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");



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



    /**
     * Converts given date string in standard format to a Date instance object
     * @param date Date string in <b>YYYY-MM-DD</b> format
     * @return
     */
    public static Date stringToDate(String date) {
        Date d=null;
        if (date != null) {
            synchronized(DATE_FORMAT) {
                try {
                    d= DATE_FORMAT.parse(date);
                } catch (Exception e) {
                }
            }
        }
        return d;
    }

}
