/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: StoreApi
 * 日期: 2020/3/14 17:13
 * 说明:
 */
package com.autumn.mall.invest.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.model.Store;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 项目客户端接口
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface StoreApi {

    /**
     * 根据uuid获取项目实体对象
     *
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    ResponseResult<Store> findById(@PathVariable("uuid") String uuid);

    /**
     * 根据查询定义查询项目
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<Store>> query(@RequestBody QueryDefinition definition);
}