package wang.bannong.gk5.boot.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface CacheManager {

    /**
     * @param key
     * @param value
     */
    <T> CacheResult<T> put(String key, Serializable value);

    /**
     * @param key
     * @param value
     * @param expire seconds
     */
    <T> CacheResult<T> put(String key, Serializable value, long expire);

    /**
     * 获取对象
     * 
     * @param key
     * @return
     */
    <T extends Serializable> CacheResult<T> getObject(String key);

    CacheResult<String> getString(String key);

    /**
     * @param key
     * @param expire 秒
     */
    void expire(String key, long expire);

    /**
     * @param key
     * @param expireDate 日期
     */
    void expireAt(String key, Date expireDate);

    void del(String key);

    void delBatch(String pattern);

    /**
     * pv自增
     * 
     * @param namespace
     * @param key
     */
    long incPv(String namespace, String key);

    /**
     * 获取pv
     * 
     * @param namespace
     * @param key
     * @return
     */
    Long getPv(String namespace, String key);

    /**
     * 释放空间
     * 
     * @param namespace
     * @param key
     * @return
     */
    Long getAndDelPv(String namespace, String key);

    /**
     * 自增
     * 
     * @param key
     * @return 自增后的值
     */
    long incr(String key);

    void hput(String key, String hashKey, Long value);
    
    long hdel(String key, Object... hashKeys);

    CacheResult<Long> hget(String key, String hashKey);
    
    // 上面的hput只能存放Long类型的值，
    // 由于redis中已存在的数据，接口改造困难，这里新加一套hash接口
    void hashPut(String key, String hashKey, Serializable value);
    long hashDel(String key, Object... hashKeys);
    Serializable hashGet(String key, String hashKey);
    boolean hasKey(String key, String hashKey);
    Set<Object> hashKeys(String key);
    
    boolean setnx(String key, Long value);

    boolean setnx(String key, String value);

    Set<String> keys(String keyPattern);

    long getExpire(String key);
    /**
     * 该Set中是否存在该元素
     * @param setName Set的名称
     * @param value  要判断的元素
     * @return
     */
    boolean isMemberOfSet(String setName, Serializable value);
    /**
     * 向Set中添加元素
     * @param setName Set的名称
     * @param values 要添加的元素
     * @return
     */
    long addToSet(String setName, Serializable... values);
    
    long delFromSet(String setName, Serializable value);
    
    long sizeOfSet(String setName);
    
    Set<Serializable> membersOfSet(String setName);
    
    public Map<Object, Object> entries(String key);
}
