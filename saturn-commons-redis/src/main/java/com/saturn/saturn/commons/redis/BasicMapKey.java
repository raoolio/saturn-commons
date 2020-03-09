package com.saturn.saturn.commons.redis;

import org.apache.commons.lang3.Validate;


/**
 * Basic MapKey implementation based on a string
 * @author raoolio
 */
public class BasicMapKey implements MapKey {

    /** Key value */
    private String key;


    /**
     * Constructor for string based key
     * @param key Key used for value retrieval
     */
    public BasicMapKey(String key) {
        Validate.notBlank(key, "BasicMapKey: Invalid key");
        this.key = key;
    }


    @Override
    public String getKey() {
        return key;
    }

}
