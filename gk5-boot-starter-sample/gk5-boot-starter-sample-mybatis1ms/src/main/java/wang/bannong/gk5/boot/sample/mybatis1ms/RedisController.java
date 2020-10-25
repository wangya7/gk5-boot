package wang.bannong.gk5.boot.sample.mybatis1ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wang.bannong.gk5.boot.starter.redis.CacheOpr;
import wang.bannong.gk5.boot.starter.redis.CacheResult;

@RestController
public class RedisController {

    private final static Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private CacheOpr cacheOpr;

    @RequestMapping(value = "/redis/scene", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String scene1(String key, String value) {
        log.info("Test redis, key:[{}], value[{}]", key, value);
        CacheResult<String> result = cacheOpr.getObject(key);
        log.info("result={}", result);
        if (result.isSucc() && !result.isEmpty()) {
            return result.getModule();
        } else {
            cacheOpr.put(key, value);
            return value;
        }
    }

}
