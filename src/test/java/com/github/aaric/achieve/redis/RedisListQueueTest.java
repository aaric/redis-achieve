package com.github.aaric.achieve.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.Random;

/**
 * RedisListQueueTest
 *
 * @author Aaric, created on 2018-09-05T14:11.
 * @since 0.3.0-SNAPSHOT
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisListQueueTest {

    private static final String TEST_QUEUE = "message:queue";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @Disabled
    public void testLPush() {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();

        String text;
        for (int i = 0; i < 10; i++) {
            text = MessageFormat.format("{0,number,000000}", new Random().nextInt(999999));
            System.out.println("text: " + text);
            listOperations.leftPush(TEST_QUEUE, text);
        }

        long count = listOperations.size(TEST_QUEUE);
        System.out.println("count: " + count);
        Assertions.assertNotEquals(0L, count);
    }

    @Test
    @Disabled
    public void testRPop() {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();

        String result;
        long count = listOperations.size(TEST_QUEUE);
        for (int i = 0; i < count; i++) {
            result = listOperations.rightPop(TEST_QUEUE);
            System.out.println("result: " + result);
        }

        count = listOperations.size(TEST_QUEUE);
        Assertions.assertEquals(0L, count);
    }
}
