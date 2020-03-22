/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.controller
 * 文件名: PaymentTypeController
 * 日期: 2020/3/15 16:50
 * 说明:
 */
package com.autumn.mall.sales.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.sales.client.PaymentTypeApi;
import com.autumn.mall.sales.model.PaymentType;
import com.autumn.mall.sales.service.PaymentTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "付款方式管理")
@RestController
@RequestMapping("/paymenttype")
public class PaymentTypeController extends AbstractSupportStateController<PaymentType, UsingState> implements PaymentTypeApi {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return paymentTypeService;
    }

    @Override
    public CrudService<PaymentType> getCrudService() {
        return paymentTypeService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}