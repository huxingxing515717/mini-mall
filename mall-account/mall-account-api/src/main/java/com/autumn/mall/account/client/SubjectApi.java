/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.client
 * 文件名: SubjectApi
 * 日期: 2020/3/15 19:32
 * 说明:
 */
package com.autumn.mall.account.client;

import com.autumn.mall.account.model.Subject;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface SubjectApi {

    /**
     * 根据uuid获取实体对象
     *
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    ResponseResult<Subject> findById(@PathVariable("uuid") String uuid);

    /**
     * 通过uuid集合批量查询
     *
     * @param uuids
     * @return
     */
    @PostMapping("/all")
    ResponseResult<Map<String, Subject>> findAllByIds(@RequestBody Set<String> uuids);

    /**
     * 根据查询定义查询
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<Subject>> query(@RequestBody QueryDefinition definition);
}