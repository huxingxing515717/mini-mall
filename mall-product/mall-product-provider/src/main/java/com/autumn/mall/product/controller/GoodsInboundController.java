/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.controller
 * 文件名: GoodsInboundController
 * 日期: 2020/3/17 8:16
 * 说明:
 */
package com.autumn.mall.product.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.client.StockClient;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.model.GoodsInbound;
import com.autumn.mall.product.model.GoodsInboundDetail;
import com.autumn.mall.product.service.GoodsInboundService;
import com.autumn.mall.product.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

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
    private GoodsService goodsService;
    @Autowired
    private StockClient stockClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑商品入库单", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商品入库单", required = true, dataType = "GoodsInbound")
    public ResponseResult<String> save(@Valid @RequestBody GoodsInbound entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, goodsInboundService.save(entity));
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "根据id删除实体对象", httpMethod = "DELETE")
    @ApiImplicitParam(name = "uuid", value = "入库单uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult deleteById(@PathVariable("uuid") String uuid) {
        goodsInboundService.deleteById(uuid);
        return ResponseResult.SUCCESS();
    }

    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "入库单id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<GoodsInbound> findById(@PathVariable("uuid") String uuid) {
        GoodsInbound entity = goodsInboundService.findById(uuid);
        // 如果details不为空，那就是从redis缓存中取出来的，我们不处理。
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            entity.setDetails(goodsInboundService.findDetailsByIdOrderByLineNumber(uuid));
            fetchGoods(entity.getDetails());
        }
        // 如果是未生效，实时获取库存数量
        if (entity.getState().equals(BizState.ineffect)) {
            fetchWarehouseQty(entity.getWarehouse(), entity.getDetails());
        }
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "生效商品入库单", httpMethod = "PUT")
    @ApiImplicitParam(name = "uuid", value = "入库单uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult doEffect(@PathVariable("uuid") String uuid) {
        goodsInboundService.doEffect(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商品入库单", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<GoodsInbound>> query(@RequestBody QueryDefinition definition) {
        QueryResult<GoodsInbound> queryResult = goodsInboundService.query(definition);
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
        result.put("all", goodsInboundService.query(definition).getTotal());
        definition.getFilter().put("state", BizState.ineffect.name());
        result.put(BizState.ineffect.name(), goodsInboundService.query(definition).getTotal());
        definition.getFilter().put("state", BizState.effect.name());
        result.put(BizState.effect.name(), goodsInboundService.query(definition).getTotal());
        return result;
    }

    private void fetchWarehouseQty(String warehouse, List<GoodsInboundDetail> details) {
        List<String> entityKeys = new ArrayList<>();
        details.stream().forEach(detail -> entityKeys.add(goodsService.getModuleKeyPrefix() + detail.getGoodsUuid()));
        Map<String, StockInfo> stockInfoMap = stockClient.findByEntityKeys(entityKeys).getData();
        details.stream().forEach(detail -> {
            StockInfo stockInfo = stockInfoMap.get(goodsService.getModuleKeyPrefix() + detail.getGoodsUuid());
            detail.setWarehouseQty(BigDecimal.ZERO);
            stockInfo.getWarehouseInfos().stream().forEach(warehouseInfo -> {
                if (warehouseInfo.getWarehouse().equals(warehouse)) {
                    detail.setWarehouseQty(warehouseInfo.getQuantity());
                }
            });
        });
    }

    private void fetchGoods(List<GoodsInboundDetail> details) {
        Set<String> goodsUuids = new HashSet<>();
        details.stream().forEach(detail -> goodsUuids.add(detail.getGoodsUuid()));
        Map<String, Goods> goodsMap = goodsService.findAllByIds(goodsUuids);
        details.stream().forEach(detail -> detail.setGoods(goodsMap.get(detail.getGoodsUuid())));
    }
}