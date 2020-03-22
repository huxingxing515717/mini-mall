/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 楼宇名：com.autumn.mall.invest.controller
 * 文件名: BuildingController
 * 日期: 2020/3/15 13:22
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.BuildingApi;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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
public class BuildingController extends AbstractSupportStateController<Building, UsingState> implements BuildingApi {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private StoreService storeService;

    @Override
    public CrudService<Building> getCrudService() {
        return buildingService;
    }

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return buildingService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }

    @Override
    protected void fetchParts(List<Building> buildings, List<String> fetchParts) {
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