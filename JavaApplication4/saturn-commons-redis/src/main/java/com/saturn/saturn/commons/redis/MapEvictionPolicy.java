package com.saturn.saturn.commons.redis;


/**
 * Tells if the given entry is removed
 * @param <K> Key type
 * @param <V> Value type
 */
public interface MapEvictionPolicy<K,V> {

    /**
     * Remove the given entry ?
     * @param hashKey Map's hashKey
     * @param key Entry key
     * @param value Entry value
     * @return
     */
    public boolean remove(String hashKey,K key,V value);

}
