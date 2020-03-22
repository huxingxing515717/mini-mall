/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.controller
 * 文件名: AbstractSupportStateController
 * 日期: 2020/3/22 20:44
 * 说明:
 */
package com.autumn.mall.commons.controller;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.service.SupportStateService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支持状态的抽象控制器
 *
 * @author Anbang713
 * @create 2020/3/22
 */
public abstract class AbstractSupportStateController<T extends IsEntity, S extends Enum> extends AbstractController<T> {

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") S targetState) {
        getSupportStateService().changeState(uuid, targetState);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    public abstract SupportStateService<S> getSupportStateService();
}