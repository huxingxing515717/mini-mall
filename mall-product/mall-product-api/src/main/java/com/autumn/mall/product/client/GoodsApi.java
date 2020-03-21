/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.client
 * 文件名: GoodsApi
 * 日期: 2020/3/15 20:24
 * 说明:
 */
package com.autumn.mall.product.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.product.model.Goods;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface GoodsApi {

    /**
     * 根据uuid获取实体对象
     *
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    ResponseResult<Goods> findById(@PathVariable("uuid") String uuid);

    /**
     * 根据查询定义查询
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<Goods>> query(@RequestBody QueryDefinition definition);
}