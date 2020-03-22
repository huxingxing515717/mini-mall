/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: TenantClient
 * 日期: 2020/3/15 16:03
 * 说明:
 */
package com.autumn.mall.invest.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商户客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-investment-provider", contextId = "mall-investment-TenantClient", path = "/tenant")
public interface TenantClient extends TenantApi {

}