package wang.bannong.gk5.boot.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wang.bannong.gk5.boot.redis.CacheManager;

public class ConcurrentControlProcessor {

    private final static Logger logger = LoggerFactory.getLogger(ConcurrentControlProcessor.class);

    public static int doProcess(CacheManager cacheManager, String key, int retryTimes,
                                long sleepMillis, long expire, BizProcessor processor) {
        int processResult = 0;
        int retryTimesWork = retryTimes <= 0 ? 1 : retryTimes;
        long sleepMillisWork = sleepMillis <= 0 ? 1 : sleepMillis;
        for (int i = 0; i < retryTimesWork; i++) {
            // 未获取到锁的情况，睡眠一会儿，默认睡眠0.05s
            if (processResult == DistributedLocker.LOCK_FAILED) {
                try {
                    Thread.sleep(sleepMillisWork);
                } catch (InterruptedException e) {
                    logger.warn("睡眠被打扰了！", e);
                }
            }

            // 获取锁并进行相应的业务处理
            processResult = DistributedLocker.lockAndProcess(cacheManager, key, expire, processor);

            // 不是锁获取失败的情况，重试处理结束
            if (processResult != DistributedLocker.LOCK_FAILED) {
                break;
            }
        }

        return processResult;
    }

    public static int doProcess(CacheManager cacheManager, String key, int retryTimes,
                                long sleepMillis, BizProcessor processor) {
        return doProcess(cacheManager, key, retryTimes, sleepMillis, 0, processor);
    }
}
