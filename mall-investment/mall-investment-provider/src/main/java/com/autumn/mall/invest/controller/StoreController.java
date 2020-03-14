/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: StoreController
 * 日期: 2020/3/14 17:41
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.StoreApi;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 项目控制器
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Api(value = "项目管理")
@RestController
@RequestMapping("/store")
public class StoreController implements StoreApi {

    @Autowired
    private StoreService storeService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑项目", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "项目实体类", required = true, dataType = "Store")
    public ResponseResult<String> save(@Valid @RequestBody Store entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, storeService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "项目id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<Store> findById(@PathVariable("id") String id) {
        Store entity = storeService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(storeService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询项目", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Store>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, storeService.query(definition));
    }
}