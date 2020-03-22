/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.controller
 * 文件名: GoodsController
 * 日期: 2020/3/15 20:39
 * 说明:
 */
package com.autumn.mall.product.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.product.client.GoodsApi;
import com.autumn.mall.product.model.Goods;
import com.autumn.mall.product.service.GoodsService;
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
@Api(value = "商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController extends AbstractSupportStateController<Goods, UsingState> implements GoodsApi {

    @Autowired
    private GoodsService goodsService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return goodsService;
    }

    @Override
    public CrudService<Goods> getCrudService() {
        return goodsService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}