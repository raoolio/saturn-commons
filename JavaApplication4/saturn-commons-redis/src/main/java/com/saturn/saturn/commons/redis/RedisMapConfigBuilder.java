package com.saturn.saturn.commons.redis;

import com.saturn.saturn.commons.redis.impl.DefaultMapEvictionPolicy;
import org.apache.commons.lang3.Validate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * RedisMapConfig builder
 * @author raoolio
 */
public class RedisMapConfigBuilder {

    private RedisMapConfig conf;


    public RedisMapConfigBuilder() {
        conf= new RedisMapConfig();
    }


    public RedisMapConfigBuilder setConnFactory(RedisConnectionFactory connFactory) {
        conf.setConnFactory(connFactory);
        return this;
    }


    public RedisMapConfigBuilder setHashPrefix(String hashPrefix) {
        conf.setHashPrefix(hashPrefix);
        return this;
    }

    public RedisMapConfigBuilder setHashKeySize(int size) {
        conf.setHashKeySize(size);
        return this;
    }


    public RedisMapConfigBuilder setEvictionPolicy(MapEvictionPolicy evictionPolicy) {
        conf.setEvictionPolicy(evictionPolicy);
        return this;
    }

    public RedisMapConfigBuilder setKeySerializer(RedisSerializer keySerializer) {
        conf.setKeySerializer(keySerializer);
        return this;
    }

    public RedisMapConfigBuilder setValueSerializer(RedisSerializer valueSerializer) {
        conf.setValueSerializer(valueSerializer);
        return this;
    }


    public RedisMapConfig build() {
        Validate.notNull(conf.getConnectionFactory(),"Redis Connection Factory is required");
        Validate.notBlank(conf.getHashPrefix(),"hashPrefix is required");

        RedisSerializer strSerializer=null;

        if (!conf.hasSerializer())
            strSerializer= new StringRedisSerializer();

        // Build bean
        if (!conf.hasHashKeySize())
            conf.setHashKeySize(3);

        if (!conf.hasEvictionPolicy())
            conf.setEvictionPolicy(new DefaultMapEvictionPolicy());

        if (!conf.hasKeySerializer())
            conf.setKeySerializer(strSerializer);

        if (!conf.hasValueSerializer())
            conf.setValueSerializer(strSerializer);

        return conf;
    }


}
