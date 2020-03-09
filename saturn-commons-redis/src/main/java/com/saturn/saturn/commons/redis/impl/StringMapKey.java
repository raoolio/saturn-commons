package com.saturn.saturn.commons.redis.impl;

import com.saturn.saturn.commons.redis.MapKey;


/**
 * String based MapKey implementation
 * @author raoolio
 */
public class StringMapKey implements MapKey {

    private String key;


    /**
     * Constructor
     * @param key
     */
    public StringMapKey(String key) {
        this.key = key;
    }


    @Override
    public String getKey() {
        return key;
    }

}
