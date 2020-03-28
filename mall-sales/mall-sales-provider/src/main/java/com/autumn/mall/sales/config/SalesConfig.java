/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.config
 * 文件名: SalesConfig
 * 日期: 2020/3/22 22:12
 * 说明:
 */
package com.autumn.mall.sales.config;

import com.autumn.mall.sales.HierarchicalContract;
import com.zaxxer.hikari.HikariDataSource;
import feign.Contract;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 解决feign多重继承问题
 *
 * @author Anbang713
 * @create 2020/3/22
 */
@Configuration
public class SalesConfig {

    @Bean
    public Contract feignContract() {
        return new HierarchicalContract();
    }

    @Bean
    @Primary
    public DataSourceProxy dataSourceProxy(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        return new DataSourceProxy(dataSource);
    }
}