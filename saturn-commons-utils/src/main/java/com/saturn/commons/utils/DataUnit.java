package com.saturn.commons.utils;


/**
 * DataUnit for enabling conversions just like the built-in TimeUnit
 * @author raoolio
 */
public enum DataUnit {


   /**
     * Bit unit representation
     */
    BIT {
        public float toBits(float d)      { return d; }
        public float toNibbles(float d)   { return d/4; }
        public float toBytes(float d)     { return d/8; }
        public float toKibibytes(float d) { return d/8/KILO; }
        public float toMebibytes(float d) { return d/8/MEGA; }
        public float toGibibytes(float d) { return d/8/GIGA; }
        public float toTebibytes(float d) { return d/8/TERA; }
        public float toPebibytes(float d) { return d/8/PETA; }
        public float toExbibytes(float d) { return d/8/EXA; }
        public float toZebibytes(float d) { return d/8/ZETTA; }
        public float toYobibytes(float d) { return d/8/YOTTA; }
        public float convert(float d, DataUnit u) { return u.toBits(d); }
    },



   /**
     * Nibble unit representation
     */
    NIBBLE {
        public float toBits(float d)      { return d*4; }
        public float toNibbles(float d)   { return d; }
        public float toBytes(float d)     { return d/2; }
        public float toKibibytes(float d) { return d/2/KILO; }
        public float toMebibytes(float d) { return d/2/MEGA; }
        public float toGibibytes(float d) { return d/2/GIGA; }
        public float toTebibytes(float d) { return d/2/TERA; }
        public float toPebibytes(float d) { return d/2/PETA; }
        public float toExbibytes(float d) { return d/2/EXA; }
        public float toZebibytes(float d) { return d/2/ZETTA; }
        public float toYobibytes(float d) { return d/2/YOTTA; }
        public float convert(float d, DataUnit u) { return u.toNibbles(d); }
    },



   /**
     * Byte unit representation
     */
    BYTE {
        public float toBits(float d)      { return d*8; }
        public float toNibbles(float d)   { return d*2; }
        public float toBytes(float d)     { return d; }
        public float toKibibytes(float d) { return d/KILO; }
        public float toMebibytes(float d) { return d/MEGA; }
        public float toGibibytes(float d) { return d/GIGA; }
        public float toTebibytes(float d) { return d/TERA; }
        public float toPebibytes(float d) { return d/PETA; }
        public float toExbibytes(float d) { return d/EXA; }
        public float toZebibytes(float d) { return d/ZETTA; }
        public float toYobibytes(float d) { return d/YOTTA; }
        public float convert(float d, DataUnit u) { return u.toBytes(d); }
    },



    /**
     * Kibibyte unit representation
     */
    KIBIBYTE {
        public float toBits(float d)      { return d*KILO*8; }
        public float toNibbles(float d)   { return d*KILO*2; }
        public float toBytes(float d)     { return d*KILO; }
        public float toKibibytes(float d) { return d; }
        public float toMebibytes(float d) { return d/KILO; }
        public float toGibibytes(float d) { return d/MEGA; }
        public float toTebibytes(float d) { return d/GIGA; }
        public float toPebibytes(float d) { return d/TERA; }
        public float toExbibytes(float d) { return d/PETA; }
        public float toZebibytes(float d) { return d/EXA; }
        public float toYobibytes(float d) { return d/ZETTA; }
        public float convert(float d, DataUnit u) { return u.toKibibytes(d); }
    },



    /**
     * Mebibyte unit representation
     */
    MEBIBYTE {
        public float toBits(float d)      { return d*MEGA*8; }
        public float toNibbles(float d)   { return d*MEGA*2; }
        public float toBytes(float d)     { return d*MEGA; }
        public float toKibibytes(float d) { return d*KILO; }
        public float toMebibytes(float d) { return d; }
        public float toGibibytes(float d) { return d/KILO; }
        public float toTebibytes(float d) { return d/MEGA; }
        public float toPebibytes(float d) { return d/GIGA; }
        public float toExbibytes(float d) { return d/TERA; }
        public float toZebibytes(float d) { return d/PETA; }
        public float toYobibytes(float d) { return d/EXA; }
        public float convert(float d, DataUnit u) { return u.toMebibytes(d); }
    },



    /**
     * Gibibyte unit representation
     */
    GIBIBYTE {
        public float toBits(float d)      { return d*GIGA*8; }
        public float toNibbles(float d)   { return d*GIGA*2; }
        public float toBytes(float d)     { return d*GIGA; }
        public float toKibibytes(float d) { return d*MEGA; }
        public float toMebibytes(float d) { return d*KILO; }
        public float toGibibytes(float d) { return d; }
        public float toTebibytes(float d) { return d/KILO; }
        public float toPebibytes(float d) { return d/MEGA; }
        public float toExbibytes(float d) { return d/GIGA; }
        public float toZebibytes(float d) { return d/TERA; }
        public float toYobibytes(float d) { return d/PETA; }
        public float convert(float d, DataUnit u) { return u.toGibibytes(d); }
    },



