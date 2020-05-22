package com.github.aaric.achieve.redis.runner;

import com.github.aaric.achieve.redis.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * com.github.aaric.achieve.redis.runner
 *
 * @author Aaric, created on 2020-05-22T10:56.
 * @version 0.1.0-SNAPSHOT
 */
@Slf4j
@Order(1)
@Component
public class RedisRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("hello redis...");
        redisTemplate.convertAndSend(RedisConfig.TEST_CHANNEL, "hello redis");
    }
}
