/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: FloorController
 * 日期: 2020/3/15 14:20
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.FloorApi;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.model.Floor;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.FloorService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class FloorController extends AbstractSupportStateController<Floor, UsingState> implements FloorApi {

    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return floorService;
    }

    @Override
    public CrudService<Floor> getCrudService() {
        return floorService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }

    @Override
    public void fetchParts(List<Floor> floors, List<String> fetchParts) {
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