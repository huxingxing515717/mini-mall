/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.service
 * 文件名: PaymentTypeServiceImpl
 * 日期: 2020/3/15 16:46
 * 说明:
 */
package com.autumn.mall.sales.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.sales.model.PaymentType;
import com.autumn.mall.sales.repository.PaymentTypeRepository;
import com.autumn.mall.sales.response.SalesResultCode;
import com.autumn.mall.sales.specification.PaymentTypeSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 付款方式业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class PaymentTypeServiceImpl extends AbstractServiceImpl<PaymentType> implements PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentTypeSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(PaymentType entity) {
        super.doBeforeSave(entity);
        // 不允许存在代码重复的付款方式
        Optional<PaymentType> optional = paymentTypeRepository.findByCode(entity.getCode());
        if (optional.isPresent()) {
            if (entity.getUuid() == null || entity.getUuid().equals(optional.get().getUuid()) == false) {
                MallExceptionCast.cast(SalesResultCode.CODE_IS_EXISTS);
            }
            // 如果是已禁用状态，不允许修改
            if (optional.get().getState().equals(UsingState.disabled)) {
                MallExceptionCast.cast(SalesResultCode.ENTITY_IS_DISABLED);
            }
        }
        // 如果是编辑，则代码不允许修改
        if (StringUtils.isNotBlank(entity.getUuid())) {
            PaymentType tenant = findById(entity.getUuid());
            if (tenant.getCode().equals(entity.getCode()) == false) {
                MallExceptionCast.cast(SalesResultCode.CODE_IS_NOT_ALLOW_MODIFY);
            }
        }
    }

    @Override
    public void changeState(String uuid, UsingState targetState) {
        if (StringUtils.isBlank(uuid) || targetState == null) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        Optional<PaymentType> optional = getRepository().findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        PaymentType entity = optional.get();
        if ((UsingState.using.equals(targetState) && UsingState.using.equals(entity.getState())
                || (UsingState.disabled.equals(targetState) && UsingState.disabled.equals(entity.getState())))) {
            MallExceptionCast.cast(SalesResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        entity.setState(targetState);
        getRepository().save(entity);
        saveOperationLog(uuid, UsingState.using.equals(targetState) ? "启用" : "停用");
        doAfterSave(entity);
    }

    @Override
    public PaymentTypeRepository getRepository() {
        return paymentTypeRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:sales:paymenttype:";
    }
}