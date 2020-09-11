package com.yiban.yblaas.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: yblaas
 * @description: redis工具类
 * @author: xiaozhu
 * @create: 2020-04-17 11:11
 **/
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        //存值
        redisTemplate.opsForValue().set(key, value);
        //stringRedisTemplate.opsForValue().set(new String(key), new String(value), i, TimeUnit.SECONDS);
    }

    public void hashSet(String key, Object hashKey, Object hashValue){
        //增
        redisTemplate.boundHashOps(key).put(hashKey, hashValue);
    }

    public void expire(String key, int i) {
        //设置超时时间
        //三个参数 分别是key 时间 时间类型（时分秒等）
        redisTemplate.expire(key, i, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        //取值
        return redisTemplate.opsForValue().get(key);
    }

    public Object hashGet(String key,Object hashKey) {
        //hsh取值
        return redisTemplate.boundHashOps(key).get(hashKey);
    }


    public void del(String key) {
        //删值
        redisTemplate.delete(key);
    }

    public void hashDel(String key, Object hashKey){
        //hash删除
        redisTemplate.boundHashOps(key).delete(hashKey);
    }

    public Set<String> keys(String prefix) {
        //查询所有的key
        return redisTemplate.keys(prefix + "*");
    }

    public int hashSize(String key){
        //hash查询所有的key
        Long size = redisTemplate.boundHashOps(key).size();
        return size == null ? 0 : size.intValue();
    }

    public void dels(Set<String> keys){
        //批量删值
        redisTemplate.delete(keys);
    }
}
