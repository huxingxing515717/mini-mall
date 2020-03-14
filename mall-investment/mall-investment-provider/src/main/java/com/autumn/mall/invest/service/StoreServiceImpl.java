/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: StoreServiceImpl
 * 日期: 2020/3/14 19:51
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.repository.StoreRepository;
import com.autumn.mall.invest.specification.StoreSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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