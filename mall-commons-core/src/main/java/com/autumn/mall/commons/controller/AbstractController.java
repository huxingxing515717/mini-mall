/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.controller
 * 文件名: AbstractController
 * 日期: 2020/3/22 20:16
 * 说明:
 */
package com.autumn.mall.commons.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.commons.client.ClientApi;
import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.commons.service.CrudService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 所有控制器的抽象实现基类
 *
 * @author Anbang713
 * @create 2020/3/22
 */
public abstract class AbstractController<T extends IsEntity> implements ClientApi<T> {

    @PostMapping
    @ApiOperation(value = "新增或编辑实体", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "实体类", required = true)
    public ResponseResult<String> save(@Valid @RequestBody T entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, getCrudService().save(entity));
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "删除实体", httpMethod = "DELETE")
    @ApiImplicitParam(name = "uuid", value = "唯一标识uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult deleteById(@PathVariable("uuid") String uuid) {
        getCrudService().deleteById(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fetchPropertyInfo", value = "是否获取实体相关联的其它实体完整信息，默认为true", required = true, dataType = "Boolean", paramType = "query")
    })
    public ResponseResult<T> findById(@PathVariable("uuid") String uuid, @RequestParam(value = "fetchPropertyInfo", defaultValue = "true") boolean fetchPropertyInfo) {
        T entity = getCrudService().findById(uuid);
        if (fetchPropertyInfo) {
            fetchParts(Arrays.asList(entity), getDefaultParts());
        }
        doAfterLoad(entity);
        return new ResponseResult(CommonsResultCode.SUCCESS, entity);
    }

    @Override
    @PostMapping("/ids")
    @ApiOperation(value = "批量查询指定唯一标识集合的实体，并将结果转换成Map结构，其中key为uuid，value为实体对象。", httpMethod = "POST")
    @ApiImplicitParam(name = "uuids", value = "唯一标识uuid的集合", required = true, dataType = "List", paramType = "body")
    public ResponseResult<Map<String, T>> findAllByIds(@RequestBody Set<String> uuids) {
        if (CollectionUtil.isEmpty(uuids)) {
            return new ResponseResult(CommonsResultCode.SUCCESS);
        }
        return new ResponseResult(CommonsResultCode.SUCCESS, getCrudService().findAllByIds(uuids));
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询数据", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<T>> query(@RequestBody QueryDefinition definition) {
        QueryResult<T> queryResult = getCrudService().query(definition);
        fetchParts(queryResult.getRecords(), definition.getFetchParts());
        SummaryQueryResult summaryQueryResult = SummaryQueryResult.newInstance(queryResult);
        summaryQueryResult.getSummary().putAll(querySummary(definition));
        return new ResponseResult(CommonsResultCode.SUCCESS, summaryQueryResult);
    }

    protected void doAfterLoad(T entity) {
        // do nothing
    }

    protected List<String> getDefaultParts() {
        return Arrays.asList();
    }

    /**
     * 提供空方法，子类似具体情况进行实现
     *
     * @param entities
     * @param fetchParts
     */
    protected void fetchParts(List<T> entities, List<String> fetchParts) {
        // do nothing
    }

    protected Map<String, Object> querySummary(QueryDefinition definition) {
        Map<String, Object> result = new HashMap<>();
        if (definition.isQuerySummary() == false || getSummaryFields().isEmpty()) {
            return result;
        }
        List<String> summaryFields = getSummaryFields();
        definition.setPageSize(1);
        definition.getFilter().put("state", null);
        result.put("all", getCrudService().query(definition).getTotal());
        for (String field : summaryFields) {
            definition.getFilter().put("state", field);
            result.put(field, getCrudService().query(definition).getTotal());
        }
        return result;
    }

    /**
     * 汇总的字段集合
     *
     * @return
     */
    public List<String> getSummaryFields() {
        return Arrays.asList();
    }

    public abstract CrudService<T> getCrudService();
}