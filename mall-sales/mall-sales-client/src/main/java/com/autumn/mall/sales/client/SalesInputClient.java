/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.client
 * 文件名: SalesInputClient
 * 日期: 2020/3/22 15:17
 * 说明:
 */
package com.autumn.mall.sales.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 销售数据录入单客户端接口
 *
 * @author Anbang713
 * @create 2020/3/22
 */
@FeignClient(name = "mall-sales-provider", path = "/salesinput")
public interface SalesInputClient extends SalesInputApi {

}