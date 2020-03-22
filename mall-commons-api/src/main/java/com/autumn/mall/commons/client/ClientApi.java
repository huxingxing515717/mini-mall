/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.client
 * 文件名: ClientApi
 * 日期: 2020/3/22 20:06
 * 说明:
 */
package com.autumn.mall.commons.client;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 客户端api父接口
 *
 * @author Anbang713
 * @create 2020/3/22
 */
public interface ClientApi<T extends IsEntity> {

    /**
     * 根据uuid获取实体对象
     *
     * @param uuid              唯一标识
     * @param fetchPropertyInfo 是否获取实体相关联的其它实体完整信息，默认为true
     * @return
     */
    @GetMapping("/{uuid}")
    ResponseResult<T> findById(@PathVariable("uuid") String uuid, @RequestParam(value = "fetchPropertyInfo", defaultValue = "true") boolean fetchPropertyInfo);

    /**
     * 批量查询指定唯一标识集合的实体，并将结果转换成Map结构，其中key为uuid，value为实体对象。<p>
     * 注意：如果某个uuid的实体不存在，那么结果中不会包含该键
     *
     * @param uuids 唯一标识集合
     * @return
     */
    @PostMapping("/ids")
    ResponseResult<Map<String, T>> findAllByIds(@RequestBody Set<String> uuids);

    /**
     * 根据查询定义查询
     *
     * @param definition 查询定义
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<T>> query(@RequestBody QueryDefinition definition);
}