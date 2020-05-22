package com.github.aaric.achieve.redis.config;

import com.github.aaric.achieve.redis.listener.SubscribeMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

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
    ValueOperations<String, String> valueOperations() {
        return redisTemplate.opsForValue();
    }

    @Bean
    ListOperations<String, String> listOperations() {
        return redisTemplate.opsForList();
    }

    @Bean
    SetOperations<String, String> setOperations() {
        return redisTemplate.opsForSet();
    }

    @Bean
    HashOperations<String, String, String> hashOperations() {
        return redisTemplate.opsForHash();
    }

    @Bean
    ZSetOperations<String, String> zSetOperations() {
        return redisTemplate.opsForZSet();
    }

    @Bean
    GeoOperations<String, String> geoOperations() {
        return redisTemplate.opsForGeo();
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, SubscribeMessageListener subscribeMessageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscribeMessageListener, new PatternTopic(TEST_CHANNEL));
        return container;
    }
}
