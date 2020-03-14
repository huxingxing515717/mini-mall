/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.client
 * 文件名: OperationLogApi
 * 日期: 2020/3/14 21:06
 * 说明:
 */
package com.autumn.mall.basis.client;

import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.commons.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 操作日志客户端接口
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface OperationLogApi {

    /**
     * 通过实体标识查询所有操作日志
     *
     * @param entityKey
     * @return
     */
    @GetMapping("/{entityKey}")
    ResponseResult<List<OperationLog>> findAllByEntityKey(@PathVariable("entityKey") String entityKey);
}