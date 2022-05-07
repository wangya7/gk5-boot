package wang.bannong.gk5.boot.starter.web.security;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;
import wang.bannong.gk5.boot.starter.web.util.Constant;

/**
 * TOKEN SERVICE
 */
@Component
public class TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置 token
     *
     * @param ia      ia，可以为空，空则创建
     * @param subject 主体信息
     * @return ia
     */
    public String setSubject(String ia, Subject subject) {
        Objects.requireNonNull(subject, "subject cannot be null");
        if (StringUtils.isBlank(ia)) {
            ia = createIa();
        }
        String token = getToken(ia);
        switch (subject.getClient()) {
            case ADMIN:
                redisTemplate.opsForValue().set(token, subject, Constant.ADMIN_TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
                break;
            case NATIVE:
                redisTemplate.opsForValue().set(token, subject, Constant.NATIVE_TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
                break;
            default:
                redisTemplate.opsForValue().set(token, subject, Constant.TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
        }
        return ia;
    }

    /**
     * 获取登录对象并更新延迟生效时长
     */
    public Subject getSubject(String ia) {
        if (StringUtils.isNotBlank(ia)) {
                String token = getToken(ia);
            Subject subject = (Subject) redisTemplate.opsForValue().get(token);
            if (subject != null) {
                switch (subject.getClient()) {
                    case ADMIN:
                        redisTemplate.expire(token, Constant.ADMIN_TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
                        break;
                    case NATIVE:
                        redisTemplate.expire(token, Constant.NATIVE_TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
                        break;
                    default:
                        redisTemplate.expire(token, Constant.TOKEN_EFFECTIVE_TIME, TimeUnit.SECONDS);
                }
                return subject;
            }
        }
        return null;
    }

    public static String createIa() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取 token
     */
    public static String getToken(String ia) {
        Objects.requireNonNull(ia, "ia cannot be null");
        return String.format(Constant.CK_TOKEN_PATTERN, ia);
    }
}
