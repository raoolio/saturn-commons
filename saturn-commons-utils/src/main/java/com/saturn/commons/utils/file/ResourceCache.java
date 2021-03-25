package com.saturn.commons.utils.file;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;


/**
 * Resource cache loader
 */
public class ResourceCache {

    /** XML Contents */
    private LoadingCache<String,String> cache;


    /**
     * Constructor
     */
    public ResourceCache() {
        cache= CacheBuilder.newBuilder()
            .maximumSize(5)
            .build(new ResourceLoader());
    }



    /**
     * Returns the given resource content
     * @param fileName Resource file name
     * @return
     * @throws ExecutionException
     */
    public String getResource(String fileName) throws ExecutionException {
        return cache.get(fileName);
    }



}
