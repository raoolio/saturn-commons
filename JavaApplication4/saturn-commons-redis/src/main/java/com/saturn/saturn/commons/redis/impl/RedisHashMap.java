package com.saturn.saturn.commons.redis.impl;

import com.saturn.commons.utils.TimeUtils;
import com.saturn.saturn.commons.redis.MapEvictionPolicy;
import com.saturn.saturn.commons.redis.RedisMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import com.saturn.saturn.commons.redis.RedisMapConfig;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * RedisMap implementation
 * @author raoolio
 * @param <K> Type of key mapping.
 * @param <V> Type of value mappings
 */
public class RedisHashMap<K,V> implements RedisMap<K,V> {

    /** Logger */
    private static final Logger LOG= LogManager.getLogger(RedisHashMap.class);

    /** Redis KEY */
    private RedisMapConfig config;

    /** RedisTemplate instance */
    private RedisTemplate<String,V> redisTemplate;

    /** Counter cache */
    private HashOperations<String, K, V> cache;



    /**
     * Constructor
     * @param config RedisMap configuration
     */
    public RedisHashMap(RedisMapConfig config) {
        this.config= config;
        init();
    }


    private void init() {
        LOG.info("Starting RedisHashMap instance...");

        // Set key value serializers...
        redisTemplate= new RedisTemplate();
        redisTemplate.setConnectionFactory(config.getConnectionFactory());
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setHashKeySerializer( config.getKeySerializer() );
        redisTemplate.setHashValueSerializer( config.getValueSerializer() );

        // Init HashOperations instance
        cache= redisTemplate.opsForHash();
    }



    /**
     * Cleans the given bucket
     * @param map Hashmap
     * @return
     */
    private int cleanBucket(String hashKey,Map<K,V> map) {
        int n=0;
        LOG.trace("Cleaning entries under bucket[{}]",hashKey);
        MapEvictionPolicy evictionPolicy= config.getEvictionPolicy();

        Iterator<K> it= map.keySet().iterator();
        while (it.hasNext()) {
            K key= it.next();
            V val= map.get(key);

            // Drop this entry?
            if (evictionPolicy.remove(hashKey, key, val)) {
                LOG.trace("Purging KEY[{}]",key);
                cache.delete(hashKey,key);
                it.remove();
                n++;
            }
        }
        return n;
    }



    /**
     * Removes expired entries from the Redis Cache
     */
    public void cleanCache() {
        long t= System.currentTimeMillis();
        int n=0;
        String searchKw= config.getHashPrefix().concat("*");
        LOG.trace("Looking for hashKeys with prefix[{}]",searchKw);

        Set<String> hashKeysSet= redisTemplate.keys(searchKw);
        LOG.trace("[{}] buckets found!",hashKeysSet.size());

        Iterator<String> hashKeys=  hashKeysSet.iterator();
        while (hashKeys.hasNext()) {
            String hashKey= hashKeys.next();
            Map<K,V> bucket= cache.entries(hashKey);
            n+=cleanBucket(hashKey,bucket);
        }

        if (n>0) {
            t= System.currentTimeMillis()-t;
            LOG.info("Redis cleanup done! [{}] entries removed in {}",n,TimeUtils.milisToString(t));
        }
    }



    /**
     * Builds the HashKey by appending the HASH_PREFIX to user key
     * @param key Key instance
     * @return
     */
    private String getHashKey(K key) {
        return config.getHashPrefix()+":"+key;
    }



    /**
     * Builds the HashKey by appending the HASH_PREFIX to user key
     * @param hashKey HashKey
     * @return
     */
    private String getHashKey(String hashKey) {
        return config.getHashPrefix()+":"+hashKey;
    }



    /**
     * Stores the subsId as hash value, the msisdn is used as hash key.
     * @param key Key instance
     * @param value Value to associate to given key
     */
    @Override
    public void put(K key, V value) {
        String hashKey= getHashKey(key);
        cache.put(hashKey, key, value);
        LOG.debug("MAP[{}] KEY[{}]=[{}]",hashKey,key,value);
    }



    /**
     * Retrieves the subsId associated with given msisdn.
     * @param key Request object
     * @return
     */
    @Override
    public V get(K key) {
        String hashKey= getHashKey(key);
        V val= cache.get(hashKey, key);
        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hashKey,key,val);
        return val;
    }



    /**
     * Retrieves the subsId associated with given msisdn.
     * @param key Request object
     * @return
     */
    @Override
    public V getAndRemove(K key) {
        String hashKey= getHashKey(key);
        V val= cache.get(hashKey, key);
        if (val!=null) {
            cache.delete(hashKey, key);
        }

        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hashKey,key,val);
        return val;
    }



    /**
     * Deletes the token mapped to given key
     * @param key Key instance
     */
    @Override
    public void remove(K key) {
        String hashKey= getHashKey(key);
        cache.delete(hashKey, key);
        LOG.debug("MAP[{}] KEY[{}]",hashKey,key);
    }



    @Override
    public boolean exists(K key) {
        String hashKey= getHashKey(key);
        boolean has=cache.hasKey(hashKey, key);
        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hashKey,key,has);
        return has;
    }



    @Override
    public long size(String hashKey) {
        String hk= getHashKey(hashKey);
        long size= cache.size(hk);
        LOG.debug("MAP[{}] -> [{}]",hk,size);
        return size;
    }


}
