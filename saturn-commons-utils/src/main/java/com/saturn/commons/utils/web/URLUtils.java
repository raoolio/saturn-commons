package com.saturn.commons.utils.web;

import static com.saturn.commons.utils.io.InputStreamUtils.LOG;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


/**
 * URLUtils methods
 */
public class URLUtils {


    private URLUtils() {
    }



    /**
     * Decodes the given string if it contains URL-encoded chars. Trims the
     * string if it's length is greater than maxLength chars
     * @param s String to decode
     * @param maxLength Maximum string length
     * @return
     */
    public static String URLDecodeString(String s,int maxLength) throws Exception {
        return URLDecodeString(s,maxLength,true);
    }



    /**
     * Decodes the given string if it contains URL-encoded chars. Trims the string if it's length is greater than maxLength chars
     * @param s String to decode
     * @param maxLength Maximum string length
     * @param trimOnParentheses Trim string on Parentheses close ?
     * @return
     */
    public static String URLDecodeString(String s,int maxLength,boolean trimOnParentheses) throws Exception {

        StringBuilder sb= new StringBuilder(maxLength);
        boolean isEncode=false;
        int pars=0;
        StringBuilder enc= new StringBuilder();

        // loop through chars
        loop: for (int p=0; p<s.length(); p++) {
            char c= s.charAt(p);

            switch(c) {
                case '%':
                    isEncode= true;
                    break;
                case '(':
                    pars++;
                    break;
                case ')':
                    if (--pars==0) {
                        if (trimOnParentheses) {
                            sb.append(c);
                            break loop;
                        }
                    }
                    break;

                default:
                    // encoded?
                    if (isEncode) {
                        // encode complete ? (multiple of 3)
                        if (enc.length()%3 == 0) {
                            sb.append(java.net.URLDecoder.decode(enc.toString(), "UTF-8"));
                            enc.delete(0, enc.length());
                            isEncode=false;
                        }  else if (! ((c>='0' && c<'9') || (c>='A' && c<='F'))) {
                            sb.append(enc.toString());
                            enc.delete(0, enc.length());
                            isEncode=false;
                        }
                    }
            }

            StringBuilder b= isEncode? enc : sb;
            b.append(c);

            if (sb.length()>maxLength)
                sb.delete(maxLength, sb.length());
        }//fin-for

        return sb.toString();
    }



    /**
     * Downloads given URL file using Java's NIO Channels
     * @param url
     * @param destFile
     * @throws IOException
     */
    public static void downloadFile(URL url, File destFile) throws IOException {
        try (InputStream in = url.openStream();
            ReadableByteChannel rbc = Channels.newChannel(in);
            FileOutputStream fos = new FileOutputStream(destFile)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch(Exception ex) {
            LOG.error("Error downloading["+url+"]",ex);
        }
    }
}
