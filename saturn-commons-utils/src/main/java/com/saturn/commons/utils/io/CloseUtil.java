package com.saturn.commons.utils.io;

import java.io.Closeable;
import java.lang.reflect.Method;


/**
 * Object closing util
 */
public class CloseUtil {

    /** No args class array */
    private static final Class[] NOARGS= {};


    private CloseUtil() {
    }



    /**
     * Closes given array of objects (quietly)
     * @param obs
     */
    public static void close(Object ... obs) {
        if (obs==null) return;

        for (Object ob: obs) {
            close(ob);
        }
    }



    /**
     * Generic object closer
     * @param ob Object instance
     */
    public static void close(Object ob) {
        if (ob==null) return;
        try {
            if (ob instanceof Closeable) {
                Closeable c=(Closeable)ob;
                c.close();
            } else {
                Method m= ob.getClass().getMethod("close", NOARGS);
                if (m!=null) {
                    m.setAccessible(true);
                    m.invoke(ob, null);
                }
            }
        } catch (Exception e) {
        }
    }



}
