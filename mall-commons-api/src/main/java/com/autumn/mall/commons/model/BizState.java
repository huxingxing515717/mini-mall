/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.model
 * 文件名: BizState
 * 日期: 2020/3/16 19:38
 * 说明:
 */
package com.autumn.mall.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
@Getter
@AllArgsConstructor
public enum BizState {
    ineffect("未生效"), effect("已生效");

    private String caption;
}