/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis
 * 文件名: BasisProviderApplication
 * 日期: 2020/3/14 21:19
 * 说明:
 */
package com.autumn.mall.basis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 基础微服务启动类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(basePackages = {"com.autumn.mall.commons", "com.autumn.mall.basis"})
public class BasisProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasisProviderApplication.class, args);
    }
}