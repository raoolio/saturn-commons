package com.saturn.commons.http.util;

import com.saturn.commons.http.HttpHeader;
import java.util.List;


/**
 * HttpHeader utility methods
 */
public class HttpHeaderUtil {


    /** No instance constructor */
    private HttpHeaderUtil() {
    }



    /**
     * Returns a string of comma-separated values of the given header
     * @param header HttpHeader instance
     * @return
     */
    public static String valuesToString(HttpHeader header) {
        List<String> vals= header.getValues();
        if (vals.size()==1)
            return vals.get(0);
        else {
            StringBuilder sb= new StringBuilder();
            for (String v: vals) {
                if (sb.length()>0)
                    sb.append(",");
                sb.append(v);
            }

            return sb.toString();
        }
    }

}
