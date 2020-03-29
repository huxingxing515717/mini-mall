# mini-mall (更新中……)

## 项目介绍

`mini-mall`项目是一个将当前购物中心核心概念和业务抽离出来的后台管理系统。采用当前最流行的微服务架构实现，基于Spring Cloud（H版）技术栈、Redis、MongoDB、RabbitMQ、ELK等技术实现相关业务功能，在项目功能和技术本身真正做到麻雀虽小五脏俱全。其主要业务功能包括招商微服务、销售微服务、财务微服务等内容，招商微服务包括项目管理、楼宇管理、楼层管理、合同管理等；销售微服务包括商品管理、商品库存管理、销售数据录入管理等；财务微服务包括出账管理、账单管理、在线缴费功能等。除此之外，我们还会在架构上实现网关服务、服务注册与发现、配置中心、微服务调用链追踪等功能。

## 系统架构图

## 组织架构

```
mini-mall
├── mall-registry-server:9010 -- 基于Eureka的微服务注册中心（后续考虑将其改造成Consul、Nacos）
├── mall-gateway-server:9015 -- 基于Zuul的网关服务（后续考虑将其改造成gateway）
├── mall-commons-api -- 通用api组件
├── mall-commons-core -- 通用核心组件，提供业务相关的处理
├── mall-basis -- 基础微服务模块
	├── mall-basis-api -- 基础微服务api组件
	├── mall-basis-client -- 基础微服务客户端接口组件
	├── mall-basis-provider:9020 -- 基础微服务服务提供者组件
├── mall-investment -- 招商微服务模块
	├── mall-investment-api -- 招商微服务api组件
	├── mall-investment-client -- 招商微服务客户端接口组件
	├── mall-investment-provider:9025 -- 招商微服务服务提供者组件
├── mall-sales -- 销售微服务模块
	├── mall-sales-api -- 销售微服务api组件
	├── mall-sales-client -- 销售微服务客户端接口组件
	├── mall-sales-provider:9030 -- 销售微服务服务提供者组件
├── mall-account -- 账务微服务模块
	├── mall-account-api -- 账务微服务api组件
	├── mall-account-client -- 账务微服务客户端接口组件
	├── mall-account-provider:9035 -- 账务微服务服务提供者组件
├── mall-product -- 商品微服务模块
	├── mall-product-api -- 商品微服务api组件
	├── mall-product-client -- 商品微服务客户端接口组件
	├── mall-product-provider:9040 -- 商品微服务服务提供者组件
├── mall-config-server:9045 -- 基于config的分布式配置中心
```

## 后端技术选型

|     技术     | 说明                       | 官网地址                                  |
| :----------: | -------------------------- | ----------------------------------------- |
| Spring Boot  | 简化Spring开发的框架       | https://spring.io/projects/spring-boot    |
| Spring Cloud | 微服务框架                 | https://spring.io/projects/spring-cloud   |
|  Swagger-UI  | 接口文档生成工具           | https://github.com/swagger-api/swagger-ui |
|    Lombok    | 简化对象封装工具           | https://github.com/rzwitserloot/lombok    |
|    Redis     | 基于键值对的存储和缓存系统 | https://redis.io/                         |
|   RabbitMQ   | 消息中间件                 | https://www.rabbitmq.com/                 |
|   MongoDB    | NoSQL数据库                | https://www.mongodb.com/                  |
|    seata     | 分布式事务框架             | https://github.com/seata/seata          |

## 环境搭建

### 开发环境

|   工具   | 版本号 | 下载地址                                                     |
| :------: | :----- | :----------------------------------------------------------- |
|   JDK    | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
|  MySQL   | 5.6    | https://www.mysql.com                                        |
|  Nginx   | 1.12.2 | http://nginx.org/en/download.html                            |
| RabbitMQ | 3.7.7  | http://www.rabbitmq.com/download.html                        |
| MongoDB  | 4.0.2  | https://www.mongodb.com/download-center                      |
|  Redis   | 3.2.1  | https://redis.io/download                                    |
|  seata   | 1.0.0  | https://github.com/seata/seata/releases/download/v1.0.0/seata-server-1.0.0.zip |
