package com.saturn.common.utils.dto;

import java.util.concurrent.TimeUnit;


/**
 * Time value class
 */
public class TimeValue {

    private int value;
    private TimeUnit unit;



    /**
     * Creates a time value with given parameters
     * @param value Time value
     * @param unit Time unit
     */
    public TimeValue(int value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
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

    @Override
    public String toString() {
        return "TimeValue: " + value + " " + unit;
    }

}
