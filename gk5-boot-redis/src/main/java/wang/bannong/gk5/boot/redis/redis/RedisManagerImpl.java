package wang.bannong.gk5.boot.redis.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import wang.bannong.gk5.cache.AbstractCacheManager;
import wang.bannong.gk5.cache.CacheErrorEnum;
import wang.bannong.gk5.cache.CacheResult;

@Service("cacheManager")
public class RedisManagerImpl extends AbstractCacheManager {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public <T> CacheResult<T> put(String key, Serializable value) {

        return put(key, value, -1);
    }

    @Override
    public <T> CacheResult<T> put(String key, Serializable value, long expire) {
        if (key == null || key.trim().isEmpty()) {
            return CacheResult.of(CacheErrorEnum.key_cannot_empty.name());
        }
        if (expire <= 0) {
            // 数据不过期
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(key, (String) value);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        } else {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(key, (String) value, expire, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            }
        }
        return CacheResult.of(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> CacheResult<T> getObject(String key) {
        T value = (T) redisTemplate.opsForValue().get(key);
        return CacheResult.of(true, value);
    }

    @Override
    public CacheResult<String> getString(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return CacheResult.of(true, value);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delBatch(String pattern) {
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    @Override
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public long incPv(String namespace, String key) {
        return redisTemplate.opsForHash().increment(namespace, key, 1);
    }

    @Override
    public Long getPv(String namespace, String key) {
        return (Long) redisTemplate.opsForHash().get(namespace, key);
    }

    @Override
    public Long getAndDelPv(String namespace, String key) {
        Long value = (Long) redisTemplate.opsForHash().get(namespace, key);
        if (value != null) {
            redisTemplate.opsForHash().delete(namespace, key);
        }
        return value;
    }

    @Override
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public void hput(String key, String hashKey, Long value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public long hdel(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public CacheResult<Long> hget(String key, String hashKey) {
        Long value = (Long) redisTemplate.opsForHash().get(key, hashKey);
        return CacheResult.of(true, value);
    }

    @Override
    public boolean setnx(String key, Long value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Set<String> keys(String keyPattern) {
        return redisTemplate.keys(keyPattern);
    }

    @Override
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        if (expire == null) {
            return 0L;
        }

        return expire.longValue();
    }

    @Override
    public long addToSet(String setName, Serializable... values) {
        return redisTemplate.opsForSet().add(setName, values);
    }

    @Override
    public long delFromSet(String setName, Serializable value) {
        return redisTemplate.opsForSet().remove(setName, value);
    }

    @Override
    public long sizeOfSet(String setName) {
        return redisTemplate.opsForSet().size(setName);
    }

    @Override
    public boolean isMemberOfSet(String setName, Serializable value) {
        return redisTemplate.opsForSet().isMember(setName, value);
    }

    @Override
    public Map<Object, Object> entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
