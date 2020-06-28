package com.github.aaric.achieve.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RedisTest
 *
 * @author Aaric, created on 2017-10-11T11:54.
 * @since 0.1.0-SNAPSHOT
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTest {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Test
    public void testBizOnline() {
        String vin = "LFV2A21J970002020"; //LVGBPB9E7KG006111
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        System.err.println("# " + vin);
        //hashOperations.delete("boar:vehicle-offline", vin);
        System.err.println("boar:vehicle-offline    -> " + hashOperations.get("boar:vehicle-offline", vin));
        System.err.println("boar:vehicle-exception  -> " + hashOperations.get("boar:vehicle-exception", vin));
    }

    @Test
    public void testBizKey() {
        String deviceId = "KEYTEST052"; //YK12181287,YK12014921
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        System.err.println("# " + deviceId);
        System.err.println("boar:device-id    -> " + hashOperations.get("boar:device-id", deviceId));
        System.err.println("boar:device-key   -> " + hashOperations.get("boar:device-key", deviceId));
        System.err.println("boar:device-model -> " + hashOperations.get("boar:device-model", deviceId));

        //hashOperations.put("boar:device-id", deviceId, "TESTKDL0000000001");
        //hashOperations.put("boar:device-key", deviceId, "MDEyMzQ1Njc4OWFiY2RlZg==");
        //hashOperations.put("boar:device-model", deviceId, "{\"seriesCode\":0,\"yearCode\":13,\"brandCode\":86,\"displacementCode\":0}");
    }

    @Test
    public void testBizRedis() {
        String vin = "CS123456720242617";
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Map<String, String> map = hashOperations.entries("boar:device-id");
        map.forEach((key, value) -> {
            if (value.equals(vin)) {
                System.err.println(key + " : " + value);
            } else {
                System.out.println(key + " : " + value);
            }
        });
    }

    @Test
    @Disabled
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
    @Disabled
    public void testAddNamespace() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("com.github:aaric:hello", "hello world", 1000, TimeUnit.SECONDS);
    }

    @Test
    @Disabled
    public void testRequirePass() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String sizeString = valueOperations.get("hello:index");
        System.out.println(sizeString);
        Assertions.assertNotNull(sizeString);
    }
}
