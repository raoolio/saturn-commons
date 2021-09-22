package com.saturn.commons.utils.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Methods for reading bytes form an InputStream
 * @author Raul
 */
public class InputStreamUtils {

    /** Logger */
    public static final Logger LOG=LogManager.getLogger(InputStreamUtils.class);

    /** Content buffer size */
    private static final int BUFFER_SIZE=8192;

    /** Read retry count */
    private static final int READ_RETRIES=10;



    /**
     * Reads content from given input stream
     * @param in InputStream instance
     * @return
     */
    public static String readAsString(InputStream in) {
        return readAsString(in,0);
    }



    /**
     * Reads content from given input stream
     * @param in InputStream instance
     * @param size Content length
     * @return
     */
    public static String readAsString(InputStream in,int size) {
        StringBuilder bf=new StringBuilder(size>0? size : 100);
        byte[] buff=new byte[BUFFER_SIZE];
        int n;

        try {
            while ( (n=in.read(buff)) != -1 ) {
                for (int i=0;i<n;i++) {
                    bf.append((char)buff[i]);
                }
            }
        } catch (IOException e) {
            LOG.warn("Error reading InputStream",e);
        }

        return bf.toString();
    }



    /**
     * Writes the contents of the given InputStream to given file
     * @param in InputStream instance
     * @param destFile Destination file
     * @return
     */
    public static long writeToFile(InputStream in,File destFile) {
        long size=0;
        try (OutputStream out= new BufferedOutputStream(new FileOutputStream(destFile))) {
            byte[] buffer= new byte[BUFFER_SIZE];
            int n=-1;
            int retries=READ_RETRIES;
            boolean done=false;

            do {
                try {
                    while ( (n=in.read(buffer)) != -1 ) {
                        out.write(buffer,0,n);
                        size+= n;
                    }
                    done=true;
                } catch (SocketTimeoutException te) {
                    LOG.warn("Read timed out! retries["+retries+"]...");
                }
            } while (!done && retries-- >0 );

        } catch (Exception e) {
            LOG.warn("Error reading InputStream",e);
        } finally {
            CloseUtil.close(in);
        }

        return size;
    }



}
