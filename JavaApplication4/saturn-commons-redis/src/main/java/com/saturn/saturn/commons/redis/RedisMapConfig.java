package com.saturn.saturn.commons.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;


/**
 * RedisMap configuration
 */
public class RedisMapConfig {

    private RedisConnectionFactory connFactory;

    private String hashPrefix;

    private int hashKeySize;

    private MapEvictionPolicy evictionPolicy;

    private RedisSerializer keySerializer;

    private RedisSerializer valueSerializer;


    RedisMapConfig() {
    }


    public String getHashPrefix() {
        return hashPrefix;
    }

    public MapEvictionPolicy getEvictionPolicy() {
        return evictionPolicy;
    }

    public RedisConnectionFactory getConnectionFactory() {
        return connFactory;
    }

    public RedisSerializer getKeySerializer() {
        return keySerializer;
    }

    public RedisSerializer getValueSerializer() {
        return valueSerializer;
    }

    public int getHashKeySize() {
        return hashKeySize;
    }


    void setConnFactory(RedisConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    void setHashPrefix(String hashPrefix) {
        this.hashPrefix = hashPrefix;
    }

    void setEvictionPolicy(MapEvictionPolicy evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    void setHashKeySize(int hashKeySize) {
        this.hashKeySize = hashKeySize;
    }

    boolean hasHashKeySize() {
        return hashKeySize > 0;
    }

    boolean hasSerializer() {
        return keySerializer!=null || valueSerializer!=null;
    }

    boolean hasEvictionPolicy() {
        return evictionPolicy!=null;
    }

    boolean hasKeySerializer() {
        return keySerializer!=null;
    }

    boolean hasValueSerializer() {
        return valueSerializer!=null;
    }


    public String toString() {
        return "DefaultRedisMapConfig{" + "connFactory=" + connFactory + ", hashPrefix=" + hashPrefix + ", evictionPolicy=" + evictionPolicy + ", keySerializer=" + keySerializer + ", valueSerializer=" + valueSerializer + '}';
    }


}
