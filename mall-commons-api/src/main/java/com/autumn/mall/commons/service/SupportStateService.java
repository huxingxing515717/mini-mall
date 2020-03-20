/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.service
 * 文件名: SupportStateService
 * 日期: 2020/3/19 8:39
 * 说明:
 */
package com.autumn.mall.commons.service;

/**
 * 支持使用状态的业务层接口
 *
 * @author Anbang713
 * @create 2020/3/19
 */
public interface SupportStateService<S> {

    /**
     * 修改状态
     *
     * @param uuid        唯一标识
     * @param targetState 目标状态
     */
    void changeState(String uuid, S targetState);
}