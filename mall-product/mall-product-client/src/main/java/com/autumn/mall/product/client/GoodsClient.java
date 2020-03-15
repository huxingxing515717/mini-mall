/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.client
 * 文件名: GoodsClient
 * 日期: 2020/3/15 20:28
 * 说明:
 */
package com.autumn.mall.product.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-product-provider", path = "/goods")
public interface GoodsClient extends GoodsApi {

}