package com.lhl.redis.lock.demo;

// 用来模拟线程去操作的对象
public class Data {

    private Integer count;

    public Data() {
    }

    public Data(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
