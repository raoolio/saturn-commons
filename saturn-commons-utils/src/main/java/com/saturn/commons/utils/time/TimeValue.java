package com.saturn.commons.utils.time;

import java.util.concurrent.TimeUnit;


/**
 * Time value class
 */
public class TimeValue {

    /** Time value */
    private long value;

    /** Time unit */
    private TimeUnit unit;



    /**
     * Parses given string as a TimeValue instance
     * @param val String representation of a TimeValue
     * @return
     */
    public static final TimeValue valueOf(String val) {
        String[] ar=val.split(" ");
        if (ar.length==2) {
            return new TimeValue(Long.valueOf(ar[0]),TimeUnit.valueOf(ar[1]));
        } else
            throw new IllegalArgumentException("Invalid time value["+val+"]");
    }


    /**
     * Creates a time value with given parameters
     * @param value Time value
     * @param unit Time unit
     */
    public TimeValue(long value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }


    /** Returns time value as nanoseconds */
    public long toNanos()   { return unit.toNanos(value); }
    /** Returns time value as microseconds */
    public long toMicros()  { return unit.toMicros(value); }
    /** Returns time value as milliseconds */
    public long toMillis()  { return unit.toMillis(value); }
    /** Returns time value as seconds */
    public long toSeconds() { return unit.toSeconds(value); }
    /** Returns time value as minutes */
    public long toMinutes() { return unit.toMinutes(value); }
    /** Returns time value as hours */
    public long toHours()   { return unit.toHours(value); }
    /** Returns time value as days */
    public long toDays()    { return unit.toDays(value); }


    @Override
    public String toString() {
        // Double quoting to prevent Object to JSON conversion issues.
        return "\""+value+" "+unit+"\"";
    }

}
