package com.sky.test;

import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void  test() {
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();



    }

    @Test // 操作字符串
    public  void testSting(){
        // set  get  setex  setnx
        redisTemplate.opsForValue().set("city","北京");
        String object = (String) redisTemplate.opsForValue().get("city");
        System.out.println(object);

        redisTemplate.opsForValue().set("code","1234",3, TimeUnit.MINUTES);

        redisTemplate.opsForValue().setIfAbsent("lock","1");

        redisTemplate.opsForValue().setIfAbsent("lock","2");



    }

    @Test
    public  void  testhash(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("100","name","tom");
        hashOperations.put("100","age","20");

        String name = (String) hashOperations.get("100", "name");
        System.out.println(name);

        List values = hashOperations.values("100");
        System.out.println(values);

        hashOperations.delete("100","age");

    }
}
