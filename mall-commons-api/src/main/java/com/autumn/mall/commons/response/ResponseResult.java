/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.response
 * 文件名: ResponseResult
 * 日期: 2020/3/14 16:33
 * 说明:
 */
package com.autumn.mall.commons.response;

import lombok.Data;
import lombok.ToString;

/**
 * 响应结果
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@ToString
public class ResponseResult<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(ResultCode resultCode) {
        this(resultCode, null);
    }

    public ResponseResult(ResultCode resultCode, T data) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * 操作成功
     *
     * @return
     */
    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public static ResponseResult FALL() {
        return new ResponseResult(CommonsResultCode.FAIL);
    }
}