package com.saturn.commons.catalog.id;


/**
 * Cached ID interface
 * @author raoolio
 */
public interface CachedId {

    /**
     * Returns the cached id
     * @return
     */
    public Integer getId();


    /**
     * Increments the ID hit count
     */
    public void incrementCount();


    /**
     * Returns the hit count value
     * @return
     */
    public long getCount();

}
