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
 * 招商微服务的响应操作码<p>
 * 该微服务下所有的操作码都以：1xxxx开头。
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Getter
@ToString
@AllArgsConstructor
public enum InvestResultCode implements ResultCode {

    CODE_IS_EXISTS(false, "10001", "代码已存在，禁止操作！"),
    CODE_IS_NOT_ALLOW_MODIFY(false, "10002", "代码不允许修改！");

    private boolean success;
    private String code;
    private String message;
}