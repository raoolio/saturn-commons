package com.saturn.commons.catalog.id;

/**
 * CachedId Factory
 * @author raoolio
 */
public interface CachedIdFactory {


    /**
     * Creates a CachedId instance
     * @param id Catalog ID
     * @return
     */
    public CachedId makeCachedId(int id);
    
    
}
