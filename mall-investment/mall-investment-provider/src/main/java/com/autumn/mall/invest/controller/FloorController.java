/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: FloorController
 * 日期: 2020/3/15 14:20
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.FloorApi;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.model.Floor;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.FloorService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 楼层控制器
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "楼层管理")
@RestController
@RequestMapping("/floor")
public class FloorController implements FloorApi {

    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @PostMapping
    @ApiOperation(value = "新增或编辑楼层", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "楼层实体类", required = true, dataType = "Floor")
    public ResponseResult<String> save(@Valid @RequestBody Floor entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, floorService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "楼层uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Floor> findById(@PathVariable("uuid") String uuid) {
        Floor entity = floorService.findById(uuid);
        entity.setStore(storeService.findById(entity.getStoreUuid()));
        entity.setBuilding(buildingService.findById(entity.getBuildingUuid()));
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        floorService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询楼层", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Floor>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Floor> queryResult = floorService.query(definition);
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
        result.put("all", floorService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), floorService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), floorService.query(definition).getTotal());
        return result;
    }

    private void fetchParts(List<Floor> floors, List<String> fetchParts) {
        if (floors.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        Set<String> storeUuids = new HashSet<>();
        Set<String> buildingUuids = new HashSet<>();
        floors.stream().forEach(floor -> {
            storeUuids.add(floor.getStoreUuid());
            buildingUuids.add(floor.getBuildingUuid());
        });
        // 项目
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeService.findAllByIds(storeUuids) : new HashMap<>();
        // 楼宇
        Map<String, Building> buildingMap = fetchParts.contains("building") ? buildingService.findAllByIds(buildingUuids) : new HashMap<>();
        floors.stream().forEach(floor -> {
            floor.setStore(storeMap.get(floor.getStoreUuid()));
            floor.setBuilding(buildingMap.get(floor.getBuildingUuid()));
        });
    }
}