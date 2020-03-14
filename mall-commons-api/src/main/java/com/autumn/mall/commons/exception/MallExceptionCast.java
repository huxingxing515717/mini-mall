/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.exception
 * 文件名: MallExceptionCast
 * 日期: 2020/3/14 16:38
 * 说明:
 */
package com.autumn.mall.commons.exception;

import com.autumn.mall.commons.response.ResultCode;

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