package wang.bannong.gk5.boot.starter.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
@EnableTransactionManagement
public class RedisConfig {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConfigProperties.RedisCfg cfg = redisConfigProperties.getRedis();
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(cfg.getHost(), cfg.getPort());
        String passwd = cfg.getPassword();
        if (StringUtils.isNoneBlank(passwd)) {
            standaloneConfiguration.setPassword(passwd);
        }
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxTotal(cfg.getPoolMaxTotal());
        poolCofig.setMaxWaitMillis(cfg.getPoolMaxWaitMillis());
        poolCofig.setMaxIdle(cfg.getPoolMaxIdle());
        poolCofig.setMinIdle(cfg.getPoolMinIdle());
        poolCofig.setTestOnBorrow(cfg.isPoolTestOnBorrow());

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(poolCofig)
                .build();

        JedisConnectionFactory jedis = new JedisConnectionFactory(standaloneConfiguration, jedisClientConfiguration);
        // 初始化连接pool
        jedis.afterPropertiesSet();
        return jedis;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}