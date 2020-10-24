package wang.bannong.gk5.boot.starter.redis.util;

import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

public final class CacheKeyUtil {

    private final static String DEF_MODULE = "def";
    private final static String SEPARATOR  = ":";

    /**
     * @param biz       业务点，如：checkcode
     * @param uniqueKey 唯一标识，如：userId
     */
    public static String key(String biz, String uniqueKey) {
        return key(DEF_MODULE, null, biz, uniqueKey);
    }

    /**
     * 生成key
     *
     * @param module    系统模块
     * @param prefix    前缀
     * @param biz       业务场景
     * @param uniqueKey 业务key
     * @return key
     */
    public static String key(String module, String prefix, String biz, String uniqueKey) {
        StringJoiner key = new StringJoiner(SEPARATOR);
        key.add(StringUtils.isNotBlank(module) ? module : DEF_MODULE);
        if (StringUtils.isNoneBlank(prefix))
            key.add(prefix);
        key.add(biz);
        key.add(uniqueKey);
        return key.toString();
    }
}
