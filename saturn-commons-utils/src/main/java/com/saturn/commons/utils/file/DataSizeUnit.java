package com.saturn.commons.utils.file;


/**
 * Size Unit
 */
public enum DataSizeUnit {

    BIT(DataSizeUnit.BIT_SCALE),
    NIBBLE(DataSizeUnit.NIBBLE_SCALE),
    BYTE(DataSizeUnit.BYTE_SCALE),
    KILOBYTE(DataSizeUnit.KILO),
    MEGABYTE(DataSizeUnit.MEGA),
    GIGABYTE(DataSizeUnit.GIGA),
    TERABYTE(DataSizeUnit.TERA),
    PETABYTE(DataSizeUnit.PETA),
    EXABYTE(DataSizeUnit.EXA),
    ZETTABYTE(DataSizeUnit.ZETTA),
    YOTTABYTE(DataSizeUnit.YOTTA),
    BRONTOBYTE(DataSizeUnit.BRONTO),
    GEOPBYTE(DataSizeUnit.GEOP);


    /** Bits */
    private static final float BIT_SCALE=1/8;
    /** Nibbles */
    private static final float NIBBLE_SCALE=1/2;
    /** Bytes */
    private static final float BYTE_SCALE=1;
    /** Kilobytes */
    private static final float KILO=1024;
    /** Megabytes */
    private static final float MEGA=KILO*1024;
    /** Gigabytes */
    private static final float GIGA=MEGA*1024;
    /** Terabytes */
    private static final float TERA=GIGA*1024;
    /** Petabytes */
    private static final float PETA=TERA*1024;
    /** Exabytes */
    private static final float EXA=PETA*1024;
    /** Zettabytes */
    private static final float ZETTA=EXA*1024;
    /** Yottabytes */
    private static final float YOTTA=ZETTA*1024;
    /** Brontobytes */
    private static final float BRONTO=YOTTA*1024;
    /** Geopbytes */
    private static final float GEOP=BRONTO*1024;


    /** Scale related to bytes */
    private final float scale;


    private DataSizeUnit(float byteScale) {
        this.scale=byteScale;
    }


    public float toBits(long value) {
        float b= value*scale;
        return b*8;
    }

    public float toNibbles(long value) {
        float b= value*scale;
        return b*2;
    }

    public float toBytes(long value) {
        float b= value*scale;
        return b;
    }

    public float toKiloBytes(long value) {
        float b= value*scale;
        return (b/KILO);
    }

    public float toMegaBytes(long value) {
        float b= value*scale;
        return b/MEGA;
    }

    public float toGigaBytes(long value) {
        float b= value*scale;
        return b/GIGA;
    }

    public float toTeraBytes(long value) {
        float b= value*scale;
        return b/TERA;
    }

    public float toPetaBytes(long value) {
        float b= value*scale;
        return b/PETA;
    }

    public float toExaBytes(long value) {
        float b= value*scale;
        return b/EXA;
    }


    public float toZettaBytes(long value) {
        float b= value*scale;
        return b/ZETTA;
    }

    public float toYottaBytes(long value) {
        float b= value*scale;
        return b/YOTTA;
    }

    public float toBrontoBytes(long value) {
        float b= value*scale;
        return b/BRONTO;
    }

    public float toGeopBytes(long value) {
        float b= value*scale;
        return b/GEOP;
    }



}
