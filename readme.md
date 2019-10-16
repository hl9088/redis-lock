# redis 分布式锁的实现

> 最近面试过程中，被问到了如何使用redis实现分布式锁，开始是在百度搜索了一些博客，了解到一个setNx(key, value)的方法，
然后结合get(key)、getSet(key, value)方法就可以实现分布式锁。但是后来再次被问到具体如何实现时，才发现可能还是有点问题的，
所以还是实践一下比较好，框架就用springboot，比较方便

#### 搭建redis环境
1. 可以直接下载windows版本解压、运行redis-server.exe即可，参考下载地址https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100
2. 去redis官网（https://redis.io/）下载，参照示例解压、编译即可

> 本次使用方式1，直接在Windows平台上搭建redis环境

#### 搭建项目框架，引入redis的依赖，配置redis
~~~
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

# redis基础连接配置
spring.redis.host=localhost
spring.redis.port=6379
~~~

> 测试redis连接是否正常:在springboot测试类中添加以下代码，简单测试
~~~
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
~~~

#### 分布式锁的实现