/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: PositionController
 * 日期: 2020/3/15 15:06
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.PositionApi;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.model.Floor;
import com.autumn.mall.invest.model.Position;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.FloorService;
import com.autumn.mall.invest.service.PositionService;
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
 * 铺位控制器
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "铺位管理")
@RestController
@RequestMapping("/position")
public class PositionController implements PositionApi {

    @Autowired
    private PositionService positionService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @PostMapping
    @ApiOperation(value = "新增或编辑铺位", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "铺位实体类", required = true, dataType = "Position")
    public ResponseResult<String> save(@Valid @RequestBody Position entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, positionService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "铺位uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Position> findById(@PathVariable("uuid") String uuid) {
        Position entity = positionService.findById(uuid);
        entity.setStore(storeService.findById(entity.getStoreUuid()));
        entity.setBuilding(buildingService.findById(entity.getBuildingUuid()));
        entity.setFloor(floorService.findById(entity.getFloorUuid()));
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        positionService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询铺位", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Position>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Position> queryResult = positionService.query(definition);
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
        result.put("all", positionService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), positionService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), positionService.query(definition).getTotal());
        return result;
    }

    private void fetchParts(List<Position> positions, List<String> fetchParts) {
        if (positions.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        Set<String> storeUuids = new HashSet<>();
        Set<String> buildingUuids = new HashSet<>();
        Set<String> floorUuids = new HashSet<>();
        positions.stream().forEach(position -> {
            storeUuids.add(position.getStoreUuid());
            buildingUuids.add(position.getBuildingUuid());
            floorUuids.add(position.getFloorUuid());
        });
        // 项目
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeService.findAllByIds(storeUuids) : new HashMap<>();
        // 楼宇
        Map<String, Building> buildingMap = fetchParts.contains("building") ? buildingService.findAllByIds(buildingUuids) : new HashMap<>();
        // 楼层
        Map<String, Floor> floorMap = fetchParts.contains("floor") ? floorService.findAllByIds(floorUuids) : new HashMap<>();
        positions.stream().forEach(position -> {
            position.setStore(storeMap.get(position.getStoreUuid()));
            position.setBuilding(buildingMap.get(position.getBuildingUuid()));
            position.setFloor(floorMap.get(position.getFloorUuid()));
        });
    }
}