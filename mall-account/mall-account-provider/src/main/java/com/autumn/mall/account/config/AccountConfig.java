/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.config
 * 文件名: AccountConfig
 * 日期: 2020/3/23 21:31
 * 说明:
 */
package com.autumn.mall.account.config;

import com.autumn.mall.account.HierarchicalContract;
import com.zaxxer.hikari.HikariDataSource;
import feign.Contract;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Anbang713
 * @create 2020/3/23
 */
@Configuration
public class AccountConfig {

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