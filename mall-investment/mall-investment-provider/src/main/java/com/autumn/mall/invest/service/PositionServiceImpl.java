/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: PositionServiceImpl
 * 日期: 2020/3/15 15:02
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Position;
import com.autumn.mall.invest.repository.PositionRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.PositionSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 铺位业务层实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class PositionServiceImpl extends AbstractServiceImpl<Position> implements PositionService {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Position entity) {
        super.doBeforeSave(entity);
        // 同一项目、同一楼宇、同一楼层下，不允许存在代码重复的楼宇
        Optional<Position> optional = positionRepository.findByStoreIdAndBuildingIdAndFloorIdAndCode(entity.getStoreId(), entity.getBuildingId(), entity.getFloorId(), entity.getCode());
        if (optional.isPresent() && (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false)) {
            MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则项目、楼宇、楼层和代码都不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            Position position = findById(entity.getUuid());
            if (position.getStoreId().equals(entity.getStoreId()) == false) {
                MallExceptionCast.cast(InvestResultCode.STORE_IS_NOT_ALLOW_MODIFY);
            }
            if (position.getBuildingId().equals(entity.getBuildingId()) == false) {
                MallExceptionCast.cast(InvestResultCode.BUILDING_IS_NOT_ALLOW_MODIFY);
            }
            if (position.getFloorId().equals(entity.getFloorId()) == false) {
                MallExceptionCast.cast(InvestResultCode.FLOOR_IS_NOT_ALLOW_MODIFY);
            }
            if (position.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public PositionRepository getRepository() {
        return positionRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:invest:position:";
    }
}