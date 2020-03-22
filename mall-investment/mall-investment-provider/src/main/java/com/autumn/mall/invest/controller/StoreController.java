/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: StoreController
 * 日期: 2020/3/14 17:41
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.StoreApi;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.service.StoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 项目控制器
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Api(value = "项目管理")
@RestController
@RequestMapping("/store")
public class StoreController extends AbstractSupportStateController<Store, UsingState> implements StoreApi {

    @Autowired
    private StoreService storeService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return storeService;
    }

    @Override
    public CrudService<Store> getCrudService() {
        return storeService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}