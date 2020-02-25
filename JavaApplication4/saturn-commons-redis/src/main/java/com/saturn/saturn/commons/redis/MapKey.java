package com.saturn.saturn.commons.redis;


/**
 * MapKey used to access a Redis HashMap
 */
public interface MapKey {


    /**
     * Returns the string representation of the key object
     * @return
     */
    public String getKey();

}
