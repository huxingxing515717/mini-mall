/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.response
 * 文件名: CommonsResultCode
 * 日期: 2020/3/14 16:24
 * 说明:
 */
package com.autumn.mall.commons.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 通用响应操作码<p>
 * 所有的操作码都以：0xxxx开头。系统异常为99999。
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@ToString
@AllArgsConstructor
public enum CommonsResultCode implements ResultCode {

    SUCCESS(true, "00000", "操作成功！"),
    FAIL(false, "00001", "操作失败！"),
    INVALID_PARAM(false, "00002", "非法参数！"),
    ENTITY_IS_NOT_EXIST(false, "00003", "指定唯一标识的实体不存在！"),
    TRY_LOCKED_ERROR(false, "00004", "获取分布式锁过程中报错！"),
    SERVER_ERROR(false, "99999", "抱歉，系统繁忙，请稍后重试！");

    private boolean success;
    private String code;
    private String message;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}