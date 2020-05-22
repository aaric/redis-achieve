package com.github.aaric.achieve.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * RedisTest
 *
 * @author Aaric, created on 2017-10-11T11:54.
 * @since 1.0-SNAPSHOT
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTest {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSetString() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("string", "hello", 1000, TimeUnit.SECONDS);
    }

    @Test
    public void testSetList() {
        String key = "list";
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, "first");
        listOperations.leftPush(key, "third");
        listOperations.rightPush(key, "second");
        listOperations.rightPush(key, "third");
        redisTemplate.expire(key, 1000, TimeUnit.SECONDS);
    }

    @Test
    public void testAddNamespace() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("com.github:aaric:hello", "hello world", 1000, TimeUnit.SECONDS);
    }
}
