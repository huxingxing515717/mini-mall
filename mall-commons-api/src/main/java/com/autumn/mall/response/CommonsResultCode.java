/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.response
 * 文件名: CommonsResultCode
 * 日期: 2020/3/14 16:24
 * 说明:
 */
package com.autumn.mall.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 通用响应操作码
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@ToString
@AllArgsConstructor
public enum CommonsResultCode implements ResultCode {

    SUCCESS(true, 00000, "操作成功！"),
    FAIL(false, 00001, "操作失败！"),
    INVALID_PARAM(false, 00002, "非法参数！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");

    private boolean success;
    private int code;
    private String message;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}