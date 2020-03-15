/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.config
 * 文件名: SwaggerConfig
 * 日期: 2020/3/15 20:33
 * 说明:
 */
package com.autumn.mall.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类<p>
 * 服务启动后通过{@code http://ip:port/swagger-ui.html#/}访问即可
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)//
                .apiInfo(getApiInfo())//
                .select()//
                .apis(RequestHandlerSelectors.basePackage("com.autumn.mall.product"))// 这里指定需要生成swagger接口的包路径
                .paths(PathSelectors.any())//
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("商品微服务").version("1.0.0").build();
    }
}