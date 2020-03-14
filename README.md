# mini-mall

## 项目介绍

`mini-mall`项目是一个将当前购物中心核心概念和业务抽离出来的后台管理系统。采用当前最流行的微服务架构实现，基于Spring Cloud（H版）技术栈、Redis、MongoDB、RabbitMQ、ELK等技术实现相关业务功能，在项目功能和技术本身真正做到麻雀虽小五脏俱全。其主要业务功能包括招商微服务、销售微服务、财务微服务等内容，招商微服务包括项目管理、楼宇管理、楼层管理、合同管理等；销售微服务包括商品管理、商品库存管理、销售数据录入管理等；财务微服务包括出账管理、账单管理、在线缴费功能等。除此之外，我们还会在架构上实现网关服务、服务注册与发现、配置中心、微服务调用链追踪等功能。

## 系统架构图

## 组织架构

```
mini-mall
```

## 后端技术选型

|     技术     | 说明                 | 官网地址                                  |
| :----------: | -------------------- | ----------------------------------------- |
| Spring Boot  | 简化Spring开发的框架 | https://spring.io/projects/spring-boot    |
| Spring Cloud | 微服务框架           | https://spring.io/projects/spring-cloud   |
|  Swagger-UI  | 接口文档生成工具     | https://github.com/swagger-api/swagger-ui |
|    Lombok    | 简化对象封装工具     | https://github.com/rzwitserloot/lombok    |

## 环境搭建

### 开发环境

| 工具  | 版本号 |                           下载地址                           |
| :---: | :----- | :----------------------------------------------------------: |
|  JDK  | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| MySQL | 5.6    |                    https://www.mysql.com                     |
| Nginx | 1.12.2 |              http://nginx.org/en/download.html               |

### 搭建步骤

> windows环境单机版部署

> linux环境单机版部署

> linux环境集群版部署

> docker环境部署