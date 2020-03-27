/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.config
 * 文件名: DataSourceConfig
 * 日期: 2020/3/27 23:19
 * 说明:
 */
package com.autumn.mall.basis.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 配置seata数据源代理
 *
 * @author Anbang713
 * @create 2020/3/27
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSourceProxy dataSourceProxy(DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        return new DataSourceProxy(dataSource);
    }
}