/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.controller
 * 文件名: SubjectController
 * 日期: 2020/3/15 19:43
 * 说明:
 */
package com.autumn.mall.account.controller;

import com.autumn.mall.account.client.SubjectApi;
import com.autumn.mall.account.model.Subject;
import com.autumn.mall.account.service.SubjectService;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
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
@Api(value = "科目管理")
@RestController
@RequestMapping("/subject")
public class SubjectController implements SubjectApi {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    @ApiOperation(value = "新增或编辑科目", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "科目实体类", required = true, dataType = "Subject")
    public ResponseResult<String> save(@Valid @RequestBody Subject entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, subjectService.save(entity));
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "科目id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Subject> findById(@PathVariable("uuid") String uuid) {
        Subject entity = subjectService.findById(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "改变实体状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "targetState", value = "目标状态", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult changeState(@PathVariable("uuid") String uuid, @RequestParam("targetState") String targetState) {
        subjectService.changeState(uuid, UsingState.valueOf(targetState));
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询科目", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Subject>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Subject> queryResult = subjectService.query(definition);
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
        result.put("all", subjectService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.using.name());
        result.put(UsingState.using.name(), subjectService.query(definition).getTotal());
        definition.getFilter().put("state", UsingState.disabled.name());
        result.put(UsingState.disabled.name(), subjectService.query(definition).getTotal());
        return result;
    }
}