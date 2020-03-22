/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.controller
 * 文件名: SalesInputController
 * 日期: 2020/3/22 15:28
 * 说明:
 */
package com.autumn.mall.sales.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.client.StockClient;
import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.controller.AbstractController;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.product.client.GoodsClient;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.sales.client.SalesInputApi;
import com.autumn.mall.sales.model.SalesInput;
import com.autumn.mall.sales.model.SalesInputDetail;
import com.autumn.mall.sales.service.SalesInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售数据录入单
 *
 * @author Anbang713
 * @create 2020/3/22
 */
@Api(value = "销售数据录入单管理")
@RestController
@RequestMapping("/salesinput")
public class SalesInputController extends AbstractController<SalesInput> implements SalesInputApi {

    @Autowired
    private SalesInputService salesInputService;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private StockClient stockClient;

    @Override
    public CrudService<SalesInput> getCrudService() {
        return salesInputService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(BizState.ineffect.name(), BizState.effect.name());
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fetchPropertyInfo", value = "是否获取实体相关联的其它实体完整信息，默认为true", required = true, dataType = "Boolean", paramType = "query")
    })
    public ResponseResult<SalesInput> findById(@PathVariable("uuid") String uuid, @RequestParam(value = "fetchPropertyInfo", defaultValue = "true") boolean fetchPropertyInfo) {
        SalesInput entity = salesInputService.findById(uuid);
        // 如果details不为空，那就是从redis缓存中取出来的，我们不处理。
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            entity.setDetails(salesInputService.findDetailsByUuid(uuid));
            fetchGoods(entity.getDetails());
        }
        // 如果是未生效，实时获取库存数量
        if (entity.getState().equals(BizState.ineffect)) {
            fetchWarehouseQty(entity.getDetails());
        }
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "生效录入单", httpMethod = "PUT")
    @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult doEffect(@PathVariable("uuid") String uuid) {
        salesInputService.doEffect(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    public void fetchParts(List<SalesInput> records, List<String> fetchParts) {
        if (records.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
    }

    private void fetchWarehouseQty(List<SalesInputDetail> details) {
        List<String> entityKeys = new ArrayList<>();
        details.stream().forEach(detail -> entityKeys.add(MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS + detail.getGoodsUuid()));
        Map<String, StockInfo> stockInfoMap = stockClient.findByEntityKeys(entityKeys).getData();
        details.stream().forEach(detail -> {
            StockInfo stockInfo = stockInfoMap.get(MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS + detail.getGoodsUuid());
            detail.setWarehouseQty(BigDecimal.ZERO);
            stockInfo.getWarehouseInfos().stream().forEach(warehouseInfo -> {
                if (warehouseInfo.getWarehouse().equals(detail.getWarehouse())) {
                    detail.setWarehouseQty(warehouseInfo.getQuantity());
                }
            });
        });
    }

    private void fetchGoods(List<SalesInputDetail> details) {
        Set<String> goodsUuids = new HashSet<>();
        details.stream().forEach(detail -> goodsUuids.add(detail.getGoodsUuid()));
        Map<String, Goods> goodsMap = goodsClient.findAllByIds(goodsUuids).getData();
        details.stream().forEach(detail -> detail.setGoods(goodsMap.get(detail.getGoodsUuid())));
    }
}