/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: StoreClient
 * 日期: 2020/3/14 17:19
 * 说明:
 */
package com.autumn.mall.invest.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 项目客户端接口
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@FeignClient(name = "mall-investment-provider", contextId = "mall-investment-StoreClient", path = "/store")
public interface StoreClient extends StoreApi {

}