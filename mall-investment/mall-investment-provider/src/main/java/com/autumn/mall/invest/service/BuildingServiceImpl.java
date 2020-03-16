/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: BuildingServiceImpl
 * 日期: 2020/3/15 13:12
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Building;
import com.autumn.mall.invest.repository.BuildingRepository;
import com.autumn.mall.invest.response.InvestResultCode;
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
        Optional<Building> optional = buildingRepository.findByStoreIdAndCode(entity.getStoreId(), entity.getCode());
        if (optional.isPresent() && (entity.getId() == null || entity.getId().equals(optional.get().getId()) == false)) {
            MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则项目和代码都不允许修改
        if (StringUtils.isNotBlank(entity.getId())) {
            Building building = findById(entity.getId());
            if (building.getStoreId().equals(entity.getStoreId()) == false) {
                MallExceptionCast.cast(InvestResultCode.STORE_IS_NOT_ALLOW_MODIFY);
            }
            if (building.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
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
    public String getCacheKeyPrefix() {
        return "mall:invest:building:";
    }
}