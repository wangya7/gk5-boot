package wang.bannong.gk5.boot.redis.util;

public class CacheKeyUtil {

    /**
     * @param biz       业务点，如：checkcode
     * @param uniqueKey 唯一标识，如：userId
     */
    public static String key(String biz, String uniqueKey) {
        StringBuilder key = new StringBuilder();
        key.append(biz).append(":").append(uniqueKey);
        return key.toString();
    }

    public static String key(String prefix, String biz, String uniqueKey) {
        StringBuilder key = new StringBuilder();
        key.append(prefix).append(":").append(biz).append(":").append(uniqueKey);
        return key.toString();
    }
}
