package wang.bannong.gk5.boot.starter.redis.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import wang.bannong.gk5.boot.starter.redis.AbstractCacheOpr;
import wang.bannong.gk5.boot.starter.redis.CacheErrorEnum;
import wang.bannong.gk5.boot.starter.redis.CacheResult;

@Service("cachePersistentOpr")
public class RedisPersistentOprImpl extends AbstractCacheOpr {
    @Resource
    private StringRedisTemplate stringRedisPersistentTemplate;
    @Resource
    private RedisTemplate<String, Serializable> redisPersistentTemplate;

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
                stringRedisPersistentTemplate.opsForValue().set(key, (String) value);
            } else {
                redisPersistentTemplate.opsForValue().set(key, value);
            }
        } else {
            if (value instanceof String) {
                stringRedisPersistentTemplate.opsForValue().set(key, (String) value, expire, TimeUnit.SECONDS);
            } else {
                redisPersistentTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            }
        }
        return CacheResult.of(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> CacheResult<T> getObject(String key) {
        T value = (T) redisPersistentTemplate.opsForValue().get(key);
        return CacheResult.of(true, value);
    }

    @Override
    public CacheResult<String> getString(String key) {
        String value = stringRedisPersistentTemplate.opsForValue().get(key);
        return CacheResult.of(true, value);
    }

    @Override
    public void del(String key) {
        redisPersistentTemplate.delete(key);
    }

    @Override
    public void expire(String key, long expire) {
        redisPersistentTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void expireAt(String key, Date date) {
        redisPersistentTemplate.expireAt(key, date);
    }

    @Override
    public long incPv(String namespace, String key) {
        return redisPersistentTemplate.opsForHash().increment(namespace, key, 1);
    }

    @Override
    public Long getPv(String namespace, String key) {
        return (Long) redisPersistentTemplate.opsForHash().get(namespace, key);
    }

    @Override
    public Long getAndDelPv(String namespace, String key) {
        Long value = (Long) redisPersistentTemplate.opsForHash().get(namespace, key);
        if (value != null) {
            redisPersistentTemplate.opsForHash().delete(namespace, key);
        }
        return value;
    }

    @Override
    public long incr(String key) {
        return redisPersistentTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public void hput(String key, String hashKey, Long value) {
        redisPersistentTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public long hdel(String key, Object... hashKeys) {
        return redisPersistentTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public CacheResult<Long> hget(String key, String hashKey) {
        Long value = (Long) redisPersistentTemplate.opsForHash().get(key, hashKey);
        return CacheResult.of(true, value);
    }

    @Override
    public boolean setnx(String key, Long value) {
        return redisPersistentTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public boolean setnx(String key, String value) {
        return redisPersistentTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Set<String> keys(String keyPattern) {
        return redisPersistentTemplate.keys(keyPattern);
    }

    @Override
    public long getExpire(String key) {
        Long expire = redisPersistentTemplate.getExpire(key);
        if (expire == null) {
            return 0L;
        }

        return expire.longValue();
    }

    public boolean isMemberOfSet(String setName, Serializable value) {
        return redisPersistentTemplate.opsForSet().isMember(setName, value);
    }

    public long addToSet(String setName, Serializable... values) {
        return redisPersistentTemplate.opsForSet().add(setName, values);
    }

    @Override
    public long delFromSet(String setName, Serializable value) {
        return redisPersistentTemplate.opsForSet().remove(setName, value);
    }

    public Set<Serializable> membersOfSet(String setName) {
        return redisPersistentTemplate.opsForSet().members(setName);
    }
}
