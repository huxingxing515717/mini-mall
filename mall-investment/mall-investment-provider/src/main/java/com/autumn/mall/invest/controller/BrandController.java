/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 品牌名：com.autumn.mall.invest.controller
 * 文件名: BrandController
 * 日期: 2020/3/15 15:36
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.BrandApi;
import com.autumn.mall.invest.model.Brand;
import com.autumn.mall.invest.service.BrandService;
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
@Api(value = "品牌管理")
@RestController
@RequestMapping("/brand")
public class BrandController extends AbstractSupportStateController<Brand, UsingState> implements BrandApi {

    @Autowired
    private BrandService brandService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return brandService;
    }

    @Override
    public CrudService<Brand> getCrudService() {
        return brandService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}