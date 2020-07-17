package com.github.aaric.achieve.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * RedisTTLTests
 *
 * @author Aaric, created on 2020-07-17T08:39.
 * @version 0.4.2-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTTLTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSetTtl() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("str1", "hello");
        valueOperations.set("str2", "world", Duration.ofSeconds(100)); //设置值和过期时间
        long ttl = valueOperations.getOperations().getExpire("str2", TimeUnit.SECONDS); //获取过期时间
        valueOperations.getOperations().expire("str2", 1000, TimeUnit.SECONDS); //设置过期时间
        log.info("ttl -> {}", ttl);

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("something", "hello", "world");
        hashOperations.getOperations().expire("something", 1000, TimeUnit.SECONDS); //设置过期时间
    }
}
