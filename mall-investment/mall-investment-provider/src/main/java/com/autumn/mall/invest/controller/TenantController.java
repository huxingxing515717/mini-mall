/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: TenantController
 * 日期: 2020/3/15 16:09
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.TenantApi;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.service.TenantService;
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
@Api(value = "商户管理")
@RestController
@RequestMapping("/tenant")
public class TenantController implements TenantApi {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑商戶", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商戶实体类", required = true, dataType = "Tenant")
    public ResponseResult<String> save(@Valid @RequestBody Tenant entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, tenantService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商戶id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<Tenant> findById(@PathVariable("id") String id) {
        Tenant entity = tenantService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(tenantService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商戶", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Tenant>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, tenantService.query(definition));
    }
}