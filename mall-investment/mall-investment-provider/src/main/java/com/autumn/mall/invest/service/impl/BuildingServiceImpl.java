/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: BuildingServiceImpl
 * 日期: 2020/3/15 13:12
 * 说明:
 */
package com.autumn.mall.invest.service.impl;

import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.repository.OrderBuilder;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.order.BuildingOrderBuilder;
import com.autumn.mall.invest.repository.BuildingRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.service.BuildingService;
import com.autumn.mall.invest.specification.BuildingSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 楼宇业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class BuildingServiceImpl extends AbstractServiceImpl<Building> implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Building entity) {
        super.doBeforeSave(entity);
        // 同一项目下，不允许存在代码重复的楼宇
        Optional<Building> optional = buildingRepository.findByStoreUuidAndCode(entity.getStoreUuid(), entity.getCode());
        if (optional.isPresent()) {
            if (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
            }
            // 如果是已禁用状态，不允许修改
            if (optional.get().getState().equals(UsingState.disabled)) {
                MallExceptionCast.cast(InvestResultCode.ENTITY_IS_DISABLED);
            }
        }
        // 如果是编辑，则项目和代码都不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            Building building = findById(entity.getUuid());
            if (building.getStoreUuid().equals(entity.getStoreUuid()) == false) {
                MallExceptionCast.cast(InvestResultCode.STORE_IS_NOT_ALLOW_MODIFY);
            }
            if (building.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public void changeState(String uuid, UsingState targetState) {
        if (StringUtils.isBlank(uuid) || targetState == null) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        Optional<Building> optional = buildingRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        Building building = optional.get();
        if ((UsingState.using.equals(targetState) && UsingState.using.equals(building.getState())
                || (UsingState.disabled.equals(targetState) && UsingState.disabled.equals(building.getState())))) {
            MallExceptionCast.cast(InvestResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        building.setState(targetState);
        buildingRepository.save(building);
        saveOperationLog(uuid, UsingState.using.equals(targetState) ? "启用" : "停用");
        doAfterSave(building);
    }

    @Override
    public BuildingRepository getRepository() {
        return buildingRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public OrderBuilder getOrderBuilder() {
        return new BuildingOrderBuilder();
    }

    @Override
    public String getModuleKeyPrefix() {
        return MallModuleKeyPrefixes.INVEST_KEY_PREFIX_OF_BUILDING;
    }
}