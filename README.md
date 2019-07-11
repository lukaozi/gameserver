# gameserver

<p>
qq:874807132
</p>

## 前言

`gameserver` 是一个分布式java游戏框架，采用现阶段流行技术实现。

## 项目介绍

`gameserver`项目是一个java游戏框架，包括测试用client,netty客户端.</p>
基于Spring+netty+redis+自定义rpc(或者dubbo)+MyBatis + mysql(或者mongodb)实现。</p>
socket通信实现,登录系统,玩家线程模型,db系统,分布式id生成器,分布式锁,缓存系统,热更新机制,rpc系统,全服组队等等

### 组织结构

``` lua
gameserver
├── client -- 测试用netty客户端
├── rpc-api -- 提供分布式的rpc基础
├── server-common -- 支持包
├── server-db -- 数据落地系统 基于redis缓存+mysql 也提供了mongodb
├── server-gate -- 大部分游戏逻辑模块
└── server-team -- 提供跨服组队的demo
```

### 技术选型

技术 | 说明 | 官网
----|----|----
Spring | 容器+MVC框架 | [https://spring.io/projects/spring-framework](https://spring.io/projects/spring-framework)
Netty | 网络容器 | [https://netty.io/](https://netty.io/)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatisGenerator | 数据层代码生成 | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
Dubbo | 分布式rpc框架 | [http://dubbo.apache.org/zh-cn/](http://dubbo.apache.org/zh-cn/)
Redis | 分布式缓存 | [https://redis.io/](https://redis.io/)
MongoDb | NoSql数据库 | [https://www.mongodb.com/](https://www.mongodb.com/)
Druid | 数据库连接池 | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Protobuf | 序列化框架 | [https://developers.google.cn/protocol-buffers/](https://developers.google.cn/protocol-buffers/)
ProtoStuff | RunTime Protobuf | 

#### 架构图

##### 系统架构图

![系统架构图](doc/jiagou2.png)



## 环境搭建
等待完成

### 开发环境

工具 | 版本号 | 下载
----|----|----
JDK | 1.8 | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Mysql | 5.7 | https://www.mysql.com/
Redis | 4.x | https://redis.io/download
MongoDb | 3.2 | https://www.mongodb.com/download-center
nginx | 1.10 | http://nginx.org/en/download.html

## 项目相关文档

等待开发

## java游戏服务器学习系列
    
标题 | 链接
------------|----
网络基本功 | https://wizardforcel.gitbooks.io/network-basic/content/index.html
moba游戏的网络延迟 |  https://www.zhihu.com/question/36258781
tcp加速技术 | http://www.cnhalo.net/2016/03/13/tcp-accelerate-report/
从王者荣耀聊聊游戏的帧同步 | https://mp.weixin.qq.com/s/5atfuUNiIxYFv5y_wSVK1w
腾讯如何打造一款实时对战手游 | https://mp.weixin.qq.com/s?__biz=MzUxOTMyMzE2Mg==&mid=2247493343&amp;idx=1&amp;sn=d0e5ca2f3d608bbc26b75b70b303b0ec&source=41#wechat_redirect
Java魔法类：Unsafe应用解析 | https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html
netty源码系列 | https://www.jianshu.com/u/4fdc8c2315e8
jemalloc内存管理机制 | https://www.jianshu.com/p/f1988cc08dfd
linux内存分配小结 | https://blog.csdn.net/gfgdsg/article/details/42709943
ReentrantLock-lock方法 | https://blog.csdn.net/u012881904/article/details/51051138
IO模式和IO多路复用 | https://www.cnblogs.com/zingp/p/6863170.html
深入理解java内存模型|http://www.importnew.com/10589.html
聊聊java并发 | http://ifeve.com/talk-concurrency/
Java中synchronized的实现原理与应用 | https://blog.csdn.net/u012465296/article/details/53022317
java热更新原理实践 | https://blog.csdn.net/GV7lZB0y87u7C/article/details/79860776 
java aqs详解 | https://www.cnblogs.com/waterystone/p/4920797.html
彻底理解Java的Future模式 | https://www.cnblogs.com/cz123/p/7693064.html
java hashmap全解析 |https://javadoop.com/post/hashmap#toc0
jdk动态代理实现 | https://rejoy.iteye.com/blog/1627405
protostuff 文档 | https://protostuff.github.io/docs/protostuff-runtime/delegate/
Dubbo架构设计详解 | http://shiyanjun.cn/archives/325.html
用redis构建分布式锁 | http://ifeve.com/redis-lock/
云风的blog | https://blog.codingnow.com/

###持续更新
