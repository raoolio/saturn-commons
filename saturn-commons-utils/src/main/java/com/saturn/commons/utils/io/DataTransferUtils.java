package com.saturn.commons.utils.io;


/**
 * Data Transfer formatter
 */
public class DataTransferUtils {

    /** Kilobits */
    private static final float KILO=8000;
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



    /**
     * Converts the given data transfer speed to it's corresponding human-readable form
     * @param speed Transfer speed
     * @return Cadena con representacion de los bytes
     */
    public static String speedToString(float speed) {
        return speedToString(speed,0);
    }



    /**
     * Convierte la cantidad de bytes dada a su representacion en String
     * @param speed Cantidad de bytesa convertir
     * @param decimals Number of decimal to show
     * @return Cadena con representacion de los bytes
     */
    public static String speedToString(float speed,int decimals) {
        String res=null;
        String fmt= "%."+decimals+"f";

        if (speed<KILO)
            res= speed+"Bps";
        else if (speed<MEGA)
            res= String.format(fmt,speed/KILO)+"KBps";    // Kilbytes/s
        else if (speed<GIGA)
            res= String.format(fmt,speed/MEGA)+"MBps";    // Megabytes/s
        else if (speed<TERA)
            res= String.format(fmt,speed/GIGA)+"GBps";    // Gigabytes/s
        else if (speed<PETA)
            res= String.format(fmt,speed/TERA)+"TBps";    // Terabytes/s
        else if (speed<EXA)
            res= String.format(fmt,speed/PETA)+"PBps";    // Petabytes/s
        else if (speed<ZETTA)
            res= String.format(fmt,speed/EXA)+"EBps";     // Exabytes/s
        else if (speed<YOTTA)
            res= String.format(fmt,speed/ZETTA)+"ZBps";   // Zettabytes/s
        else if (speed<BRONTO)
            res= String.format(fmt,speed/YOTTA)+"YBps";   // Yottabytes/s
        else if (speed<GEOP)
            res= String.format(fmt,speed/BRONTO)+"BBps";  // Brontobyte/s
        else
            res= String.format(fmt,speed/GEOP)+"GPBps";   // Geopbyte/s

//        System.out.println("bytesToString: BYTES["+bytes+"] -> ["+res+"]");

        return res;
    }




}
