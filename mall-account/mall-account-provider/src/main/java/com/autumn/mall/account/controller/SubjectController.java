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
import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @Autowired
    private OperationLogClient operationLogClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑科目", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "科目实体类", required = true, dataType = "Subject")
    public ResponseResult<String> save(@Valid @RequestBody Subject entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, subjectService.save(entity));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "科目id", required = true, dataType = "Long", paramType = "path")
    public ResponseResult<Subject> findById(@PathVariable("id") String id) {
        Subject entity = subjectService.findById(id);
        entity.getOperationLogs().addAll(operationLogClient.findAllByEntityKey(subjectService.getCacheKeyPrefix() + id).getData());
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询科目", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Subject>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, subjectService.query(definition));
    }
}