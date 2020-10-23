package wang.bannong.gk5.boot.mybatis1ms.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6spy SQL 打印策略
 */
public class LogMessageFormat implements MessageFormattingStrategy {

    public LogMessageFormat(){}

    /**
     * 日志格式化方式（打印SQL日志会进入此方法，耗时操作，生产环境不建议使用）
     *
     * @param connectionId: 连接ID
     * @param now:          当前时间
     * @param elapsed:      花费时间
     * @param category:     类别
     * @param prepared:     预编译SQL
     * @param sql:          最终执行的SQL
     * @param url:          数据库连接地址
     * @return 格式化日志结果
     **/
    @Override
    public String formatMessage(int connectionId, String now, long elapsed,
                                String category, String prepared, String sql,
                                String url) {

        return (sql != null && sql.length() >0) ? " Consume Time：" + elapsed + " ms " + now + "\n Execute SQL：" + sql.replaceAll("[\\s]+", " ") + "\n" : "";
    }
}
