### JDK17 运行 Dubbo 问题
[使用jdk17编译运行dubbo 2.7.14项目](https://cn.dubbo.apache.org/zh-cn/blog/2018/08/07/%E4%BD%BF%E7%94%A8jdk17%E7%BC%96%E8%AF%91%E8%BF%90%E8%A1%8Cdubbo-2.7.14%E9%A1%B9%E7%9B%AE/)
对于普通的dubbo项目，只需要在运行时添加
--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED --add-opens java.base/java.math=ALL-UNNAMED