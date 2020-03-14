/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.exception
 * 文件名: MallExceptionCast
 * 日期: 2020/3/14 16:38
 * 说明:
 */
package com.autumn.mall.exception;

import com.autumn.mall.response.ResultCode;

/**
 * 异常抛出类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public class MallExceptionCast {

    public static void cast(ResultCode resultCode) {
        throw new MallException(resultCode);
    }
}