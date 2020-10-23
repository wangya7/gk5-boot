package wang.bannong.gk5.boot.redis.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.Resource;

import wang.bannong.gk5.cache.AbstractCacheManager;

@Service("hashCacheManager")
public class HashRedisPersistMgrImpl extends AbstractCacheManager {
    
    @Resource
    private RedisTemplate<String, Serializable> hashRedisPersistTemp;

    @Override
    public void hashPut(String key, String hashKey, Serializable value) {
        hashRedisPersistTemp.opsForHash().put(key, hashKey, value);
    }

    @Override
    public long hashDel(String key, Object... hashKeys) {
        return hashRedisPersistTemp.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Serializable hashGet(String key, String hashKey) {
        return (Serializable)hashRedisPersistTemp.opsForHash().get(key, hashKey);
    }

    @Override
    public boolean hasKey(String key, String hashKey) {
        return hashRedisPersistTemp.opsForHash().hasKey(key, hashKey);
    }
    @Override
    public Set<Object> hashKeys(String key) {
        return hashRedisPersistTemp.opsForHash().keys(key);
    }

}
