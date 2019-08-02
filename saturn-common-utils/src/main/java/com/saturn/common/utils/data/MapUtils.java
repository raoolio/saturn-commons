package com.saturn.common.utils.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map handling utils
 */
public class MapUtils {

    private MapUtils() {
    }



    /**
     * Extracts first value of array
     * @param source Source map
     * @return
     */
    public static Map<String,String> valueArray2Map(Map<String,String[]> source) {
        Map<String,String> m= new HashMap();

        Iterator<String> it=source.keySet().iterator();
        while (it.hasNext()) {
            String key= it.next();
            String[] val=source.get(key);
            m.put(key, val[0]);
        }

        return m;
    }



}
