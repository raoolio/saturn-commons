package com.saturn.common.utils;



/**
 * BufferUtils methods
 */
public class BufferUtils {


    protected BufferUtils() {
    }



    /**
     * Append the object to the buffer
     * @param sb Destination buffer
     * @param v Object
     */
    protected static StringBuilder append(StringBuilder sb,Object v) {
        if (v instanceof CharSequence)
            sb.append('"').append(v).append('"');
        else if (v instanceof Character)
            sb.append('\'').append(v).append('\'');
        else
            sb.append(v);
        return sb;
    }


}
