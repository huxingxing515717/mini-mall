/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: ContractApi
 * 日期: 2020/3/16 19:50
 * 说明:
 */
package com.autumn.mall.invest.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.model.Contract;
import org.springframework.web.bind.annotation.*;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface ContractApi {

    /**
     * 根据id获取实体对象
     *
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    ResponseResult<Contract> findById(@PathVariable("uuid") String uuid, @RequestParam(value = "fetchPropertyInfo", required = false, defaultValue = "true") boolean fetchPropertyInfo);

    /**
     * 根据查询定义查询
     *
     * @param definition
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<Contract>> query(@RequestBody QueryDefinition definition);
}