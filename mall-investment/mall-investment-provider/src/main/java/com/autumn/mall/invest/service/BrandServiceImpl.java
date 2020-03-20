/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: BrandServiceImpl
 * 日期: 2020/3/15 15:32
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Brand;
import com.autumn.mall.invest.repository.BrandRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.BrandSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 品牌业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class BrandServiceImpl extends AbstractServiceImpl<Brand> implements BrandService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Brand entity) {
        super.doBeforeSave(entity);
        // 不允许存在代码重复的项目
        Optional<Brand> optional = brandRepository.findByCode(entity.getCode());
        if (optional.isPresent() && (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false)) {
            MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            Brand store = findById(entity.getUuid());
            if (store.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public BrandRepository getRepository() {
        return brandRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:invest:brand:";
    }
}