package com.saturn.commons.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Utility methods for converting date and time objects to string representation
 */
public class DateTimeUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");


    private DateTimeUtils() {
    }



    public static String date2String(Date date) {
        String s="null";
        if (date != null) {
            synchronized(DATE_FORMAT) {
                s= DATE_FORMAT.format(date);
            }
        }
        return s;
    }


    public static String datetime2String(Date date) {
        String s="null";
        if (date != null) {
            synchronized(TIMESTAMP_FORMAT) {
                s= TIMESTAMP_FORMAT.format(date);
            }
        }
        return s;
    }



    public static String time2String(Date date) {
        String s="null";
        if (date != null) {
            synchronized(TIME_FORMAT) {
                s= TIME_FORMAT.format(date);
            }
        }
        return s;
    }

}
