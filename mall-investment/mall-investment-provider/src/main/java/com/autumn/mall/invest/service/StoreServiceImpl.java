/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: StoreServiceImpl
 * 日期: 2020/3/14 19:51
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.repository.StoreRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.StoreSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 项目业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Service
public class StoreServiceImpl extends AbstractServiceImpl<Store> implements StoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Store entity) {
        super.doBeforeSave(entity);
        // 不允许存在代码重复的项目
        Optional<Store> optional = storeRepository.findByCode(entity.getCode());
        if (optional.isPresent() && (entity.getId() == null || entity.getId().equals(optional.get().getId()) == false)) {
            MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getId())) {
            Store store = findById(entity.getId());
            if (store.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public StoreRepository getRepository() {
        return storeRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:invest:store:";
    }
}