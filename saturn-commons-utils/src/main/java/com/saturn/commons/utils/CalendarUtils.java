package com.saturn.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Calendar Utils
 */
public class CalendarUtils {



    /**
     * Returns the current date with time set at midnight
     * @return 
     */
    public static Calendar getMidnightToday() {
        Calendar today= Calendar.getInstance();
        setMidnightTime(today);
        return today;
    }


    /**
     * Returns tomorrow's date with time set at midnight
     * @return 
     */
    public static Calendar getMidnightTomorrow() {
        Calendar date= Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, 1);
        setMidnightTime(date);
        return date;
    }


    
    
    /**
     * Sets the given calendar's time to midnight 00:00:00.000
     * @return 
     */
    public static void setMidnightTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }
    
    

    /**
     *
     * @param beginTime
     * @return
     */
    public static Calendar getStartPeriodNextDay(Calendar beginTime) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        copyTime(beginTime,nextDay);
        return nextDay;
    }

    

    public static boolean isCloseDawn(Calendar startTime, Calendar endTime, Calendar now) {
        int evalStart = now.compareTo(startTime);
        int evalEnd = now.compareTo(endTime);
        return (evalStart <= 0 && evalEnd < 0);
    }
    
    

    public static boolean isCloseNightfall(Calendar startTime, Calendar endTime, Calendar now) {
        int evalStart = now.compareTo(startTime);
        int evalEnd = now.compareTo(endTime);
        return (evalStart > 0 && evalEnd >= 0);
    }

    
    
    public static long getDiffInSeconds(Calendar startTime, Calendar now) {
        long diff = startTime.getTimeInMillis() - now.getTimeInMillis();
        long diffSeconds = diff / 1000;
        return diffSeconds;
    }


    
    /**
     * Converts the given Calendar to its String representation
     * @param c Calendar
     * @return
     */
    public static String cal2Str(Calendar c) {
        SimpleDateFormat fmt= new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
        return fmt.format(c.getTime());
    }


    
    /**
     * Copies the calendar's source time to the destination calendar.
     * @param source Source calendar
     * @param dest Destination calendar
     */
    public static void copyTime(Calendar source,Calendar dest) {
        dest.set(Calendar.HOUR_OF_DAY, source.get(Calendar.HOUR_OF_DAY));
        dest.set(Calendar.MINUTE, source.get(Calendar.MINUTE));
        dest.set(Calendar.SECOND, source.get(Calendar.SECOND));
        dest.set(Calendar.MILLISECOND, source.get(Calendar.MILLISECOND));
    }

    
    
    /**
     * Copies the calendar's source time to the destination calendar.
     * @param source Source calendar
     * @param dest Destination calendar
     */
    public static void copyDate(Calendar source,Calendar dest) {
        dest.set(Calendar.YEAR, source.get(Calendar.YEAR));
        dest.set(Calendar.MONTH, source.get(Calendar.MONTH));
        dest.set(Calendar.DATE, source.get(Calendar.DATE));
    }    
    

    
    /**
     * Sets the time for the given Calendar instance
     * @param c Target Calendar
     * @param time Time to set in HH:MM:SS
     * @return
     */
    public static Calendar setTime(Calendar c,String time) {
        String[] hour= time.split(":");
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour[0]));
        c.set(Calendar.MINUTE, hour.length>=2? Integer.valueOf(hour[1]) : 0); // Set Minute
        c.set(Calendar.SECOND, hour.length>=3? Integer.valueOf(hour[2]) : 0); // Set Second
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }




}
