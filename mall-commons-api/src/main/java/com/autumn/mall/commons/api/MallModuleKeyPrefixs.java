/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.api
 * 文件名: MallModuleKeyPrefixs
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
public interface MallModuleKeyPrefixs {

    /**
     * 商品微服务
     */
    String KEY_PREFIX_GOODS = "mall:product:goods:";
}