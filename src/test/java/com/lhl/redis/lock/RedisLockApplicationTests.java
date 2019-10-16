package com.lhl.redis.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis(){
        // 写入一个值
        stringRedisTemplate.opsForValue().set("test", "test data");
        // 测试获取值
        String test = stringRedisTemplate.opsForValue().get("test");
        System.out.println(test);
    }

}
