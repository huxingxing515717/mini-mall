/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.config
 * 文件名: AccountConfig
 * 日期: 2020/3/23 21:31
 * 说明:
 */
package com.autumn.mall.account.config;

import com.autumn.mall.account.HierarchicalContract;
import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}