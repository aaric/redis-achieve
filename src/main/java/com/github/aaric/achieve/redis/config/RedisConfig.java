package com.github.aaric.achieve.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置
 *
 * @author Aaric, created on 2017-10-11T16:47.
 * @since 0.4.0-SNAPSHOT
 */
@Configuration
public class RedisConfig {

    private StringRedisTemplate template;

    @Autowired
    public RedisConfig(StringRedisTemplate template) {
        this.template = template;
    }

    /**
     * redisTemplate
     *
     * @return
     */
    @Bean
    RedisTemplate<String, String> redisTemplate() {
        // STRING（字符串）、LIST（列表）、SET（集合）、HASH（散列）和ZSET（有序集合）
        return template;
    }
}
