/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.client
 * 文件名: StockApi
 * 日期: 2020/3/15 21:12
 * 说明:
 */
package com.autumn.mall.basis.client;

import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.commons.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface StockApi {

    /**
     * 通过实体标识查询库存信息
     *
     * @param entityKey
     * @return
     */
    @GetMapping("/{entityKey}")
    ResponseResult<StockInfo> findByEntityKey(@PathVariable("entityKey") String entityKey);

    /**
     * 通过实体标识和仓库查询库存信息
     *
     * @param entityKey
     * @param warehouse
     * @return
     */
    @GetMapping("/{entityKey}/warehouse")
    ResponseResult<StockInfo> findByEntityKeyAndWarehouse(@PathVariable("entityKey") String entityKey, @RequestParam(value = "warehouse") String warehouse);

    /**
     * 根据实体标识批量查询库存信息
     *
     * @param entityKeys
     * @return
     */
    @PostMapping("/stockInfos")
    ResponseResult<Map<String, StockInfo>> findByEntityKeys(@RequestBody List<String> entityKeys);
}