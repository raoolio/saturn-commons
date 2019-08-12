package com.saturn.commons.catalog;


/**
 * KeyFilter
 */
public interface KeyFilter {


    /**
     * Called before hitting the cache
     * @param id Cached value ID
     * @return New ID to use
     */
    public String filter(String id);


}
