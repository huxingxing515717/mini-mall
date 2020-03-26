/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: FloorServiceImpl
 * 日期: 2020/3/15 14:13
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.repository.OrderBuilder;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Floor;
import com.autumn.mall.invest.order.FloorOrderBuilder;
import com.autumn.mall.invest.repository.FloorRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.FloorSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 楼层业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class FloorServiceImpl extends AbstractServiceImpl<Floor> implements FloorService {

    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private FloorSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Floor entity) {
        super.doBeforeSave(entity);
        // 同一项目、同一楼宇下，不允许存在代码重复的楼宇
        Optional<Floor> optional = floorRepository.findByStoreUuidAndBuildingUuidAndCode(entity.getStoreUuid(), entity.getBuildingUuid(), entity.getCode());
        if (optional.isPresent()) {
            if (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
            }
            // 如果是已禁用状态，不允许修改
            if (optional.get().getState().equals(UsingState.disabled)) {
                MallExceptionCast.cast(InvestResultCode.ENTITY_IS_DISABLED);
            }
        }
        // 如果是编辑，则项目、楼宇和代码都不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            Floor floor = findById(entity.getUuid());
            if (floor.getStoreUuid().equals(entity.getStoreUuid()) == false) {
                MallExceptionCast.cast(InvestResultCode.STORE_IS_NOT_ALLOW_MODIFY);
            }
            if (floor.getBuildingUuid().equals(entity.getBuildingUuid()) == false) {
                MallExceptionCast.cast(InvestResultCode.BUILDING_IS_NOT_ALLOW_MODIFY);
            }
            if (floor.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public void changeState(String uuid, UsingState targetState) {
        if (StringUtils.isBlank(uuid) || targetState == null) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        Optional<Floor> optional = floorRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        Floor floor = optional.get();
        if ((UsingState.using.equals(targetState) && UsingState.using.equals(floor.getState())
                || (UsingState.disabled.equals(targetState) && UsingState.disabled.equals(floor.getState())))) {
            MallExceptionCast.cast(InvestResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        floor.setState(targetState);
        floorRepository.save(floor);
        saveOperationLog(uuid, UsingState.using.equals(targetState) ? "启用" : "停用");
        doAfterSave(floor);
    }

    @Override
    public FloorRepository getRepository() {
        return floorRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public OrderBuilder getOrderBuilder() {
        return new FloorOrderBuilder();
    }

    @Override
    public String getModuleKeyPrefix() {
        return MallModuleKeyPrefixes.INVEST_KEY_PREFIX_OF_FLOOR;
    }
}