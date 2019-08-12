package com.saturn.commons.catalog.id;


/**
 * Factory for returning counting CachedId
 */
public class CachedIdCountFactory implements CachedIdFactory {

    @Override
    public CachedId makeCachedId(int id) {
        return new CountingCachedId(id);
    }

}
