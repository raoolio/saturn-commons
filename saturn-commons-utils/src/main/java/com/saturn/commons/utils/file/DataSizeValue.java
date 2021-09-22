package com.saturn.commons.utils.file;


/**
 * Time value class
 */
public class DataSizeValue {

    /** Time value */
    private long value;

    /** Time unit */
    private DataSizeUnit unit;



    /**
     * Parses given string as a TimeValue instance
     * @param val String representation of a TimeValue
     * @return
     */
    public static final DataSizeValue valueOf(String val) {
        String[] ar=val.split(" ");
        if (ar.length==2) {
            return new DataSizeValue(Long.valueOf(ar[0]),DataSizeUnit.valueOf(ar[1]));
        } else
            throw new IllegalArgumentException("Invalid time value["+val+"]");
    }


    /**
     * Creates a time value with given parameters
     * @param value Time value
     * @param unit Time unit
     */
    public DataSizeValue(long value, DataSizeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DataSizeUnit getUnit() {
        return unit;
    }

    public void setUnit(DataSizeUnit unit) {
        this.unit = unit;
    }


    /** Converts current data size value to bits */
    public float toBits()   { return unit.toBits(value); }
    /** Converts current data size value to nibbles */
    public float toNibbles()  { return unit.toNibbles(value); }
    /** Converts current data size value to bytes */
    public float toBytes()  { return unit.toBytes(value); }
    /** Converts current data size value to kilobytes */
    public float toKiloBytes() { return unit.toKiloBytes(value); }
    /** Converts current data size value to megabytes */
    public float toMegaBytes() { return unit.toMegaBytes(value); }
    /** Converts current data size value to terabytes */
    public float toTeraBytes()   { return unit.toTeraBytes(value); }
    /** Converts current data size value to bits */
    public float toGigaBytes()    { return unit.toGigaBytes(value); }
    /** Converts current data size value to petabytes */
    public float toPetaBytes()    { return unit.toPetaBytes(value); }
    /** Converts current data size value to exabytes */
    public float toExaBytes()    { return unit.toExaBytes(value); }
    /** Converts current data size value to zettabytes */
    public float toZettaBytes()    { return unit.toZettaBytes(value); }
    /** Converts current data size value to yottabytes */
    public float toYottaBytes()    { return unit.toYottaBytes(value); }
    /** Converts current data size value to brontobytes */
    public float toBrontoBytes()    { return unit.toBrontoBytes(value); }
    /** Converts current data size value to geopbytes */
    public float toGeopBytes()    { return unit.toGeopBytes(value); }


    @Override
    public String toString() {
        // Double quoting to prevent Object to JSON conversion issues.
        return "\""+value+" "+unit+"\"";
    }

}
