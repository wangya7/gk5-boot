### Redis配置
application.yml配置如下：
```yaml
cache:
  redis:
    host: 192.168.3.20
    port: 6379
    password: 
    timeout: 50
    poolMaxTotal: 500
    poolMaxWaitMillis: -1
    poolMaxIdle: 150
    poolMinIdle: 100
    poolTestOnBorrow: true
```

### TODO
1. 重构Cache CacheManager接口命名
2. 基于redission实现分布式锁
3. 加入其它的Redis特性，HeperLogLog 布隆过滤器 GeoHash 限流