## gk5-boot-starter-mybatis

Mybatis 开箱即用，连接池使用的是 [druid](https://github.com/alibaba/druid), 暂时支持 主从双数据源，主从只是叫法上的区别，功能完全一样，比如主数据源支持分页，从数据源支持事务。

## 如何使用

约定一下保持Mapper的接口路径和XML文件路径保持一致。

1. application.yml 配置
```yaml
spring:
  profiles:
    active: druid

# MyBatis配置
mybatis:
  # Packages to search type aliases. (Package delimiters are ",; \t\n")
  typeAliasesPackage: wang.bannong.gk5.boot.starter.mybatis.sample.entity
  # Locations of MyBatis mapper files.
  mapperLocations: classpath*:wang.bannong.gk5.boot.starter.mybatis.sample.mapper/*Mapper.xml
  # Location of MyBatis xml config file.
  configLocation: classpath:mybatis/mybatis-config.xml

# PageHelper分页插件
pagehelper:
  helperDialect: mysql
  supportMethodsArguments: true
  params: count=countSql
```
2. application-druid.yml 配置
演示配置双数据源
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    druid:
      # 主库数据源
      master:
        url: jdbc:mysql://127.0.0.1:3306/gk5?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true
        username: root
        password: '@iam3307'
      # 从库数据源
      slave:
        # 从数据源开关/默认关闭
        enabled: true
        url: jdbc:mysql://127.0.0.1:3306/gk5?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true
        username: root
        password: '@iam3307'
      # 初始连接数
      initialSize: 5
      # 最小连接池数量
      minIdle: 10
      # 最大连接池数量
      maxActive: 20
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      # 配置一个连接在池中最大生存的时间，单位是毫秒
      maxEvictableIdleTimeMillis: 900000
      # 配置检测连接是否有效
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      webStatFilter:
        enabled: true
      statViewServlet:
        enabled: true
        # 设置白名单，不填则允许所有访问
        allow:
        url-pattern: /druid/*
        # 控制台管理用户名和密码
        login-username: iloveu
        login-password: iloveu2
      filter:
        stat:
          enabled: true
          # 慢SQL记录
          log-slow-sql: true
          slow-sql-millis: 1000
          merge-sql: true
        wall:
          config:
            multi-statement-allow: true
```
3. 配置 resources/mybatis/mybatis-config.xml
如果已经配置请忽略。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <!-- 全局参数 -->
  <settings>
    <!-- 使全局的映射器启用或禁用缓存 -->
    <setting name="cacheEnabled" value="true"/>
    <!-- 允许JDBC 支持自动生成主键 -->
    <setting name="useGeneratedKeys" value="true"/>
    <!-- 配置默认的执行器.SIMPLE就是普通执行器;REUSE执行器会重用预处理语句(prepared statements);BATCH执行器将重用语句并执行批量更新 -->
    <setting name="defaultExecutorType" value="SIMPLE"/>
    <!-- 指定 MyBatis 所用日志的具体实现 -->
    <setting name="logImpl" value="SLF4J"/>
    <!-- 使用驼峰命名法转换字段 -->
    <!-- <setting name="mapUnderscoreToCamelCase" value="true"/> -->
  </settings>
</configuration>
```
4. 启动类增加你注解，表明Mapper文件路径
```java
@MapperScan("wang.bannong.gk5.boot.starter.mybatis.sample.mapper")
```
## 版本日志
### 1.0.0-SNAPSHOT
2023/6/5 00:12 First Version.
