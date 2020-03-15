/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: TenantApi
 * 日期: 2020/3/15 16:02
 * 说明:
 */
package com.autumn.mall.invest.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.model.Tenant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 商户客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
public interface TenantApi {

    /**
     * 根据id获取实体对象
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    ResponseResult<Tenant> findById(@PathVariable("id") String id);

    /**
     * 根据查询定义查询
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<QueryResult<Tenant>> query(@RequestBody QueryDefinition definition);
}