/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: BizTypeController
 * 日期: 2020/3/15 16:27
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.BizTypeApi;
import com.autumn.mall.invest.model.BizType;
import com.autumn.mall.invest.service.BizTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "业态管理")
@RestController
@RequestMapping("/biztype")
public class BizTypeController implements BizTypeApi {

    @Autowired
    private BizTypeService bizTypeService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑业态", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "业态实体类", required = true, dataType = "BizType")
    public ResponseResult<String> save(@Valid @RequestBody BizType entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, bizTypeService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "业态id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<BizType> findById(@PathVariable("id") String id) {
        BizType entity = bizTypeService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(bizTypeService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询业态", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<BizType>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, bizTypeService.query(definition));
    }
}