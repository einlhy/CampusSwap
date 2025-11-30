package com.lhy.campusswap.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisCache {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final ListOperations<String, Object> listOps;
    private final SetOperations<String, Object> setOps;
    private final HashOperations<String, String, Object> hashOps;
    private final ZSetOperations<String, Object> zSetOps;

    @Autowired
    public RedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
        this.hashOps = redisTemplate.opsForHash();
        this.zSetOps = redisTemplate.opsForZSet();
    }

    // ============================== Common ==============================

    /**
     * 判断key是否存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            throw new RedisOperationException("判断key是否存在失败: " + key, e);
        }
    }

    /**
     * 删除key
     */
    public boolean delete(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(key));
        } catch (Exception e) {
            throw new RedisOperationException("删除key失败: " + key, e);
        }
    }

    /**
     * 批量删除key
     */
    public long delete(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        try {
            Long count = redisTemplate.delete(keys);
            return count == null ? 0L : count;
        } catch (Exception e) {
            throw new RedisOperationException("批量删除key失败", e);
        }
    }

    /**
     * 设置过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
        } catch (Exception e) {
            throw new RedisOperationException("设置过期时间失败: " + key, e);
        }
    }

    /**
     * 获取过期时间
     */
    public long getExpire(String key, TimeUnit unit) {
        try {
            Long expire = redisTemplate.getExpire(key, unit);
            return expire == null ? -2L : expire;
        } catch (Exception e) {
            throw new RedisOperationException("获取过期时间失败: " + key, e);
        }
    }

    /**
     * 查找匹配的key
     */
    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            throw new RedisOperationException("查找key失败: " + pattern, e);
        }
    }

    // ============================== String Operations ==============================

    /**
     * 缓存对象
     */
    public <T> void set(String key, T value) {
        try {
            valueOps.set(key, value);
        } catch (Exception e) {
            throw new RedisOperationException("设置缓存失败: " + key, e);
        }
    }

    /**
     * 缓存对象并设置过期时间
     */
    public <T> void set(String key, T value, long timeout, TimeUnit unit) {
        try {
            valueOps.set(key, value, timeout, unit);
        } catch (Exception e) {
            throw new RedisOperationException("设置缓存失败: " + key, e);
        }
    }

    /**
     * 只有key不存在时设置缓存
     */
    public <T> boolean setIfAbsent(String key, T value, long timeout, TimeUnit unit) {
        try {
            return Boolean.TRUE.equals(valueOps.setIfAbsent(key, value, timeout, unit));
        } catch (Exception e) {
            throw new RedisOperationException("设置缓存失败: " + key, e);
        }
    }

    /**
     * 获取缓存对象
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        try {
            return (T) valueOps.get(key);
        } catch (Exception e) {
            throw new RedisOperationException("获取缓存失败: " + key, e);
        }
    }

    /**
     * 获取缓存对象，如果不存在返回默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        try {
            T value = (T) valueOps.get(key);
            return value != null ? value : defaultValue;
        } catch (Exception e) {
            throw new RedisOperationException("获取缓存失败: " + key, e);
        }
    }

    /**
     * 原子递增
     */
    public long increment(String key, long delta) {
        try {
            Long value = valueOps.increment(key, delta);
            return value == null ? 0L : value;
        } catch (Exception e) {
            throw new RedisOperationException("原子递增失败: " + key, e);
        }
    }

    /**
     * 原子递减
     */
    public long decrement(String key, long delta) {
        try {
            Long value = valueOps.decrement(key, delta);
            return value == null ? 0L : value;
        } catch (Exception e) {
            throw new RedisOperationException("原子递减失败: " + key, e);
        }
    }

    // ============================== List Operations ==============================

    /**
     * 缓存List数据
     */
    public <T> long pushAll(String key, List<T> values) {
        if (CollectionUtils.isEmpty(values)) {
            return 0L;
        }
        try {
            Long count = listOps.rightPushAll(key, values.toArray());
            return count == null ? 0L : count;
        } catch (Exception e) {
            throw new RedisOperationException("缓存List失败: " + key, e);
        }
    }

    /**
     * 获取List范围
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> listRange(String key, long start, long end) {
        try {
            return (List<T>) listOps.range(key, start, end);
        } catch (Exception e) {
            throw new RedisOperationException("获取List范围失败: " + key, e);
        }
    }

    /**
     * 获取整个List
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        try {
            return (List<T>) listOps.range(key, 0, -1);
        } catch (Exception e) {
            throw new RedisOperationException("获取List失败: " + key, e);
        }
    }

    /**
     * 获取List长度
     */
    public long listSize(String key) {
        try {
            Long size = listOps.size(key);
            return size == null ? 0L : size;
        } catch (Exception e) {
            throw new RedisOperationException("获取List长度失败: " + key, e);
        }
    }

    // ============================== Hash Operations ==============================

    /**
     * 缓存Hash
     */
    public <T> void putAll(String key, Map<String, T> map) {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        try {
            hashOps.putAll(key, (Map<String, Object>) map);
        } catch (Exception e) {
            throw new RedisOperationException("缓存Hash失败: " + key, e);
        }
    }

    /**
     * 获取整个Hash
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> entries(String key) {
        try {
            return (Map<String, T>) hashOps.entries(key);
        } catch (Exception e) {
            throw new RedisOperationException("获取Hash失败: " + key, e);
        }
    }

    /**
     * 设置Hash字段值
     */
    public <T> void put(String key, String field, T value) {
        try {
            hashOps.put(key, field, value);
        } catch (Exception e) {
            throw new RedisOperationException("设置Hash字段失败: " + key + "." + field, e);
        }
    }

    /**
     * 获取Hash字段值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, String field) {
        try {
            return (T) hashOps.get(key, field);
        } catch (Exception e) {
            throw new RedisOperationException("获取Hash字段失败: " + key + "." + field, e);
        }
    }

    /**
     * 删除Hash字段
     */
    public long delete(String key, String... fields) {
        if (fields == null || fields.length == 0) {
            return 0L;
        }
        try {
            return hashOps.delete(key, (Object[]) fields);
        } catch (Exception e) {
            throw new RedisOperationException("删除Hash字段失败: " + key, e);
        }
    }

    /**
     * 判断Hash字段是否存在
     */
    public boolean hasKey(String key, String field) {
        try {
            return hashOps.hasKey(key, field);
        } catch (Exception e) {
            throw new RedisOperationException("判断Hash字段是否存在失败: " + key + "." + field, e);
        }
    }

    // ============================== Set Operations ==============================

    /**
     * 添加Set元素
     */
    public <T> long add(String key, T... values) {
        if (values == null || values.length == 0) {
            return 0L;
        }
        try {
            Long count = setOps.add(key, values);
            return count == null ? 0L : count;
        } catch (Exception e) {
            throw new RedisOperationException("添加Set元素失败: " + key, e);
        }
    }

    /**
     * 获取整个Set
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> members(String key) {
        try {
            return (Set<T>) setOps.members(key);
        } catch (Exception e) {
            throw new RedisOperationException("获取Set失败: " + key, e);
        }
    }

    /**
     * 判断是否是Set成员
     */
    public <T> boolean isMember(String key, T value) {
        try {
            return Boolean.TRUE.equals(setOps.isMember(key, value));
        } catch (Exception e) {
            throw new RedisOperationException("判断Set成员失败: " + key, e);
        }
    }

    // ============================== ZSet Operations ==============================

    /**
     * 添加ZSet元素
     */
    public <T> boolean add(String key, T value, double score) {
        try {
            return Boolean.TRUE.equals(zSetOps.add(key, value, score));
        } catch (Exception e) {
            throw new RedisOperationException("添加ZSet元素失败: " + key, e);
        }
    }

    /**
     * 获取ZSet范围
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> zsetRange(String key, long start, long end) {
        try {
            return (Set<T>) zSetOps.range(key, start, end);
        } catch (Exception e) {
            throw new RedisOperationException("获取ZSet范围失败: " + key, e);
        }
    }

    // ============================== 分布式锁 ==============================

    /**
     * 尝试获取分布式锁
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime, TimeUnit unit) {
        try {
            return Boolean.TRUE.equals(valueOps.setIfAbsent(lockKey, requestId, expireTime, unit));
        } catch (Exception e) {
            throw new RedisOperationException("获取分布式锁失败: " + lockKey, e);
        }
    }

    /**
     * 释放分布式锁
     */
    public boolean releaseLock(String lockKey, String requestId) {
        try {
            String currentValue = (String) valueOps.get(lockKey);
            if (requestId.equals(currentValue)) {
                return delete(lockKey);
            }
            return false;
        } catch (Exception e) {
            throw new RedisOperationException("释放分布式锁失败: " + lockKey, e);
        }
    }
}
/**
 * Redis操作异常
 */
class RedisOperationException extends RuntimeException {
    public RedisOperationException(String message) {
        super(message);
    }

    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}