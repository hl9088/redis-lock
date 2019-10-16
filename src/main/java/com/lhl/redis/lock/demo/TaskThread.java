package com.lhl.redis.lock.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来模拟去争抢redis锁 操作数据的任务线程
 */
public class TaskThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(TaskThread.class);

    // 线程的名称
    private String name;

    // redis操作对象
    private RedisLockUtil redisLockUtil;

    public TaskThread(String name, RedisLockUtil redisLockUtil) {
        this.name = name;
        this.redisLockUtil = redisLockUtil;
    }

    @Override
    public void run() {
        while(true){
            long value = System.currentTimeMillis() + 3000;
            if(redisLockUtil.tryLock("count", String.valueOf(value))){
                // 获取到锁 对data对象的count值减一 模拟是操作数据库
                logger.info("{}, {}", name, redisLockUtil.count("optObject3"));
                // 解锁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("{}解锁", name);
                redisLockUtil.unlock("count", String.valueOf(value));
                return;
            }
        }
    }
}
