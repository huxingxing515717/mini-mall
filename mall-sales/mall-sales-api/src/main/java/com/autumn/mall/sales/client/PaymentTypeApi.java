/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.client
 * 文件名: PaymentTypeApi
 * 日期: 2020/3/15 16:38
 * 说明:
 */
package com.autumn.mall.sales.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.sales.model.PaymentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface PaymentTypeApi {

    /**
     * 根据id获取实体对象
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    ResponseResult<PaymentType> findById(@PathVariable("id") String id);

    /**
     * 根据查询定义查询
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<QueryResult<PaymentType>> query(@RequestBody QueryDefinition definition);
}