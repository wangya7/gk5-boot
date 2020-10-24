package wang.bannong.gk5.boot.starter.redis;

public interface Cache {
    /**
     * 设置简单的字符串类型的key-value键值对，无过期时间
     */
    void set(String key, String value);

    /**
     * 设置简单的带过期时间的字符串类型的key-value键值对
     */
    void setex(String key, String value, int seconds);

    /**
     * 获取指定字符串类型key的值
     */
    String get(String key);

    /**
     * 删除指定字符串类型key的缓存
     */
    Long del(String key);

    /**
     * 检查指定字符串类型key的缓存是否存在
     *
     * @return true-存在、false-不存在
     */
    Boolean exists(String key);

    /**
     * 设置指定字符串类型key的缓存过期时间
     *
     * @return true-设置成功、false-设置失败
     */
    Boolean expire(String key, int seconds);

    /**
     * 指定字符串类型key的缓存值自增（原子操作）
     *
     * @return 自增后的值
     */
    Long incr(String key);

    /**
     * 指定字符串类型key的缓存值自减（原子操作）
     *
     * @return 自减后的值
     */
    Long decr(String key);
}
