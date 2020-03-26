/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.service
 * 文件名: CacheService
 * 日期: 2020/3/14 22:48
 * 说明:
 */
package com.autumn.mall.commons.service;

/**
 * 缓存服务的接口定义
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface CacheService {

    /**
     * 获取模块的key前缀常量定义，可选值{@link com.autumn.mall.commons.api.MallModuleKeyPrefixes}
     *
     * @return
     */
    String getModuleKeyPrefix();
}