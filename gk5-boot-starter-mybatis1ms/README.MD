### Mybatis
提供Mybatis连接数据库，主从分离【一主一从】，从库默认增加分页查询插件


### Getting Started


1. 配置application.yml

```
datasource:
  mappersPath: wang.bannong.gk5.test.dao.mapper
  master:
    host: 139.196.225.76
    port: 3306
    db: marktonight
    username: root
    password: wetoec11
    maxIdle: 10
    minIdle: 10
    connectionTimeout: 30000
  slave:
    host: 139.196.225.76
    port: 3306
    db: marktonight
    username: root
    password: wetoec11
    maxIdle: 10
    minIdle: 10
    connectionTimeout: 30000
```
2. 配置第一步的mappersPath
3. 现在实现一个User表操作
    * Mybatis的UserMapper.java可以自己实现，当然也可以使用代码生成器
    * 实现UserDAO.java
    ```java
    @org.springframework.stereotype.Repository
    public class UserDAO {
       // 实现主库操作  增删改
       @org.springframework.beans.factory.annotation.Autowired
       private UserMapper masterUserMapper;
    
       // 实现从库操作  查询逻辑
       @org.springframework.beans.factory.annotation.Autowired
       private UserMapper slaveUserMapper;
    }
    ```