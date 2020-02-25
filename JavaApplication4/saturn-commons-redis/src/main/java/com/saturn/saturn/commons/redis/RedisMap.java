package com.saturn.saturn.commons.redis;


/**
 * Redis HashMap interface
 * @author raoolio
 * @param <K> Type for map keys
 * @param <V> Type for map values
 */
public interface RedisMap<K,V> {


    /**
     * Stores a value in the map at given key
     * @param key Key instance
     * @param value Value to store
     */
    public void put(K key,V value);


    /**
     * Retrieves the value stored in the map at given key
     * @param key Key instance
     * @return Value stored or <b>NULL</b> if not found.
     */
    public V get(K key);


    /**
     * Retrieves and removes the value stored in the map at given key
     * @param key Key instance
     * @return Value stored or <b>NULL</b> if not found.
     */
    public V getAndRemove(K key);


    /**
     * Tells if the given key is mapped to a value in the map
     * @param key Key instance
     * @return
     */
    public boolean exists(K key);


    /**
     * Deletes the value mapped to given key
     * @param key  Key instance
     */
    public void remove(K key);


    /**
     * Returns the number of elements stored at given bucket
     * @param hashKey Map's hash
     * @return
     */
    public long size(String hashKey);


}
