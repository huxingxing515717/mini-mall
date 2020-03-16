/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.controller
 * 文件名: OperationLogController
 * 日期: 2020/3/14 21:21
 * 说明:
 */
package com.autumn.mall.basis.controller;

import com.autumn.mall.basis.client.OperationLogApi;
import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.basis.service.OperationLogService;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
@Api(value = "操作日志管理")
@RestController
@RequestMapping("/operationlog")
public class OperationLogController implements OperationLogApi {

    @Autowired
    private OperationLogService operationLogService;

    @Override
    @GetMapping("/{entityKey}")
    @ApiOperation(value = "根据实体标识查询操作日志", httpMethod = "GET")
    @ApiImplicitParam(name = "entityKey", value = "实体标识", required = true, dataType = "String")
    public ResponseResult<List<OperationLog>> findAllByEntityKey(@PathVariable("entityKey") String entityKey) {
        return new ResponseResult(CommonsResultCode.SUCCESS, operationLogService.findAllByEntityKey(entityKey));
    }
}