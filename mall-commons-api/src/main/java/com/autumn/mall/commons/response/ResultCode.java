/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.model.response
 * 文件名: ResultCode
 * 日期: 2020/3/14 16:23
 * 说明:
 */
package com.autumn.mall.commons.response;

/**
 * 响应操作码
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface ResultCode {

    /**
     * 操作是否成功，true为成功，false为失败
     *
     * @return
     */
    boolean isSuccess();

    /**
     * 操作码
     *
     * @return
     */
    String getCode();

    /**
     * 提示信息
     *
     * @return
     */
    String getMessage();
}