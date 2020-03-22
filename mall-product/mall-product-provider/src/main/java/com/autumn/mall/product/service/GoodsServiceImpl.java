/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.service
 * 文件名: GoodsServiceImpl
 * 日期: 2020/3/15 20:36
 * 说明:
 */
package com.autumn.mall.product.service;

import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.repository.GoodsRepository;
import com.autumn.mall.product.response.ProductResultCode;
import com.autumn.mall.product.specification.GoodsSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class GoodsServiceImpl extends AbstractServiceImpl<Goods> implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Goods entity) {
        super.doBeforeSave(entity);
        // 不允许存在代码重复的科目
        Optional<Goods> optional = goodsRepository.findByCode(entity.getCode());
        if (optional.isPresent()) {
            if (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false) {
                MallExceptionCast.cast(ProductResultCode.CODE_IS_EXISTS);
            }
            // 如果是已禁用状态，不允许修改
            if (optional.get().getState().equals(UsingState.disabled)) {
                MallExceptionCast.cast(ProductResultCode.ENTITY_IS_DISABLED);
            }
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            Goods subject = findById(entity.getUuid());
            if (subject.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(ProductResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public void changeState(String uuid, UsingState targetState) {
        if (StringUtils.isBlank(uuid) || targetState == null) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        Optional<Goods> optional = getRepository().findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        Goods entity = optional.get();
        if ((UsingState.using.equals(targetState) && UsingState.using.equals(entity.getState())
                || (UsingState.disabled.equals(targetState) && UsingState.disabled.equals(entity.getState())))) {
            MallExceptionCast.cast(ProductResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        entity.setState(targetState);
        getRepository().save(entity);
        saveOperationLog(uuid, UsingState.using.equals(targetState) ? "启用" : "停用");
        doAfterSave(entity);
    }

    @Override
    public GoodsRepository getRepository() {
        return goodsRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS;
    }
}