package com.github.aaric.achieve.redis;

import com.github.aaric.achieve.redis.listener.SubscribeMessageListener;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.Random;

/**
 * RedisPubSubQueueTest
 *
 * @author Aaric, created on 2018-09-05T15:03.
 * @since 0.3.0-SNAPSHOT
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisPubSubQueueTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @Disabled
    public void testPublishMessage() {
        String text = MessageFormat.format("{0,number,000000}", new Random().nextInt(999999));
        System.out.println("text: " + text);
        redisTemplate.convertAndSend(SubscribeMessageListener.TEST_CHANNEL, text);
    }
}
