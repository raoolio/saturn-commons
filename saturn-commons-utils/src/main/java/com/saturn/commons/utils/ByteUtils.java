package com.saturn.commons.utils;



/**
 * Converts the given bytes to Human-form representation.
 * @author raoolio
 */
public class ByteUtils
{
    /** Kilobytes */
    private static final float KILO=1024;
    /** Megabytes */
    private static final float MEGA=KILO*KILO;
    /** Gigabytes */
    private static final float GIGA=KILO*MEGA;
    /** Terabytes */
    private static final float TERA=KILO*GIGA;
    /** Petabytes */
    private static final float PETA=KILO*TERA;
    /** Exabytes */
    private static final float EXA=KILO*PETA;
    /** Zettabytes */
    private static final float ZETTA=KILO*EXA;
    /** Yottabytes */
    private static final float YOTTA=KILO*ZETTA;
    /** Brontobytes */
    private static final float BRONTO=KILO*YOTTA;
    /** Geopbytes */
    private static final float GEOP=KILO*BRONTO;



    // Constructor
    private ByteUtils() {
    }


    /**
     * Convierte la cantidad de bytes dada a su representacion en String
     * @param bytes Cantidad de bytesa convertir
     * @return Cadena con representacion de los bytes
     */
    public static String bytesToString(long bytes) {
        return bytesToString(bytes,0);
    }



    /**
     * Convierte la cantidad de bytes dada a su representacion en String
     * @param bytes Cantidad de bytesa convertir
     * @param decimals
     * @return Cadena con representacion de los bytes
     */
    public static String bytesToString(long bytes,int decimals) {
        String res=null;
        String fmt= "%."+decimals+"f";

        if (bytes<KILO)
            res= bytes+"B ";
        else if (bytes<MEGA)
            res= String.format(fmt,bytes/KILO)+"KB";    // Kilbytes
        else if (bytes<GIGA)
            res= String.format(fmt,bytes/MEGA)+"MB";    // Megabytes
        else if (bytes<TERA)
            res= String.format(fmt,bytes/GIGA)+"GB";    // Gigabytes
        else if (bytes<PETA)
            res= String.format(fmt,bytes/TERA)+"TB";    // Terabytes
        else if (bytes<EXA)
            res= String.format(fmt,bytes/PETA)+"PB";    // Petabytes
        else if (bytes<ZETTA)
            res= String.format(fmt,bytes/EXA)+"EB";     // Exabytes
        else if (bytes<YOTTA)
            res= String.format(fmt,bytes/ZETTA)+"ZB";   // Zettabytes
        else if (bytes<BRONTO)
            res= String.format(fmt,bytes/YOTTA)+"YB";   // Yottabytes
        else if (bytes<GEOP)
            res= String.format(fmt,bytes/BRONTO)+"BB";  // Brontobyte
        else
            res= String.format(fmt,bytes/GEOP)+"GPB";   // Geopbyte

//        System.out.println("bytesToString: BYTES["+bytes+"] -> ["+res+"]");

        return res;
    }



    /**
     * ByteUtils conversion test
     * @param args
     */
    public static void main(String[] args) {
        long b=45;
        System.out.println(bytesToString(b,1));
        System.out.println(bytesToString(b*10,1));
        System.out.println(bytesToString(b*45,1));
        System.out.println(bytesToString(b*75,1));

    }


}
