/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: BizTypeController
 * 日期: 2020/3/15 16:27
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.client.BizTypeApi;
import com.autumn.mall.invest.model.BizType;
import com.autumn.mall.invest.service.BizTypeService;
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
@Api(value = "业态管理")
@RestController
@RequestMapping("/biztype")
public class BizTypeController extends AbstractSupportStateController<BizType, UsingState> implements BizTypeApi {

    @Autowired
    private BizTypeService bizTypeService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return bizTypeService;
    }

    @Override
    public CrudService<BizType> getCrudService() {
        return bizTypeService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}