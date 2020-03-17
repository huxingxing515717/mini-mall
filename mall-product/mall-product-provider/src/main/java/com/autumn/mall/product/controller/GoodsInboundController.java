/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.controller
 * 文件名: GoodsInboundController
 * 日期: 2020/3/17 8:16
 * 说明:
 */
package com.autumn.mall.product.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.model.GoodsInbound;
import com.autumn.mall.product.service.GoodsInboundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 商品入库单
 *
 * @author Anbang713
 * @create 2020/3/17
 */
@Api(value = "商品入库单管理")
@RestController
@RequestMapping("/goodsinbound")
public class GoodsInboundController {

    @Autowired
    private GoodsInboundService goodsInboundService;
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑商品入库单", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商品入库单", required = true, dataType = "GoodsInbound")
    public ResponseResult<String> save(@Valid @RequestBody GoodsInbound entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsInboundService.save(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除实体对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "入库单id", required = true, dataType = "String", paramType = "path")
    public ResponseResult deleteById(@PathVariable("id") String id) {
        goodsInboundService.deleteById(id);
        return ResponseResult.SUCCESS();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "入库单id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<GoodsInbound> findById(@PathVariable("id") String id) {
        GoodsInbound entity = goodsInboundService.findById(id);
        entity.getDetails().addAll(goodsInboundService.findDetailsByIdOrderByLineNumber(id));
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(goodsInboundService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商品入库单", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Goods>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsInboundService.query(definition));
    }

    @PostMapping("/effect")
    @ApiOperation(value = "批量生效商品入库单", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true)
    public ResponseResult<Map> effect(@RequestBody List<String> ids) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsInboundService.doEffect(ids));
    }
}