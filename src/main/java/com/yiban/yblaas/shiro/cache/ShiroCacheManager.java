package com.yiban.yblaas.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: yblaas
 * @description: shiro的cacheManager实现
 * @author: xiaozhu
 * @create: 2020-04-17 13:05
 **/
@Component
public class ShiroCacheManager implements CacheManager {

    @Resource
    private ShiroCache shiroCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroCache;
    }
}
