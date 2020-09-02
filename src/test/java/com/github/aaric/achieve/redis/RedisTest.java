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

import java.text.MessageFormat;
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
    public void testBatteryVoltage() {
        // KEYTEST000001 | TESTGPS0000000001
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        String key = "business:alarmVoltageThreshold";
        String deviceId = "KEYTEST01"; //KEYTEST000001
        String hashKey = "12:31"; //86:0

        //hashOperations.entries(key).forEach((k, v) -> System.err.println(k + " -> " + v));
        System.err.println(hashOperations.get("boar:device-model", deviceId));
        System.err.println(hashOperations.get("boar:vehicle-alarm", deviceId));

        //hashOperations.put(key, hashKey, "16,17");
        System.err.println(hashOperations.get(key, hashKey));
    }

    @Test
    public void testBizIsr() {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        // jtt808
        for (int i = 0; i < 10; i++) {
            String vin = MessageFormat.format("TESTGPS0000{0,number,000000}", i + 1);
            String deviceId = MessageFormat.format("91111{0,number,000000}", i + 1);
            System.err.println(vin + ":" + deviceId);

            hashOperations.put("boar:device-id", deviceId, vin);
        }

        // tbox
        for (int i = 0; i < 10; i++) {
            String vin = MessageFormat.format("TESTBOX0000{0,number,000000}", i + 1);
            String deviceId = MessageFormat.format("KEYTEST{0,number,000000}", i + 1);
            System.err.println(vin + ":" + deviceId);

            hashOperations.put("boar:device-id", deviceId, vin);
            hashOperations.put("boar:device-key", deviceId, "MDEyMzQ1Njc4OWFiY2RlZg==");
            hashOperations.put("boar:device-model", deviceId, "{\"seriesCode\":0,\"yearCode\":19,\"brandCode\":86,\"displacementCode\":0}");
        }
    }

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
        String deviceId = "911111100001"; //YK12181287,YK12014921
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        System.err.println("# " + deviceId);
        System.err.println("boar:device-id    -> " + hashOperations.get("boar:device-id", deviceId));
        System.err.println("boar:device-key   -> " + hashOperations.get("boar:device-key", deviceId));
        System.err.println("boar:device-model -> " + hashOperations.get("boar:device-model", deviceId));

        //hashOperations.put("boar:device-id", deviceId, "TESTKDL0000000001");
        //hashOperations.put("boar:device-key", deviceId, "MDEyMzQ1Njc4OWFiY2RlZg==");
        //hashOperations.put("boar:device-model", deviceId, "{\"seriesCode\":0,\"yearCode\":19,\"brandCode\":86,\"displacementCode\":0}");
    }

    @Test
    public void testBizRedis() {
        String vin = "HLFTEST0000000010";
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
