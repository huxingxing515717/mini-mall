/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.client
 * 文件名: StockClient
 * 日期: 2020/3/15 21:23
 * 说明:
 */
package com.autumn.mall.basis.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 库存客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-basis-provider", path = "/stock", contextId = "mall-basis-StockClient")
public interface StockClient extends StockApi {

}