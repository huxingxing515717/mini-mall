/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.response
 * 文件名: InvestResultCode
 * 日期: 2020/3/14 17:11
 * 说明:
 */
package com.autumn.mall.invest.response;

import com.autumn.mall.commons.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 招商微服务的响应操作码
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Getter
@ToString
@AllArgsConstructor
public enum InvestResultCode implements ResultCode {

    ENTITY_IS_STOPPED(false, 10001, "当前实体为已停用状态，禁止操作！");

    private boolean success;
    private int code;
    private String message;
}