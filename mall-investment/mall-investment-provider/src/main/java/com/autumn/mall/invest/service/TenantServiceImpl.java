/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: TenantServiceImpl
 * 日期: 2020/3/15 16:06
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.repository.TenantRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.TenantSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 商户业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class TenantServiceImpl extends AbstractServiceImpl<Tenant> implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private TenantSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Tenant entity) {
        super.doBeforeSave(entity);
        // 不允许存在代码重复的商户
        Optional<Tenant> optional = tenantRepository.findByCode(entity.getCode());
        if (optional.isPresent() && (entity.getId() == null || entity.getId().equals(optional.get().getId()) == false)) {
            MallExceptionCast.cast(InvestResultCode.CODE_IS_EXISTS);
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getId())) {
            Tenant tenant = findById(entity.getId());
            if (tenant.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(InvestResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public TenantRepository getRepository() {
        return tenantRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:invest:tenant:";
    }
}