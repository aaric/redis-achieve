package com.github.aaric.achieve.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * RedisClusterTest
 *
 * @author Aaric, created on 2018-04-04T10:29.
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterTest {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Test
    public void testOpsForValue() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("hello", "123");
        redisTemplate.expire("hello", 1, TimeUnit.MINUTES);
    }
}
