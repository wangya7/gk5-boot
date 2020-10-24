package wang.bannong.gk5.boot.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public abstract class AbstractCacheOpr implements CacheOpr {

    /**
     * @param key
     * @param value
     */
    public <T> CacheResult<T> put(String key, Serializable value) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * @param key
     * @param value
     * @param expire seconds
     */
    public <T> CacheResult<T> put(String key, Serializable value, long expire) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    public <T extends Serializable> CacheResult<T> getObject(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    public CacheResult<String> getString(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * @param key
     * @param expire 秒
     */
    public void expire(String key, long expire) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * @param key
     * @param expireDate 日期
     */
    public void expireAt(String key, Date expireDate) {
        throw new RuntimeException("未实现的方法...");
    }

    public void del(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    public void delBatch(String pattern) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * pv自增
     *
     * @param namespace
     * @param key
     */
    public long incPv(String namespace, String key) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 获取pv
     *
     * @param namespace
     * @param key
     * @return
     */
    public Long getPv(String namespace, String key) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 释放空间
     *
     * @param namespace
     * @param key
     * @return
     */
    public Long getAndDelPv(String namespace, String key) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 自增
     *
     * @param key
     * @return 自增后的值
     */
    public long incr(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    public void hput(String key, String hashKey, Long value) {
        throw new RuntimeException("未实现的方法...");
    }

    public long hdel(String key, Object... hashKeys) {
        return 0;
    }

    public CacheResult<Long> hget(String key, String hashKey) {
        throw new RuntimeException("未实现的方法...");
    }

    public void hashPut(String key, String hashKey, Serializable value) {
        throw new RuntimeException("未实现的方法...");
    }

    public long hashDel(String key, Object... hashKeys) {
        return 0;
    }

    public Serializable hashGet(String key, String hashKey) {
        throw new RuntimeException("未实现的方法...");
    }

    public boolean hasKey(String key, String hashKey) {
        throw new RuntimeException("未实现的方法...");
    }

    public Set<Object> hashKeys(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    public boolean setnx(String key, Long value) {
        throw new RuntimeException("未实现的方法...");
    }

    public boolean setnx(String key, String value) {
        throw new RuntimeException("未实现的方法...");
    }

    public Set<String> keys(String keyPattern) {
        throw new RuntimeException("未实现的方法...");
    }

    public long getExpire(String key) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 该Set中是否存在该元素
     *
     * @param setName Set的名称
     * @param value   要判断的元素
     * @return
     */
    public boolean isMemberOfSet(String setName, Serializable value) {
        throw new RuntimeException("未实现的方法...");
    }

    /**
     * 向Set中添加元素
     *
     * @param setName Set的名称
     * @param values  要添加的元素
     * @return
     */
    public long addToSet(String setName, Serializable... values) {
        throw new RuntimeException("未实现的方法...");
    }

    public long delFromSet(String setName, Serializable value) {
        throw new RuntimeException("未实现的方法...");
    }

    public long sizeOfSet(String setName) {
        throw new RuntimeException("未实现的方法...");
    }

    public Set<Serializable> membersOfSet(String setName) {
        throw new RuntimeException("未实现的方法...");
    }

    @Override
    public Map<Object, Object> entries(String key) {

        throw new RuntimeException("未实现的方法...");
    }

}
