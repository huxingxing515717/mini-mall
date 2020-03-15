/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.service
 * 文件名: GoodsServiceImpl
 * 日期: 2020/3/15 20:36
 * 说明:
 */
package com.autumn.mall.product.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
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
        if (optional.isPresent() && (entity.getId() == null || entity.getId().equals(optional.get().getId()) == false)) {
            MallExceptionCast.cast(ProductResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getId())) {
            Goods subject = findById(entity.getId());
            if (subject.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(ProductResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
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
        return "mall:product:goods:";
    }
}