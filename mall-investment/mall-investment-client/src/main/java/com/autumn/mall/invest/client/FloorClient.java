/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: FloorClient
 * 日期: 2020/3/15 14:10
 * 说明:
 */
package com.autumn.mall.invest.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 楼层客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-investment-provider", contextId = "mall-investment-FloorClient", path = "/floor")
public interface FloorClient extends FloorApi {

}