    /**
     * Tebibyte unit representation
     */
    TEBIBYTE {
        public float toBits(float d)      { return d*TERA*8; }
        public float toNibbles(float d)   { return d*TERA*2; }
        public float toBytes(float d)     { return d*TERA; }
        public float toKibibytes(float d) { return d*GIGA; }
        public float toMebibytes(float d) { return d*MEGA; }
        public float toGibibytes(float d) { return d*KILO; }
        public float toTebibytes(float d) { return d; }
        public float toPebibytes(float d) { return d/KILO; }
        public float toExbibytes(float d) { return d/MEGA; }
        public float toZebibytes(float d) { return d/GIGA; }
        public float toYobibytes(float d) { return d/TERA; }
        public float convert(float d, DataUnit u) { return u.toTebibytes(d); }
    },



    /**
     * Pebibyte unit representation
     */
    PEBIBYTE {
        public float toBits(float d)      { return d*PETA*8; }
        public float toNibbles(float d)   { return d*PETA*2; }
        public float toBytes(float d)     { return d*PETA; }
        public float toKibibytes(float d) { return d*TERA; }
        public float toMebibytes(float d) { return d*GIGA; }
        public float toGibibytes(float d) { return d*MEGA; }
        public float toTebibytes(float d) { return d*KILO; }
        public float toPebibytes(float d) { return d; }
        public float toExbibytes(float d) { return d/KILO; }
        public float toZebibytes(float d) { return d/MEGA; }
        public float toYobibytes(float d) { return d/GIGA; }
        public float convert(float d, DataUnit u) { return u.toPebibytes(d); }
    },


    /**
     * Exbibyte unit representation
     */
    EXBIBYTE {
        public float toBits(float d)      { return d*EXA*8; }
        public float toNibbles(float d)   { return d*EXA*2; }
        public float toBytes(float d)     { return d*EXA; }
        public float toKibibytes(float d) { return d*PETA; }
        public float toMebibytes(float d) { return d*TERA; }
        public float toGibibytes(float d) { return d*GIGA; }
        public float toTebibytes(float d) { return d*MEGA; }
        public float toPebibytes(float d) { return d*KILO; }
        public float toExbibytes(float d) { return d; }
        public float toZebibytes(float d) { return d/KILO; }
        public float toYobibytes(float d) { return d/MEGA; }
        public float convert(float d, DataUnit u) { return u.toExbibytes(d); }
    },



    /**
     * Zebibyte unit representation
     */
    ZEBIBYTE {
        public float toBits(float d)      { return d*ZETTA*8; }
        public float toNibbles(float d)   { return d*ZETTA*2; }
        public float toBytes(float d)     { return d*ZETTA; }
        public float toKibibytes(float d) { return d*EXA; }
        public float toMebibytes(float d) { return d*PETA; }
        public float toGibibytes(float d) { return d*TERA; }
        public float toTebibytes(float d) { return d*GIGA; }
        public float toPebibytes(float d) { return d*MEGA; }
        public float toExbibytes(float d) { return d*KILO; }
        public float toZebibytes(float d) { return d; }
        public float toYobibytes(float d) { return d/KILO; }
        public float convert(float d, DataUnit u) { return u.toZebibytes(d); }
    },



    /**
     * Yobibyte unit representation
     */
    YOBIBYTE {
        public float toBits(float d)      { return d*YOTTA*8; }
        public float toNibbles(float d)   { return d*YOTTA*2; }
        public float toBytes(float d)     { return d*YOTTA; }
        public float toKibibytes(float d) { return d*ZETTA; }
        public float toMebibytes(float d) { return d*EXA; }
        public float toGibibytes(float d) { return d*PETA; }
        public float toTebibytes(float d) { return d*TERA; }
        public float toPebibytes(float d) { return d*GIGA; }
        public float toExbibytes(float d) { return d*MEGA; }
        public float toZebibytes(float d) { return d/KILO; }
        public float toYobibytes(float d) { return d; }
        public float convert(float d, DataUnit u) { return u.toYobibytes(d); }
    }
    ;



    /** Static constants for conversion */
    static final float KILO=1024;
    static final float MEGA=KILO*KILO;
    static final float GIGA=KILO*MEGA;
    static final float TERA=KILO*GIGA;
    static final float PETA=KILO*TERA;
    static final float EXA=KILO*PETA;
    static final float ZETTA=KILO*EXA;
    static final float YOTTA=KILO*ZETTA;
//    static final float BRONTO=KILO*YOTTA;
//    static final float GEOP=KILO*BRONTO;


    public float toBits(float d) {
        throw new AbstractMethodError();
    }

    public float toNibbles(float d) {
        throw new AbstractMethodError();
    }

    public float toBytes(float d) {
        throw new AbstractMethodError();
    }

    public float toKibibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toMebibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toGibibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toTebibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toPebibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toExbibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toZebibytes(float d) {
        throw new AbstractMethodError();
    }

    public float toYobibytes(float d) {
        throw new AbstractMethodError();
    }


}
