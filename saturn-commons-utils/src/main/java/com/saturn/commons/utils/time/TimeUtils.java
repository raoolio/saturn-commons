package com.saturn.commons.utils.time;

import java.util.concurrent.TimeUnit;



/**
 * Algoritmo para convertir una cantidad de milisegundos a String para mostrar
 * en logs.
 * @author raoolio
 */
public class TimeUtils
{
    /** UTC Format */
    private static final String UTC_FORMAT="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /** Para Conversion de Milisegundos */

    /** Segundos */
    private static final float SECOND=1000;
    /** Minutos */
    private static final float MINUTE=60*SECOND;
    /** Horas */
    private static final float HOUR=60*MINUTE;
    /** Dias */
    private static final float DAY=24*HOUR;
    /** Mes */
    private static final float MONTH=30*DAY;
    /** Año */
    private static final float YEAR=365*DAY;

    /** Un Microsegundo posee mil nanosegundos */
    private static final float MICROSECOND= 1000;

    /** Un Milisegundo posee un millon de nanosegundos */
    private static final float MILISECOND= 1000000;




//    private static DecimalFormat fmt= new DecimalFormat("###.##");


    // Constructor
    private TimeUtils()
    {
    }



    /**
     * Convierte los milisegundos a String con Unidad
     * @param milis Milisegundos transcurridos
     * @return
     */
    public static final String milisToString(long milis) {

        if (milis<SECOND)
            return intToString(milis,"ms");
        else if (milis<MINUTE)
            return intToString(milis/SECOND,"sec");
        else if (milis<HOUR) {
            Float min=milis/MINUTE;
            int secs= (int) ((min-min.intValue())*60f) ;
            StringBuilder sb=new StringBuilder();
            sb.append(min.intValue()).append("min ");
            sb.append(secs).append("sec");
            return sb.toString();
        } else if (milis<DAY) {
            Float hrs= milis/HOUR;
            int mins= (int) ((hrs-hrs.intValue())*60f );
            StringBuilder sb= new StringBuilder();
            sb.append(hrs.intValue()).append("hrs ");
            sb.append(mins).append("min");
            return sb.toString();
        } else if (milis<MONTH) {
            Float days= milis/DAY;
            int hrs= (int) ((days-days.intValue())*24f );
            StringBuilder sb= new StringBuilder();
            sb.append(days.intValue()).append("days ");
            sb.append(hrs).append("hrs");
            return sb.toString();
        } else if (milis<YEAR) {
            Float months= milis/MONTH;
            int days= (int) ((months-months.intValue())*30f );
            StringBuilder sb= new StringBuilder();
            sb.append(months.intValue()).append("months ");
            sb.append(days).append("days");
            return sb.toString();
        } else {
            Float years= milis/YEAR;
            int months= (int) ((years-years.intValue())*12f );
            StringBuilder sb= new StringBuilder();
            sb.append(years.intValue()).append("years ");
            sb.append(months).append("months");
            return sb.toString();
        }
    }



    /**
     * Convierte los milisegundos a String con Unidad
     * @param nano Nanosegundos
     * @return
     */
    public static final String nanoToString(long nano) {

        // Es nano segundo?
        if (nano<MICROSECOND)
            return floatToString(nano,"ns");
        // Es Micro-segundo?
        else if (nano<MILISECOND)
            return floatToString(nano/1000,"us");
        else
            // Lo convertimos a milisegundo y lo formateamos
            return milisToString(nano/1000000);
    }


    private static String intToString(float value,String unit) {
        Float f= value;
        return String.valueOf(f.intValue()).concat(unit);
    }



    private static String floatToString(float value,String unit) {
        float rounded = Math.round(value * 100f)/100f;
        return String.valueOf(rounded)+unit;
    }



    /**
     * Suspende el Thread actual la cantidad de segundos dada...
     * @param seconds Cantidad de segundos de suspension
     */
    public static void sleep(long seconds) {
        sleep(seconds,TimeUnit.SECONDS);
    }



    /**
     * Suspende el Thread actual la cantidad de unidades de tiempo dada
     * @param time Cantidad de tiempo a dormir
     * @param unit Unidad de tiempo
     */
    public static void sleep(long time,TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(time));
        }
        catch (InterruptedException e) {
        }
    }



    /**
     * Suspende el Thread actual la cantidad de unidades de tiempo dada
     * @param value Valor de tiempo a esperar
     */
    public static void sleep(TimeValue value) {
        try {
            Thread.sleep(value.toMillis());
        } catch (InterruptedException e) {
        }
    }




//    public static void main(String[] a)
//    {
//        long total=65500857000l;
//
//        System.out.println(TimeUtils.milisToString(total));
//    }



}
