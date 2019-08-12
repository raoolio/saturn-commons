package com.saturn.commons.catalog.id;



/**
 * Factory that returns CachedId instances that do NOT track HIT count
 */
public class CachedIdPlainFactory implements CachedIdFactory {

    @Override
    public CachedId makeCachedId(int id) {
        return new CachedIdImpl(id);
    }

}
