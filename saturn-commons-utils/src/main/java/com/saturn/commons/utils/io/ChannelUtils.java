package com.saturn.commons.utils.io;

import static com.saturn.commons.utils.io.InputStreamUtils.LOG;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


/**
 * Channel enabled operations
 */
public class ChannelUtils {


    /**
     * Downloads given URL file using Java's NIO Channels
     * @param in InputStream instance
     * @param destFile Destination file
     * @throws IOException
     * @return Number of bytes written
     */
    public static long writeToFile(InputStream in, File destFile) throws IOException {
        long bytes=0;
        try (ReadableByteChannel rbc = Channels.newChannel(in);
            FileOutputStream fos = new FileOutputStream(destFile)) {
            bytes= fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch(Exception ex) {
            LOG.error("Error downloading to File["+destFile+"]",ex);
        }
        return bytes;
    }

}
