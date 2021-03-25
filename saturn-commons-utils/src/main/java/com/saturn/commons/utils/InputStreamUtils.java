package com.saturn.commons.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream Utils
 */
public class InputStreamUtils {



    /**
     * Reads the given InputStream with a buffer size of 512 and returns it as a String
     * @param in InputStream instance
     * @return
     * @throws IOException
     */
    public static String readInput(InputStream in) throws IOException {
        return readInput(in,512);
    }



    /**
     * Reads the given InputStream and returns it as a String
     * @param in InputStream instance
     * @param buffSize Buffer size
     * @return
     * @throws IOException
     */
    public static String readInput(InputStream in, int buffSize) throws IOException {
        StringBuilder sb= new StringBuilder(250);
        byte[] array=new byte[buffSize];
        int bytes=-1;

        while ( (bytes=in.read(array)) > 0 ) {
            for (int i=0;i<bytes;i++) {
                sb.append((char)array[i]);
            }
        }

        return sb.toString();
    }

}
