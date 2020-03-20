/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.controller
 * 文件名: GoodsController
 * 日期: 2020/3/15 20:39
 * 说明:
 */
package com.autumn.mall.product.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.product.client.GoodsApi;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.service.GoodsService;
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
@Api(value = "商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController implements GoodsApi {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑商品", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商品实体类", required = true, dataType = "Goods")
    public ResponseResult<String> save(@Valid @RequestBody Goods entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "商品id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Goods> findById(@PathVariable("id") String id) {
        Goods entity = goodsService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(goodsService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商品", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Goods>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsService.query(definition));
    }
}