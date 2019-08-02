package com.saturn.common.utils.data;

import java.text.DecimalFormat;



/**
 * Algoritmo de conversion de Bytes a su representacion en String segun la unidad
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

    private static DecimalFormat fmt= new DecimalFormat("###.##");



    // Constructor
    private ByteUtils()
    {
    }



    /**
     * Convierte la cantidad de bytes dada a su representacion en String
     * @param bytes Cantidad de bytesa convertir
     * @return Cadena con representacion de los bytes
     */
    public static String bytesToString(long bytes)
    {
        String res=null;

        if (bytes<KILO)
            res= bytes+"B";
        else if (bytes<MEGA)
            res= floatToString(bytes/KILO)+"KB";
        else if (bytes<GIGA)
            res= floatToString(bytes/MEGA)+"MB";
        else if (bytes<TERA)
            res= floatToString(bytes/GIGA)+"GB";
        else if (bytes<PETA)
            res= floatToString(bytes/TERA)+"TB";
        else if (bytes<EXA)
            res= floatToString(bytes/PETA)+"PB";
        else if (bytes<ZETTA)
            res= floatToString(bytes/EXA)+"EB";
        else if (bytes<YOTTA)
            res= floatToString(bytes/ZETTA)+"ZB";
        else if (bytes<BRONTO)
            res= floatToString(bytes/YOTTA)+"YB";
        else if (bytes<GEOP)
            res= floatToString(bytes/BRONTO)+"BB";
        else
            res= floatToString(bytes/GEOP)+"GPB";

//        System.out.println("bytesToString: BYTES["+bytes+"] -> ["+res+"]");

        return res;
    }



    private static synchronized String floatToString(float value)
    {
        return fmt.format(value);
    }


}
