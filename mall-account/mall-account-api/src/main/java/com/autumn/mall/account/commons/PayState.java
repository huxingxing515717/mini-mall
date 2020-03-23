/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.commons
 * 文件名: PayState
 * 日期: 2020/3/23 21:03
 * 说明:
 */
package com.autumn.mall.account.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收款状态
 *
 * @author Anbang713
 * @create 2020/3/23
 */
@Getter
@AllArgsConstructor
public enum PayState {
    unPay("未收款"), payed("已收款");

    private String caption;
}