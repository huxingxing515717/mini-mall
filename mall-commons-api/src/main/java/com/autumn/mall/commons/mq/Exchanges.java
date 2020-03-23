/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.mq
 * 文件名: Exchanges
 * 日期: 2020/3/14 20:34
 * 说明:
 */
package com.autumn.mall.commons.mq;

/**
 * 交换器名称常量定义
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface Exchanges {

    /**
     * 从mall-commons-core工程发出的mq交换器名称
     */
    String MALL_COMMONS_EXCHANGE = "mini-mall.commons.core";

    /**
     * 商品微服务
     */
    String MALL_PRODUCT_PROVIDER_EXCHANGE = "mini-mall.product.provider";

    /**
     * 销售微服务
     */
    String MALL_SALES_PROVIDER_EXCHANGE = "mini-mall.sales.provider";
}