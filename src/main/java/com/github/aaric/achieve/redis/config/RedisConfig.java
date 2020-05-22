package com.github.aaric.achieve.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;

/**
 * Redis配置
 *
 * @author Aaric, created on 2017-10-11T16:47.
 * @since 0.4.0-SNAPSHOT
 */
@Configuration
public class RedisConfig {

    /**
     * 订阅频道
     */
    public static final String TEST_CHANNEL = "message:channel";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public ValueOperations<String, String> valueOperations() {
        // STRING（字符串）
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, String> listOperations() {
        // LIST（列表）
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, String> setOperations() {
        // SET（集合）
        return redisTemplate.opsForSet();
    }

    @Bean
    public HashOperations<String, String, String> hashOperations() {
        // HASH（散列）
        return redisTemplate.opsForHash();
    }

    @Bean
    public ZSetOperations<String, String> zSetOperations() {
        // ZSET（有序集合）
        return redisTemplate.opsForZSet();
    }

    public GeoOperations<String, String> geoOperations() {
        // GEO（地理）
        return redisTemplate.opsForGeo();
    }
}
