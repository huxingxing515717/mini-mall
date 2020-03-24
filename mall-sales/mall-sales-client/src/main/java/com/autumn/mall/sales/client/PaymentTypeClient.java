/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.client
 * 文件名: PaymentTypeClient
 * 日期: 2020/3/15 16:41
 * 说明:
 */
package com.autumn.mall.sales.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 付款方式客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-sales-provider", contextId = "mall-sales-PaymentTypeClient", path = "/paymenttype")
public interface PaymentTypeClient extends PaymentTypeApi {

}