package com.lhl.redis.lock.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final long EXPIRE_TIME = 10000;

    public boolean tryLock(String key, String value){
        if(stringRedisTemplate.opsForValue().setIfAbsent(key, value, EXPIRE_TIME, TimeUnit.SECONDS)){
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(currentValue) && Long.valueOf(currentValue) < System.currentTimeMillis()){
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String value){
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        try {
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 模拟操作数据对象
    public Integer count(String key){
        String value = stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(value)){
            value = "0";
        }
        stringRedisTemplate.opsForValue().set(key, String.valueOf(Integer.valueOf(value) + 1));
        return Integer.valueOf(value) + 1;
    }
}
