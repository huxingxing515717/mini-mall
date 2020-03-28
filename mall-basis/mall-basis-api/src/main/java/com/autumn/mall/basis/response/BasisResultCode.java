/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.response
 * 文件名: BasisResultCode
 * 日期: 2020/3/28 9:29
 * 说明:
 */
package com.autumn.mall.basis.response;

import com.autumn.mall.commons.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Anbang713
 * @create 2020/3/28
 */
@Getter
@ToString
@AllArgsConstructor
public enum BasisResultCode implements ResultCode {

    STOCK_IS_NOT_ENOUGH(false, "50001", "商品库存不够，禁止出库！");

    private boolean success;
    private String code;
    private String message;
}