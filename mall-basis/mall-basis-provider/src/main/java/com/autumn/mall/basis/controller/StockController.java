/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.controller
 * 文件名: StockController
 * 日期: 2020/3/15 21:35
 * 说明:
 */
package com.autumn.mall.basis.controller;

import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.client.StockApi;
import com.autumn.mall.basis.service.StockService;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "库存管理")
@RestController
@RequestMapping("/stock")
public class StockController implements StockApi {

    @Autowired
    private StockService stockService;

    @Override
    @GetMapping("/{entityKey}")
    @ApiOperation(value = "根据实体标识查询库存", httpMethod = "GET")
    @ApiImplicitParam(name = "entityKey", value = "实体标识", required = true, dataType = "String", paramType = "path")
    public ResponseResult<StockInfo> findByEntityKey(@PathVariable("entityKey") String entityKey) {
        return new ResponseResult(CommonsResultCode.SUCCESS, stockService.findByEntityKey(entityKey));
    }

    @Override
    @GetMapping("/{entityKey}/warehouse")
    @ApiOperation(value = "根据实体标识和仓库查询库存", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityKey", value = "实体标识", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "warehouse", value = "仓库", required = true, dataType = "String", paramType = "query")
    })
    public ResponseResult<StockInfo> findByEntityKeyAndWarehouse(@PathVariable("entityKey") String entityKey, @RequestParam(value = "warehouse") String warehouse) {
        return new ResponseResult(CommonsResultCode.SUCCESS, stockService.findByEntityKeyAndWarehouse(entityKey, warehouse));
    }

    @Override
    @PostMapping("/stockInfos")
    @ApiOperation(value = "根据实体标识批量查询库存", httpMethod = "POST")
    @ApiImplicitParam(name = "entityKeys", value = "实体标识集合", required = true, dataType = "List", paramType = "body")
    public ResponseResult<Map<String, StockInfo>> findByEntityKeys(@RequestBody List<String> entityKeys) {
        return new ResponseResult(CommonsResultCode.SUCCESS, stockService.findByEntityKeys(entityKeys));
    }
}