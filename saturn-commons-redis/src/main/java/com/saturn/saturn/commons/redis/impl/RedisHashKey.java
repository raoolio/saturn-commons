package com.saturn.saturn.commons.redis.impl;

/**
 *
 * @author raoolio
 */
public class RedisHashKey {

    /** Hash for retrieving the HashMap */
    private String hashKey;

    /** Key within the HashMap for retrieving a value */
    private String key;



    /**
     * Sets the HashKey instance
     * @param hashKey
     * @return
     */
    public RedisHashKey setHashKey(String hashKey) {
        this.hashKey = hashKey;
        return this;
    }



    /**
     * Sets the key instance
     * @param key
     * @return
     */
    public RedisHashKey setKey(String key) {
        this.key = key;
        return this;
    }


    /**
     * Returns the HashKey
     * @return
     */
    public String getHashKey() {
        return hashKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "MapKey: hashKey[" + hashKey + "] key[" + key + ']';
    }

    
}
