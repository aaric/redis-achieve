package com.github.aaric.achieve.redis;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

/**
 * RedisClusterTest
 *
 * @author Aaric, created on 2018-04-04T10:29.
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterTest {

    private static final String NS_VEHICLE_VIN = "com.incarcloud.rooster:vehicle-vin:";
    private static final String NS_DEVICE_ID = "com.incarcloud.rooster:device-id:";
    private static final String NS_DEVICE_ONLINE = "com.incarcloud.rooster:device-online:";
    private static final String NS_DEVICE_OFFLINE = "com.incarcloud.rooster:device-offline:";
    private static final String NS_DEVICE_HEARTBEAT = "com.incarcloud.rooster:device-heartbeat:";

    private static final String TEST_VIN = "LSBAAAAAAAB";
    private static final String TEST_DEVICE_CODE = "SWDAAAAAAAB";
    private static final String TEST_DEVICE_ID = "911111112";

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Test
    @Ignore
    public void testOpsForValue() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        for (int i = 0; i < 10; i++) {
            valueOperations.set("hello" + i, "123");
        }
        redisTemplate.keys("*").forEach(key -> {
            redisTemplate.delete(key);
        });
        System.out.println(redisTemplate.keys("*"));
        //redisTemplate.expire("hello", 1, TimeUnit.MINUTES);
    }

    @Test
    @Ignore
    public void testQuery() {
        long history = Calendar.getInstance().getTimeInMillis();
        System.out.println("--begin");
        System.out.println(String.format("Online: %d", redisTemplate.keys(NS_DEVICE_ONLINE + "*").size())); //200000
        System.out.println(String.format("Offline: %d", redisTemplate.keys(NS_DEVICE_OFFLINE + "*").size())); //200001
        System.out.println(String.format("Heartbeat: %d", redisTemplate.keys(NS_DEVICE_HEARTBEAT + "*").size()));//200003
        System.out.println(String.format("Time Diff: %d", Calendar.getInstance().getTimeInMillis() - history)); //14790
    }

    @Test
    public void testDelete() {
        System.out.println("--begin");
        // NS_DEVICE_ONLINE | NS_DEVICE_OFFLINE | NS_DEVICE_HEARTBEAT
        // NS_VEHICLE_VIN | NS_DEVICE_ID
        Set<String> vinSet = redisTemplate.keys(NS_VEHICLE_VIN + "LSBAAAAAAAA2*");
        int count = 0;
        for (String vin : vinSet) {
            if (0 == (++count % 1000)) {
                System.out.println(String.format("Delete Current: %d", count));
                System.out.println(vin);
            }
            redisTemplate.delete(vin);
        }
        System.out.println("--end");
    }

    @Test
    public void testPrint() {
        int maxLength = 200000;
        System.out.println(MessageFormat.format(NS_VEHICLE_VIN + TEST_VIN + "{0,number,000000}", maxLength));
        System.out.println(MessageFormat.format(NS_DEVICE_ID + TEST_DEVICE_ID + "{0,number,000000}", maxLength));
        System.out.println(MessageFormat.format(NS_DEVICE_ONLINE + TEST_VIN + "{0,number,000000}", maxLength));
        System.out.println(MessageFormat.format(NS_DEVICE_OFFLINE + TEST_VIN + "{0,number,000000}", maxLength));
        System.out.println(MessageFormat.format(NS_DEVICE_HEARTBEAT + TEST_VIN + "{0,number,000000}", maxLength));
    }

    @Test
    public void testOpsForHash() {
        int maxLength = 200000;
        String vin;
        String offlineKey = NS_DEVICE_OFFLINE + "list";
        String heartbeatKey = NS_DEVICE_OFFLINE + "list";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = dateFormat.format(Calendar.getInstance().getTime());

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        int count = 0;
        for (int i = 1; i <= maxLength; i++) {
            // vin
            vin = MessageFormat.format(TEST_VIN + "{0,number,000000}", i);

            // print
            if (0 == (++count % 1000)) {
                System.out.println(String.format("Add Current: %d", count));
                System.out.println(vin);
            }

            // Online
            valueOperations.set(NS_DEVICE_ONLINE + vin, current);

            // Offline
            hashOperations.put(offlineKey, vin, current);

            // Heartbeat
            hashOperations.put(heartbeatKey, vin, "{\"time\": \"" + current + "\",\"type\":3}");
        }
        System.out.println("--end");
    }

    @Test
    public void testQuery2() {
        String offlineKey = NS_DEVICE_OFFLINE + "list";
        String heartbeatKey = NS_DEVICE_OFFLINE + "list";
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        long history = Calendar.getInstance().getTimeInMillis();
        System.out.println("--begin");
        System.out.println(String.format("Online: %d", redisTemplate.keys(NS_DEVICE_ONLINE + "*").size())); //200001
        System.out.println(String.format("Offline: %d", hashOperations.size(offlineKey))); //200000
        System.out.println(String.format("Heartbeat: %d", hashOperations.size(heartbeatKey))); //200000
        System.out.println(String.format("Time Diff: %d", Calendar.getInstance().getTimeInMillis() - history)); //5076
        System.out.println("--end");
    }
}
