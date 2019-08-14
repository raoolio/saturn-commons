package com.saturn.commons.data;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Builds a cache of the given types
 * @param <K> Key value type
 * @param <V> Object value type
 */
public abstract class CacheHandler<K,V> extends CacheLoader<K,V> {

    /** Logger */
    protected final Logger LOG;

    /** Subscription ID Cache */
    protected LoadingCache<K,V> cache;



    /**
     * Constructor
     */
    public CacheHandler() {
        this(100,Duration.ofHours(1));
    }



    /**
     * Constructor
     * @param maxSize Maximum cache size
     * @param reload Reload the cache after given time
     */
    public CacheHandler(int maxSize,Duration reload) {

        // Init log
        LOG= LogManager.getLogger(this.getClass());
        LOG.info("Init cache...");

        // Init the cache
        cache= CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(reload.toMinutes(), TimeUnit.MINUTES)
                .build(this);
    }



}
