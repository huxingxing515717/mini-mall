/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.api
 * 文件名: MallModuleKeyPrefixes
 * 日期: 2020/3/22 15:55
 * 说明:
 */
package com.autumn.mall.commons.api;

/**
 * 各个模块在缓存系统、库存等微服务中的key前缀常量定义
 *
 * @author Anbang713
 * @create 2020/3/22
 */
public interface MallModuleKeyPrefixes {

    /**
     * 商品微服务
     */
    String PRODUCT_KEY_PREFIX_OF_GOODS = "mall:product:goods:";
    String PRODUCT_KEY_PREFIX_OF_GOODS_INBOUND = "mall:product:goodsinbound:";

    /**
     * 销售微服务
     */
    String SALES_KEY_PREFIX_OF_PAYMENT_TYPE = "mall:sales:paymenttype:";
    String SALES_KEY_PREFIX_OF_SALES_INPUT = "mall:sales:input:";

    /**
     * 账务微服务
     */
    String ACCOUNT_KEY_PREFIX_OF_SUBJECT = "mall:account:subject:";

    /**
     * 招商微服务
     */
    String INVEST_KEY_PREFIX_OF_STORE = "mall:invest:store:";
    String INVEST_KEY_PREFIX_OF_BUILDING = "mall:invest:building:";
    String INVEST_KEY_PREFIX_OF_FLOOR = "mall:invest:floor:";
    String INVEST_KEY_PREFIX_OF_POSITION = "mall:invest:position:";
    String INVEST_KEY_PREFIX_OF_BRAND = "mall:invest:brand:";
    String INVEST_KEY_PREFIX_OF_BIZTYPE = "mall:invest:biztype:";
    String INVEST_KEY_PREFIX_OF_TENANT = "mall:invest:tenant:";
    String INVEST_KEY_PREFIX_OF_CONTRACT = "mall:invest:contract:";
}