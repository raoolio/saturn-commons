package com.saturn.common.utils.json;

import java.util.Map;


/**
 * JSON Listener
 * @author raoolio
 */
public interface JSONListener {

    /**
     * Invoked when an attribute is found
     * @param object Object attributes
     */
    public void objectFound(Map<String,String> object);




}
