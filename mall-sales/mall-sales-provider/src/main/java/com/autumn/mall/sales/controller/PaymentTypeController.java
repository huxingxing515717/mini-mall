/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.controller
 * 文件名: PaymentTypeController
 * 日期: 2020/3/15 16:50
 * 说明:
 */
package com.autumn.mall.sales.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.sales.client.PaymentTypeApi;
import com.autumn.mall.sales.model.PaymentType;
import com.autumn.mall.sales.service.PaymentTypeService;
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
@Api(value = "付款方式管理")
@RestController
@RequestMapping("/paymenttype")
public class PaymentTypeController implements PaymentTypeApi {

    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑付款方式", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "付款方式实体类", required = true, dataType = "PaymentType")
    public ResponseResult<String> save(@Valid @RequestBody PaymentType entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, paymentTypeService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "付款方式id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<PaymentType> findById(@PathVariable("id") String id) {
        PaymentType entity = paymentTypeService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(paymentTypeService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询付款方式", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<PaymentType>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, paymentTypeService.query(definition));
    }
}