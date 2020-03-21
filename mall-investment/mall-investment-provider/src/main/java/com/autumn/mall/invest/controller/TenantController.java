/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: TenantController
 * 日期: 2020/3/15 16:09
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.TenantApi;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "商户管理")
@RestController
@RequestMapping("/tenant")
public class TenantController implements TenantApi {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    @ApiOperation(value = "新增或编辑商戶", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "商戶实体类", required = true, dataType = "Tenant")
    public ResponseResult<String> save(@Valid @RequestBody Tenant entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, tenantService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "商戶uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Tenant> findById(@PathVariable("uuid") String uuid) {
        return new ResponseResult(CommonsResultCode.SUCCESS, tenantService.findById(uuid));
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        tenantService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询商戶", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Tenant>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Tenant> queryResult = tenantService.query(definition);
        SummaryQueryResult summaryQueryResult = SummaryQueryResult.newInstance(queryResult);
        summaryQueryResult.getSummary().putAll(querySummary(definition));
        return new ResponseResult(CommonsResultCode.SUCCESS, summaryQueryResult);
    }

    private Map<String, Object> querySummary(QueryDefinition definition) {
        Map<String, Object> result = new HashMap<>();
        if (definition.isQuerySummary() == false) {
            return result;
        }
        definition.setPageSize(1);
        definition.getFilter().put("state", null);
        result.put("all", tenantService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), tenantService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), tenantService.query(definition).getTotal());
        return result;
    }
}