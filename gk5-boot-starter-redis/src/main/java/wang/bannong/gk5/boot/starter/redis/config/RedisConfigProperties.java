package wang.bannong.gk5.boot.starter.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by bn. on 2018/8/4 下午5:17
 */
@Component
@ConfigurationProperties(prefix = "cache")
@PropertySource("classpath:application.yml")
public class RedisConfigProperties implements Serializable {
    private static final long serialVersionUID = -1515561047164933985L;

    private RedisCfg redis;

    public RedisCfg getRedis() {
        return redis;
    }

    public void setRedis(RedisCfg redis) {
        this.redis = redis;
    }

    @Override
    public String toString() {
        return "RedisConfigProperties{" +
                "redis=" + redis +
                '}';
    }

    public static class RedisCfg implements Serializable {
        private static final long serialVersionUID = -8461021635945655273L;
        private String host;
        private int    port;
        private String password;
        private int    timeout;
        private int    poolMaxTotal;
        private int    poolMaxWaitMillis;
        private int    poolMaxIdle;
        private int    poolMinIdle;

        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        private boolean poolTestOnBorrow;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getPoolMaxTotal() {
            return poolMaxTotal;
        }

        public void setPoolMaxTotal(int poolMaxTotal) {
            this.poolMaxTotal = poolMaxTotal;
        }

        public int getPoolMaxWaitMillis() {
            return poolMaxWaitMillis;
        }

        public void setPoolMaxWaitMillis(int poolMaxWaitMillis) {
            this.poolMaxWaitMillis = poolMaxWaitMillis;
        }

        public int getPoolMaxIdle() {
            return poolMaxIdle;
        }

        public void setPoolMaxIdle(int poolMaxIdle) {
            this.poolMaxIdle = poolMaxIdle;
        }

        public int getPoolMinIdle() {
            return poolMinIdle;
        }

        public void setPoolMinIdle(int poolMinIdle) {
            this.poolMinIdle = poolMinIdle;
        }

        public boolean isPoolTestOnBorrow() {
            return poolTestOnBorrow;
        }

        public void setPoolTestOnBorrow(boolean poolTestOnBorrow) {
            this.poolTestOnBorrow = poolTestOnBorrow;
        }

        @Override
        public String toString() {
            return "RedisCfg{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    ", password='" + password + '\'' +
                    ", timeout=" + timeout +
                    ", poolMaxTotal=" + poolMaxTotal +
                    ", poolMaxWaitMillis=" + poolMaxWaitMillis +
                    ", poolMaxIdle=" + poolMaxIdle +
                    ", poolMinIdle=" + poolMinIdle +
                    ", poolTestOnBorrow=" + poolTestOnBorrow +
                    '}';
        }
    }
}
