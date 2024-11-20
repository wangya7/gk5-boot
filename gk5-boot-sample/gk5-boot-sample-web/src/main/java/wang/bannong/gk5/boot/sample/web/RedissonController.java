package wang.bannong.gk5.boot.sample.web;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;


import org.redisson.api.RFencedLock;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:bannongvipp@163.com">bn</a>
 * @date 2024/6/14
 */
@RestController
@RequestMapping("/redis")
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate  redisTemplate;

    static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(path = "/lock", method = RequestMethod.GET)
    public String lock() throws InterruptedException {
        //获取锁
        RLock lock = redissonClient.getLock("dis-lock");

        //加锁，参数：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
        //注意：如果指定锁自动释放时间，不管业务有没有执行完，锁都不会自动延期，即没有 watch dog 机制。
        boolean isLock = lock.tryLock(1, 2, TimeUnit.SECONDS);
        try {
            if (isLock) {
                System.out.println(FORMAT.format(System.currentTimeMillis()) + "获取分布式锁成功");
                Thread.sleep(1000);
                System.out.println(FORMAT.format(System.currentTimeMillis()) + "业务完成");
            } else {
                System.out.println(FORMAT.format(System.currentTimeMillis()) + "获取分布式锁失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("业务异常");
        } finally {
            //当前线程未解锁
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                //释放锁
                System.out.println("解锁");
                lock.unlock();
            }
        }
        return "OK";
    }

    static long FENCED_TOKEN = 0;


    @RequestMapping(path = "/fencing", method = RequestMethod.GET)
    public String fencingLock() throws InterruptedException {
        //获取锁
        RFencedLock lock = redissonClient.getFencedLock("dis-fenced-lock2");

        //加锁，参数：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
        //注意：如果指定锁自动释放时间，不管业务有没有执行完，锁都不会自动延期，即没有 watch dog 机制。
        Long fencedToken = lock.tryLockAndGetToken();
        try {
            if (fencedToken != null) {
                System.out.println("最新的fenced token ：" + fencedToken);
                if (fencedToken > FENCED_TOKEN) {
                    System.out.println(FORMAT.format(System.currentTimeMillis()) + "获取分布式锁成功");
                    Thread.sleep(40000);
                    System.out.println(FORMAT.format(System.currentTimeMillis()) + "业务完成");
                } else {
                    System.out.println(FORMAT.format(System.currentTimeMillis()) + "无需处理直接抛弃");
                }
                FENCED_TOKEN = fencedToken;
            }
        } catch (Exception e) {
            throw new RuntimeException("业务异常");
        } finally {
            //当前线程未解锁
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                //释放锁
                System.out.println("解锁");
                lock.unlock();
            }
        }
        return "OK";
    }
}
