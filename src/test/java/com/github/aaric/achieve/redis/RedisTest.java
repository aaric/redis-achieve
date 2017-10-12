package com.github.aaric.achieve.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * RedisTest
 *
 * @author Aaric, created on 2017-10-11T11:54.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Test
    public void testString() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("string", "hello", 1000, TimeUnit.SECONDS);
    }

    @Test
    public void testList() {
        String key = "list";
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, "first");
        listOperations.leftPush(key, "third");
        listOperations.rightPush(key, "second");
        listOperations.rightPush(key, "third");
        redisTemplate.expire(key, 1000, TimeUnit.SECONDS);
    }
}
