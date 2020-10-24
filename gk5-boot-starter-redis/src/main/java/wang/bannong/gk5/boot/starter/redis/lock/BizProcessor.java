package wang.bannong.gk5.boot.starter.redis.lock;

public interface BizProcessor {

    /**
     * 业务处理
     * <p>
     * 返回值必须大于等于0
     * 
     * @return 0-处理成功 其他正整数代表各种业务处理失败的具体含义
     */
    int process();
}
