### Mybatis-Plus使用篇
> 2020/2/1 NCP隔离在家期间，武汉加油，中国加油


* [官网](https://mybatis.plus/)
* [Mybatis-Plus Gitee](https://gitee.com/baomidou/mybatis-plus)

个人观点：
1. Mybatis-Plus忽略了业务层，服务层直接操作数据库。
2. 交叉的业务不能很好支持，需要多张表关联查询

本项目希望实现:
1. 基于Mybatis，构造持久层DAO
2. 自动构造业务层，参考DDD
3. 支持多数据源
4. 支持SQL日志显示，执行时间

#### 快速开始
application.yml增加配置
```yaml
datasource:
  # 真实项目的mapper接口路径
  mappersPath: wang.bannong.gk5.ntm.rpc.dao
  # 真实项目的mapper.xml路径，多个使用","分割
  mapperLocations: classpath*:/wang/bannong/gk5/ntm/rpc/dao/*.xml
  primary: master
  dbs:
  - key: master
    host: 192.168.3.20
    port: 3306
    db: ntmx
    username: dbmaster
    password: qwer1234!@#$
    minIdle: 10
    maxIdle: 10
    connectionTimeout: 3000
  - key: slave
    host: 192.168.3.20
    port: 3306
    db: ntmx
    username: dbmaster
    password: qwer1234!@#$
    minIdle: 10
    maxIdle: 10
    connectionTimeout: 3000
```
注意：
1. dbs中定义的数组key必须以master,slave开头
2. datasource.primary必须是dbs中以master开头的key

### TODO 设计需要提升
1. 动态的生成“多主多从”，负载均衡
2. 尽量抛弃 Mybatis-Plus 包的依赖