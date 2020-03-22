/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: TenantController
 * 日期: 2020/3/15 16:09
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.TenantApi;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.service.TenantService;
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
@Api(value = "商户管理")
@RestController
@RequestMapping("/tenant")
public class TenantController extends AbstractSupportStateController<Tenant, UsingState> implements TenantApi {

    @Autowired
    private TenantService tenantService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return tenantService;
    }

    @Override
    public CrudService<Tenant> getCrudService() {
        return tenantService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}