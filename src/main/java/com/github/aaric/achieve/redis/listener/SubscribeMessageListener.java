package com.github.aaric.achieve.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * SubscribeMessageListener
 *
 * @author Aaric, created on 2018-09-05T15:13.
 * @since 0.3.0-SNAPSHOT
 */
@Slf4j
@Component
public class SubscribeMessageListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("hello listener...");
        String result = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topic = (String) redisTemplate.getValueSerializer().deserialize(message.getChannel());
        log.info("topic: {}, result: {}", topic, result);
    }
}
