package com.github.aaric.achieve.redis.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * SubscribeMessageListener
 *
 * @author Aaric, created on 2018-09-05T15:13.
 * @since 0.3.0-SNAPSHOT
 */
@Configuration
public class SubscribeMessageListener implements MessageListener {

    public static final String TEST_CHANNEL = "message:channel";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String result = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topic = (String) redisTemplate.getValueSerializer().deserialize(message.getChannel());
        System.out.println(topic + ": " + result);
    }
}
