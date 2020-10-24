package wang.bannong.gk5.boot.starter.mybatis;

public interface MybatisXConstant {
    String url      = "jdbc:p6spy:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    String driver   = "com.p6spy.engine.spy.P6SpyDriver";
    String poolName = "Hikari-MyBatisX-%s";

    String sqlSessionFactoryBeanName       = "%sSqlSessionFactory";
    String sqlSessionTemplateBeanName      = "%sSqlSessionTemplate";
    String mapperScannerConfigurerBeanName = "%sMapperScannerConfigurer";
}
