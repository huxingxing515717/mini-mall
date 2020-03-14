/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.exception
 * 文件名: MallException
 * 日期: 2020/3/14 16:37
 * 说明:
 */
package com.autumn.mall.exception;

import com.autumn.mall.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常类型
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Getter
@AllArgsConstructor
public class MallException extends RuntimeException {

    private ResultCode resultCode;
}