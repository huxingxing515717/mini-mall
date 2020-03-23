/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: SettleDetailController
 * 日期: 2020/3/16 21:19
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.SettleDetailApi;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.service.ContractService;
import com.autumn.mall.invest.service.SettleDetailService;
import com.autumn.mall.invest.service.StoreService;
import com.autumn.mall.invest.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
@Api(value = "结算明细管理")
@RestController
@RequestMapping("/settledetail")
public class SettleDetailController implements SettleDetailApi {

    @Autowired
    private SettleDetailService settleDetailService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private ContractService contractService;

    @Override
    @GetMapping("/{contractUuid}")
    @ApiOperation(value = "根据合同uuid查询结算明细", httpMethod = "GET")
    @ApiImplicitParam(name = "contractUuid", value = "合同uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<List<SettleDetail>> findAllByContractUuid(@PathVariable("contractUuid") String contractUuid) {
        return new ResponseResult(CommonsResultCode.SUCCESS, settleDetailService.findAllByContractUuid(contractUuid));
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询数据", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<SettleDetail>> query(@RequestBody QueryDefinition definition) {
        QueryResult<SettleDetail> queryResult = settleDetailService.query(definition);
        fetchParts(queryResult.getRecords(), definition.getFetchParts());
        SummaryQueryResult summaryQueryResult = SummaryQueryResult.newInstance(queryResult);
        summaryQueryResult.getSummary().putAll(querySummary(definition));
        return new ResponseResult(CommonsResultCode.SUCCESS, summaryQueryResult);
    }

    private void fetchParts(List<SettleDetail> records, List<String> fetchParts) {
        if (records.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        Set<String> storeUuids = new HashSet<>();
        Set<String> tenantUuids = new HashSet<>();
        Set<String> contractUuids = new HashSet<>();
        records.stream().forEach(record -> {
            storeUuids.add(record.getStoreUuid());
            tenantUuids.add(record.getTenantUuid());
            contractUuids.add(record.getContractUuid());
        });
        // 项目
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeService.findAllByIds(storeUuids) : new HashMap<>();
        // 商户
        Map<String, Tenant> tenantMap = fetchParts.contains("tenant") ? tenantService.findAllByIds(tenantUuids) : new HashMap<>();
        // 合同
        Map<String, Contract> contractMap = fetchParts.contains("contract") ? contractService.findAllByIds(contractUuids) : new HashMap<>();
        records.stream().forEach(record -> {
            record.setStore(storeMap.get(record.getStoreUuid()));
            record.setTenant(tenantMap.get(record.getTenantUuid()));
            record.setContract(contractMap.get(record.getContractUuid()));
        });

    }

    protected Map<String, Object> querySummary(QueryDefinition definition) {
        Map<String, Object> result = new HashMap<>();
        if (definition.isQuerySummary() == false) {
            return result;
        }
        List<String> summaryFields = Arrays.asList("true", "false");
        definition.setPageSize(1);
        definition.getFilter().put("noStatement", null);
        result.put("all", settleDetailService.query(definition).getTotal());
        for (String field : summaryFields) {
            definition.getFilter().put("noStatement", field);
            result.put(field, settleDetailService.query(definition).getTotal());
        }
        return result;
    }
}