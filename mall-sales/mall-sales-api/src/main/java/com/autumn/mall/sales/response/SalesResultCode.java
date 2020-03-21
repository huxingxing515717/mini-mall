/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.response
 * 文件名: SalesResultCode
 * 日期: 2020/3/15 16:39
 * 说明:
 */
package com.autumn.mall.sales.response;

import com.autumn.mall.commons.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 销售微服务的响应操作码<p>
 * 该微服务下所有的操作码都以：2xxxx开头。
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Getter
@ToString
@AllArgsConstructor
public enum SalesResultCode implements ResultCode {

    CODE_IS_EXISTS(false, "20001", "代码已存在，禁止操作！"),
    CODE_IS_NOT_ALLOW_MODIFY(false, "20002", "代码不允许修改！"),
    ENTITY_IS_DISABLED(false, "20003", "已停用状态的资料，不允许修改！"),
    ENTITY_IS_EQUALS_TARGET_STATE(false, "20004", "资料已经是目标状态，禁止重复操作！");

    private boolean success;
    private String code;
    private String message;
}