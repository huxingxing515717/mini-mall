/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 楼宇名：com.autumn.mall.invest.controller
 * 文件名: BuildingController
 * 日期: 2020/3/15 13:22
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.BuildingApi;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 楼宇控制器
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "楼宇管理")
@RestController
@RequestMapping("/building")
public class BuildingController implements BuildingApi {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @PostMapping
    @ApiOperation(value = "新增或编辑楼宇", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "楼宇实体类", required = true, dataType = "Building")
    public ResponseResult<String> save(@Valid @RequestBody Building entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, buildingService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "楼宇uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Building> findById(@PathVariable("uuid") String uuid) {
        Building entity = buildingService.findById(uuid);
        entity.setStore(storeService.findById(entity.getStoreUuid()));
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        buildingService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询楼宇", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Building>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Building> queryResult = buildingService.query(definition);
        fetchParts(queryResult.getRecords(), definition.getFetchParts());
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
        result.put("all", buildingService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), buildingService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), buildingService.query(definition).getTotal());
        return result;
    }

    private void fetchParts(List<Building> buildings, List<String> fetchParts) {
        if (buildings.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        if (fetchParts.contains("store")) {
            Set<String> storeUuids = buildings.stream().map(building -> building.getStoreUuid()).collect(Collectors.toSet());
            Map<String, Store> storeMap = storeService.findAllByIds(storeUuids);
            buildings.stream().forEach(building -> building.setStore(storeMap.get(building.getStoreUuid())));
        }
    }
}