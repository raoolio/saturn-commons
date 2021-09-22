package com.saturn.saturn.commons.redis.impl;

import com.saturn.commons.utils.time.TimeUtils;
import com.saturn.saturn.commons.redis.MapEvictionPolicy;
import com.saturn.saturn.commons.redis.MapKey;
import com.saturn.saturn.commons.redis.RedisMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.commons.lang3.Validate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * RedisMap implementation that disperses the a Map of value-pair entries across many
 * HashMap buckets inside Redis memory. For example a map like the following:
 * {
 *   "00004523" -> Value1
 *   "00000045" -> Value2
 *   "20000078" -> Value3
 * }
 *
 * Gets mapped inside Redis in the following structure:
 *
 * Bucket #1:
 * "<hashPrefix>:0000" -> {
 *   "4523" -> Value1
 *   "0045" -> Value2
 * }
 *
 * Bucket #2:
 * "<hashPrefix>:2000" -> {
 *   "0078" -> Value3
 * }
 *
 * The keySize attribute determines the key length for storing the values inside
 * each bucket. This mapping structure is performed to take advante of the Redis
 * configuration parameter 'hash-max-ziplist-entries', that states that if the
 * number of entries within a bucket is below the value of this parameter, then
 * Redis can optimize the amount of memory used to store the entries in that HashMap.
 * By default it has a value of 512 entries, but it can be tunned in the Redis
 * configuration file to suit your needs.
 *
 * @author raoolio
 * @param <V> Type of value mappings
 */
public class RedisHashMap<V> implements RedisMap<V> {

    /** Logger */
    private static final Logger LOG= LogManager.getLogger(RedisHashMap.class);

    /** Connection Factory */
    private RedisConnectionFactory connFactory;

    /** Hash Map prefix */
    private String hashPrefix;

    /** Number of right-most characters from key used as HashMap key */
    private int keySize;

    /** Eviction policy */
    private MapEvictionPolicy evictionPolicy;

    /** Map key serializer */
    private RedisSerializer keySerializer;

    /** Map value serializer */
    private RedisSerializer valueSerializer;

    /** RedisTemplate instance */
    private RedisTemplate<String,V> redisTemplate;

    /** Counter cache */
    private HashOperations<String, String, V> cache;



    /**
     * Constructor
     */
    public RedisHashMap() {
    }



    //<editor-fold defaultstate="collapsed" desc=" Setter methods ">
    public void setConnFactory(RedisConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public void setHashPrefix(String hashPrefix) {
        this.hashPrefix = hashPrefix;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public void setEvictionPolicy(MapEvictionPolicy evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }
    //</editor-fold>



    /**
     * Initialize the Redis HashMap
     */
    public void init() {
        LOG.info("Starting RedisHashMap instance...");

        Validate.notNull(connFactory,"Redis Connection Factory is required");
        Validate.notBlank(hashPrefix,"hashPrefix is required");

        // Build bean
        if (keySize <= 0)
            keySize=4;

        if (evictionPolicy==null)
            evictionPolicy= new DefaultMapEvictionPolicy();

        if (keySerializer == null)
            keySerializer= new StringRedisSerializer();

        if (valueSerializer == null)
            valueSerializer= new StringRedisSerializer();


        // Set key value serializers...
        redisTemplate= new RedisTemplate();
        redisTemplate.setConnectionFactory(connFactory);
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setHashKeySerializer( keySerializer );
        redisTemplate.setHashValueSerializer( valueSerializer );
        redisTemplate.afterPropertiesSet();

        // Init HashOperations instance
        cache= redisTemplate.opsForHash();
    }



    /**
     * Cleans the given bucket
     * @param map Hashmap
     * @return
     */
    private int cleanBucket(String hashKey,Map<String,V> map) {
        int n=0;
        LOG.trace("Cleaning entries under bucket[{}]",hashKey);

        Iterator<String> it= map.keySet().iterator();
        while (it.hasNext()) {
            String key= it.next();
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
        String searchKw= hashPrefix.concat("*");
        LOG.trace("Looking for hashKeys with prefix[{}]",searchKw);

        Set<String> hashKeysSet= redisTemplate.keys(searchKw);
        LOG.trace("[{}] buckets found!",hashKeysSet.size());

        Iterator<String> hashKeys=  hashKeysSet.iterator();
        while (hashKeys.hasNext()) {
            String hashKey= hashKeys.next();
            Map<String,V> bucket= cache.entries(hashKey);
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
    private RedisHashKey getHashKey(MapKey key) {
        String $key= key.getKey();
        int hashKeySize= $key.length()-keySize;

        return new RedisHashKey()
                .setHashKey(hashPrefix+":"+$key.substring(0, hashKeySize))
                .setKey($key.substring(hashKeySize));
    }



    /**
     * Stores the subsId as hash value, the msisdn is used as hash key.
     * @param key Key instance
     * @param value Value to associate to given key
     */
    @Override
    public void put(MapKey key, V value) {
        RedisHashKey hkey= getHashKey(key);
        cache.put(hkey.getHashKey(), hkey.getKey(), value);
        LOG.debug("MAP[{}] KEY[{}]=[{}]",hkey.getHashKey(),hkey.getKey(),value);
    }



    /**
     * Retrieves the subsId associated with given msisdn.
     * @param key Request object
     * @return
     */
    @Override
    public V get(MapKey key) {
        RedisHashKey hkey= getHashKey(key);
        V val= cache.get(hkey.getHashKey(), hkey.getKey());
        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hkey.getHashKey(),hkey.getKey(),val);
        return val;
    }



    /**
     * Retrieves the subsId associated with given msisdn.
     * @param key Request object
     * @return
     */
    @Override
    public V getAndRemove(MapKey key) {
        RedisHashKey hkey= getHashKey(key);
        V val= cache.get(hkey.getHashKey(), hkey.getKey());
        if (val!=null) {
            cache.delete(hkey.getHashKey(), hkey.getKey());
        }

        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hkey.getHashKey(),hkey.getKey(),val);
        return val;
    }



    /**
     * Deletes the token mapped to given key
     * @param key Key instance
     */
    @Override
    public void remove(MapKey key) {
        RedisHashKey hkey= getHashKey(key);
        cache.delete(hkey.getHashKey(), hkey.getKey());
        LOG.debug("MAP[{}] KEY[{}]",hkey.getHashKey(),hkey.getKey());
    }



    /**
     * Tells if given key is mapped to a value
     * @param key
     * @return
     */
    @Override
    public boolean exists(MapKey key) {
        RedisHashKey hkey= getHashKey(key);
        boolean has=cache.hasKey(hkey.getHashKey(), hkey.getKey());
        LOG.debug("MAP[{}] KEY[{}] -> [{}]",hkey.getHashKey(),hkey.getKey(),has);
        return has;
    }


}
