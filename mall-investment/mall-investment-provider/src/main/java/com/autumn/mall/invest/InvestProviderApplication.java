/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest
 * 文件名: InvestProviderApplication
 * 日期: 2020/3/14 17:31
 * 说明:
 */
package com.autumn.mall.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 招商服务提供者启动类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = {"com.autumn.mall.basis.client"})
@ComponentScan(basePackages = {"com.autumn.mall.commons", "com.autumn.mall.invest"})
public class InvestProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestProviderApplication.class, args);
    }
}