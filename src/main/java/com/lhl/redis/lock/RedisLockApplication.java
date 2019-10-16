package com.lhl.redis.lock;

import com.lhl.redis.lock.demo.Data;
import com.lhl.redis.lock.demo.RedisLockUtil;
import com.lhl.redis.lock.demo.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class RedisLockApplication implements CommandLineRunner {

    @Autowired
    private RedisLockUtil redisLockUtil;


    public static void main(String[] args) {
        SpringApplication.run(RedisLockApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // 创建任务线程
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new TaskThread("task" + i, redisLockUtil));
//            new Thread(new TaskThread("task" + i, redisLockUtil)).start();
        }
        executorService.shutdown();
    }
}
