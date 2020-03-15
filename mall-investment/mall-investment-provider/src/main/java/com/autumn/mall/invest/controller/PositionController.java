/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: PositionController
 * 日期: 2020/3/15 15:06
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.PositionApi;
import com.autumn.mall.invest.model.Position;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.FloorService;
import com.autumn.mall.invest.service.PositionService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑铺位", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "铺位实体类", required = true, dataType = "Position")
    public ResponseResult<String> save(@Valid @RequestBody Position entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, positionService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "铺位id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<Position> findById(@PathVariable("id") String id) {
        Position entity = positionService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(positionService.getCacheKeyPrefix() + id).getData());
        entity.setStore(storeService.findById(entity.getStoreUuid()));
        entity.setBuilding(buildingService.findById(entity.getBuildingUuid()));
        entity.setFloor(floorService.findById(entity.getFloorUuid()));
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询铺位", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Position>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, positionService.query(definition));
    }
}