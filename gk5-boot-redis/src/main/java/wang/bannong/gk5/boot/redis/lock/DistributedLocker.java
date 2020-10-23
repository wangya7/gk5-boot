package wang.bannong.gk5.boot.redis.lock;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Set;

import wang.bannong.gk5.cache.CacheManager;
import wang.bannong.gk5.cache.CacheResult;

/**
 * 用redis实现的分布式锁工具类
 */
public abstract class DistributedLocker {
    private final static Logger logger                                = LoggerFactory.getLogger(DistributedLocker.class);

    public final static String  LOCK_KEY_PREFIX                       = "lock:";
    public final static String  LOCK_KEY_PATTERN                      = LOCK_KEY_PREFIX + "*";

    public final static int     DEADLOCK_EXISTSTIME_THRESHOLD_DEFAULT = 10 * 60;                                         // 10分钟

    public final static int     LOCK_FAILED                           = -1;

    /**
     * 获取分布式锁
     * 
     * @param cacheManager 缓存控制器
     * @param key 缓存key值
     * @return 处理结果 true-获得锁 false-未获得锁
     */
    public static boolean lock(CacheManager cacheManager, String key) {
        return cacheManager.setnx(LOCK_KEY_PREFIX + key, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 获取分布式锁并且进行相关业务处理
     * <p>
     * 获取到分布式锁的情况，会进行如下处理：<br>
     * 1、通过BizHandler执行相关业务处理<br>
     * 2、设置该分布式锁的过期时间
     * 
     * @param cacheManager 缓存控制器
     * @param key 缓存key值
     * @param expire 锁过期时间（单位：秒，小于等于0的情况，处理完成该锁就会被释放）
     * @param handler 业务处理对象
     * @return 处理结果 true-成功 false-失败
     * 
     */
    public static boolean lockAndHandle(CacheManager cacheManager, String key, long expire, BizHandler handler) {
        String cacheKey = LOCK_KEY_PREFIX + key;

        boolean lock;
        try {
            lock = cacheManager.setnx(cacheKey, String.valueOf(System.currentTimeMillis()));
        } catch (Throwable t) {
            // 发生异常时，当作获取到锁
            lock = true;
            logger.error(String.format(">>>Obtain lock[ key = %s ] exception!", key), t);
        }
        if (!lock) {
            logger.warn(">>>Failed to obtain lock[ key = {} ].", key);
            return false;
        }

        try {
            return handler.handle();
        } finally {
            if (expire > 0) {
                cacheManager.put(cacheKey, "1", expire);
            } else {
                cacheManager.del(cacheKey);
            }
        }
    }

    /**
     * 获取分布式锁并且进行相关业务处理
     * <p>
     * 获取到分布式锁的情况，会进行如下处理：<br>
     * 1、通过BizProcessor执行相关业务处理<br>
     * 2、设置该分布式锁的过期时间
     * 
     * @param cacheManager 缓存控制器
     * @param key 缓存key值
     * @param expire 锁过期时间（单位：秒，小于等于0的情况，处理完成该锁就会被释放）
     * @param processor 业务处理对象
     * @return 处理结果 -1代表获取锁失败 0代表处理成功 其他正整数代表各种业务处理失败的具体含义
     */
    public static int lockAndProcess(CacheManager cacheManager, String key, long expire, BizProcessor processor) {
        String cacheKey = LOCK_KEY_PREFIX + key;

        boolean lock;
        try {
            lock = cacheManager.setnx(cacheKey, String.valueOf(System.currentTimeMillis()));
        } catch (Throwable t) {
            // 发生异常时，当作获取到锁
            lock = true;
            logger.error(String.format(">>>Obtain lock[ key = %s ] exception!", key), t);
        }
        if (!lock) {
            logger.warn(">>>Failed to obtain lock[ key = {} ].", key);
            return LOCK_FAILED;
        }

        try {
            return processor.process();
        } finally {
            if (expire > 0) {
                cacheManager.put(cacheKey, "1", expire);
            } else {
                cacheManager.del(cacheKey);
            }
        }
    }

    /**
     * 检查并处理死锁
     * <p>
     * 获取分布式锁的key进行检查，如果判断为死锁的话，进行删除处理<br>
     * 只有在分布式锁的key建立到过期时间设置的中途服务器宕机 或者 超时设置时发生异常时才会出现死锁
     * 
     * @param cacheManagers
     */
    public static void checkAndHandleDeadlock(long existstimeThreshold, CacheManager... cacheManagers) {
        // 参数检查
        if (cacheManagers == null) {
            return;
        }

        // 循环处理缓存对象
        for (CacheManager cacheManager : cacheManagers) {
            // 当前缓存对象为空的情况，直接处理下一个缓存对象
            if (cacheManager == null) {
                continue;
            }

            // 获取锁相关的缓存key
            Set<String> keys = cacheManager.keys(LOCK_KEY_PATTERN);
            logger.info(">>>缓存[ {} ]下获取到的keys: {}<<<", cacheManager.getClass().getName(), keys);
            if (CollectionUtils.isEmpty(keys)) {
                // 当前缓存对象下获取不到锁相关的缓存key，直接处理下一个缓存对象
                continue;
            }

            // 循环处理锁相关的缓存key
            for (String key : keys) {
                // 获取当前锁相关缓存key的缓存内容
                CacheResult<String> cacheResult = cacheManager.getString(key);
                if (!cacheResult.isSucc() || cacheResult.isEmpty()) {
                    // 获取失败的情况，直接处理下一个锁相关的缓存key
                    continue;
                }

                // 获取到锁相关的缓存key的缓存内容
                long value = NumberUtils.toLong(cacheResult.getModule());
                logger.info(">>>处理中的key[ {} ]的缓存内容: [ {} ]", key, value);
                // 缓存内容为系统时间 且 当前系统时间比缓存的系统时间大于等于存储阈值 且 该锁相关的缓存key在缓存中无过期时间的情况，进行删除处理
                if (value > 1 && (System.currentTimeMillis() - value) >= existstimeThreshold && cacheManager.getExpire(key) == -1) {
                    cacheManager.del(key);
                    logger.info(">>>key[ {} ]被成功删除.<<<", key);
                }
            }
        }
    }
}
