/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: FloorController
 * 日期: 2020/3/15 14:20
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.FloorApi;
import com.autumn.mall.invest.model.Floor;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.FloorService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑楼层", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "楼层实体类", required = true, dataType = "Floor")
    public ResponseResult<String> save(@Valid @RequestBody Floor entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, floorService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "楼层id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<Floor> findById(@PathVariable("id") String id) {
        Floor entity = floorService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(floorService.getCacheKeyPrefix() + id).getData());
        entity.setStore(storeService.findById(entity.getStoreUuid()));
        entity.setBuilding(buildingService.findById(entity.getBuildingUuid()));
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询楼层", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Floor>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, floorService.query(definition));
    }
}