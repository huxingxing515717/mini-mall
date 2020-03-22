/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.controller
 * 文件名: GoodsController
 * 日期: 2020/3/15 20:39
 * 说明:
 */
package com.autumn.mall.product.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.product.client.GoodsApi;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @PostMapping
    @ApiOperation(value = "新增或编辑商品", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商品实体类", required = true, dataType = "Goods")
    public ResponseResult<String> save(@Valid @RequestBody Goods entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "商品id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Goods> findById(@PathVariable("uuid") String uuid) {
        Goods entity = goodsService.findById(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/ids")
    @ApiOperation(value = "根据ids获取实体对象", httpMethod = "POST")
    @ApiImplicitParam(name = "uuids", value = "商品uuid集合", required = true, dataType = "List", paramType = "body")
    public ResponseResult<Map<String, Goods>> findAllByIds(@RequestBody Set<String> uuids) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsService.findAllByIds(uuids));
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        goodsService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商品", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Goods>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Goods> queryResult = goodsService.query(definition);
        SummaryQueryResult summaryQueryResult = SummaryQueryResult.newInstance(queryResult);
        summaryQueryResult.getSummary().putAll(querySummary(definition));
        return new ResponseResult(CommonsResultCode.SUCCESS, summaryQueryResult);
    }

    private Map<String, Object> querySummary(QueryDefinition definition) {
        Map<String, Object> result = new HashMap<>();
        if (definition.isQuerySummary() == false) {
            return result;
        }
        definition.setPageSize(1);
        definition.getFilter().put("state", null);
        result.put("all", goodsService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), goodsService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), goodsService.query(definition).getTotal());
        return result;
    }
}