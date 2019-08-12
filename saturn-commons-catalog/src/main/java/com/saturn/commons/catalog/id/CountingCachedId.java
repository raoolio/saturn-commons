package com.saturn.commons.catalog.id;

import java.util.concurrent.atomic.AtomicLong;



/**
 * Cached id entry
 */
public class CountingCachedId extends CachedIdImpl {

    /** Hit counter */
    private AtomicLong count;



    /**
     * Constructor with hit count starting at zero.
     * @param value
     */
    public CountingCachedId(Integer value) {
        super(value);
        this.count= new AtomicLong();
    }


    @Override
    public void incrementCount() {
        count.incrementAndGet();
    }


    /**
     * Returns the current hit count id
     * @return
     */
    @Override
    public long getCount() {
        return count.get();
    }


    /**
     *
     * @param count
     */
    public void setCount(long count) {
        this.count.set(count);
    }


    @Override
    public String toString() {
        return super.toString()+ " COUNT[" + count + ']';
    }


}
