package com.saturn.commons.catalog.id;


/**
 * No counter implementation
 * @author raoolio
 */
public class CachedIdImpl implements CachedId {


    private Integer id;


    /**
     * Constructor
     * @param id
     */
    public CachedIdImpl(Integer id) {
        this.id = id;
    }


    /**
     * Set id value
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void incrementCount() {
    }

    @Override
    public long getCount() {
        return 0;
    }

    @Override
    public String toString() {
        return "CachedIdImpl: ID[" + id + ']';
    }




}
