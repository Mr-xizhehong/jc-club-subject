package com.jingdianjichi.subject.domain.util;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存工具类
 *
 */
@Component
public class CacheUtil<V> {

    private Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .build();


    public List<V> getResult(String cacheKey, Class<V> clazz,
                             Function<String, List<V>> function) {
        List<V> resultList = new LinkedList<>();
        String content = localCache.getIfPresent(cacheKey);
        if (StringUtils.isNotBlank(content)) {
            resultList = JSON.parseArray(content, clazz);
        } else {
            resultList = function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(resultList)) {
                localCache.put(cacheKey, JSON.toJSONString(resultList));
            }
        }
        return resultList;
    }

}
