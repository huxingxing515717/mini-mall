/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.config
 * 文件名: InvestConfig
 * 日期: 2020/3/22 21:57
 * 说明:
 */
package com.autumn.mall.invest.config;

import com.autumn.mall.invest.HierarchicalContract;
import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
@Configuration
public class InvestConfig {

    @Bean
    public Contract feignContract() {
        return new HierarchicalContract();
    }
}