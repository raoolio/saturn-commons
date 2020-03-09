package com.saturn.saturn.commons.redis;


/**
 * Redis HashMap interface
 * @author raoolio
 * @param <V> Type for map values
 */
public interface RedisMap<V> {


    /**
     * Stores a value in the map at given key
     * @param key Key instance
     * @param value Value to store
     */
    public void put(MapKey key,V value);


    /**
     * Retrieves the value stored in the map at given key
     * @param key Key instance
     * @return Value stored or <b>NULL</b> if not found.
     */
    public V get(MapKey key);


    /**
     * Retrieves and removes the value stored in the map at given key
     * @param key Key instance
     * @return Value stored or <b>NULL</b> if not found.
     */
    public V getAndRemove(MapKey key);


    /**
     * Tells if the given key is mapped to a value in the map
     * @param key Key instance
     * @return
     */
    public boolean exists(MapKey key);


    /**
     * Deletes the value mapped to given key
     * @param key  Key instance
     */
    public void remove(MapKey key);


}
