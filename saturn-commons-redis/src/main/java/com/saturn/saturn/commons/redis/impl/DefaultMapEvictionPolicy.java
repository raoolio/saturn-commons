package com.saturn.saturn.commons.redis.impl;

import com.saturn.saturn.commons.redis.MapEvictionPolicy;


/**
 * Default RedisClieanFilter implementation that deletes all entries on execution
 */
public class DefaultMapEvictionPolicy implements MapEvictionPolicy {

    public boolean remove(String arg0, Object arg1, Object arg2) {
        return true;
    }



}
