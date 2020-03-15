/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.response
 * 文件名: CustomResultCode
 * 日期: 2020/3/15 12:27
 * 说明:
 */
package com.autumn.mall.commons.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义响应操作码
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResultCode implements ResultCode {

    private boolean success;
    private String code;
    private String message;
}