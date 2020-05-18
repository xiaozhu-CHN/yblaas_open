package com.yiban.yblaas.shiro.cache;


import com.yiban.yblaas.util.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: yblaas
 * @description: ShiroCache缓存
 * @author: xiaozhu
 * @create: 2020-04-17 11:26
 **/
@Component
public class ShiroCache<k,v> implements Cache<k,v> {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCache.class);

    @Autowired
    private RedisUtil redisUtil;

    private final String CACHE_PREFIX = "shiro-cache:";

    private String getkey(k k){
        try {
            return CACHE_PREFIX +k;
        } catch (Exception e) {
            logger.error("shiro缓存key值转换错误，错误信息："+e.toString());
            return null;
        }
    }

    private k getresultKey(String k){
        try {
            return (k) k.toString().substring(12);
        } catch (Exception e) {
            logger.error("shiro缓存key值反转错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public v get(k key){
        try {
            //查询
            v val = (v) redisUtil.get(getkey(key));
            if(val != null){
                return val;
            }
            return null;
        } catch (CacheException e) {
            logger.error("查询shiro缓存错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public v put(k key, v value){
        try {
            //增加
            String k = getkey(key);
            redisUtil.set(k, value);
            redisUtil.expire(k, 600);
            return value;
        } catch (CacheException e) {
            logger.error("增加shiro缓存错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public v remove(k key){
        try {
            //删除
            String k = getkey(key);
            v val = (v) redisUtil.get(k);
            redisUtil.del(k);
            if(val != null){
                return val;
            }
            return null;
        } catch (CacheException e) {
            logger.error("删除shiro缓存错误。错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public void clear(){
        try {
            //预防把Redis的所有数据都清空了
            Set<String> keys = redisUtil.keys(CACHE_PREFIX);
            if(!CollectionUtils.isEmpty(keys)){
                //集合不为空
                redisUtil.dels(keys);
            }
        } catch (CacheException e) {
            logger.error("清空shiro缓存错误，错误信息："+e.toString());
        }
    }

    @Override
    public int size() {
        try {
            //返回数量
            return redisUtil.keys(CACHE_PREFIX).size();
        } catch (Exception e) {
            logger.error("返回shiro缓存数量错误，错误信息："+e.toString());
            return 0;
        }
    }

    @Override
    public Set<k> keys() {
        try {
            //查询全部key
            Set<String> keys = redisUtil.keys(CACHE_PREFIX);
            Set<k> resultKeys = new HashSet<>();
            if(CollectionUtils.isEmpty(resultKeys)){
                //如果为空的集合就直接返回
                return resultKeys;
            }
            //如果不是就遍历
            for(String key : keys){
                k resultKey = getresultKey(key);
                resultKeys.add(resultKey);
            }
            return resultKeys;
        } catch (Exception e) {
            logger.error("查询shiro全部key缓存错误，错误信息："+e.toString());
            return new HashSet<>();
        }
    }

    @Override
    public Collection<v> values() {
        try {
            //查询全部
            Set<String> keys = redisUtil.keys(CACHE_PREFIX);
            Set<v> caches = new HashSet<>();
            if(CollectionUtils.isEmpty(keys)){
                //如果为空的集合就直接返回
                return caches;
            }
            //如果不是就遍历
            for(String key : keys){
                v cache = (v) redisUtil.get(key);
                caches.add(cache);
            }
            return caches;
        } catch (Exception e) {
            logger.error("查询shiro全部value错误，错误信息："+e.toString());
            return new HashSet<>();
        }
    }
}
