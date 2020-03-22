/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: PositionController
 * 日期: 2020/3/15 15:06
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class PositionController extends AbstractSupportStateController<Position, UsingState> implements PositionApi {

    @Autowired
    private PositionService positionService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return positionService;
    }

    @Override
    public CrudService<Position> getCrudService() {
        return positionService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }

    @Override
    public void fetchParts(List<Position> positions, List<String> fetchParts) {
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