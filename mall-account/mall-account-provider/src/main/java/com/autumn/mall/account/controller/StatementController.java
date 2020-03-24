/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.controller
 * 文件名: StatementController
 * 日期: 2020/3/23 21:29
 * 说明:
 */
package com.autumn.mall.account.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.account.client.StatementApi;
import com.autumn.mall.account.model.Statement;
import com.autumn.mall.account.service.StatementService;
import com.autumn.mall.commons.controller.AbstractController;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.invest.client.ContractClient;
import com.autumn.mall.invest.client.StoreClient;
import com.autumn.mall.invest.client.TenantClient;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.model.Tenant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 账单
 *
 * @author Anbang713
 * @create 2020/3/23
 */
@RestController
@RequestMapping("/statement")
public class StatementController extends AbstractController<Statement> implements StatementApi {

    @Autowired
    private StatementService statementService;
    @Autowired
    private StoreClient storeClient;
    @Autowired
    private TenantClient tenantClient;
    @Autowired
    private ContractClient contractClient;

    @Override
    protected void doAfterLoad(Statement entity) {
        super.doAfterLoad(entity);
        // 如果details不为空，那就是从redis缓存中取出来的，我们不处理。
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            entity.setDetails(statementService.findDetailsByUuid(entity.getUuid()));
        }
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "生效账单", httpMethod = "PUT")
    @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult doEffect(@PathVariable("uuid") String uuid) {
        statementService.doEffect(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @PostMapping("/settle")
    @ApiOperation(value = "出账", httpMethod = "POST")
    @ApiImplicitParam(name = "settleDetails", value = "实体类", required = true)
    public ResponseResult<Map<String, String>> doSettle(@Valid @RequestBody List<SettleDetail> settleDetails) {
        return new ResponseResult(CommonsResultCode.SUCCESS, statementService.settle(settleDetails));
    }

    @Override
    public CrudService<Statement> getCrudService() {
        return statementService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(BizState.ineffect.name(), BizState.effect.name());
    }

    public void fetchParts(List<Statement> records, List<String> fetchParts) {
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
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeClient.findAllByIds(storeUuids).getData() : new HashMap<>();
        // 商户
        Map<String, Tenant> tenantMap = fetchParts.contains("tenant") ? tenantClient.findAllByIds(tenantUuids).getData() : new HashMap<>();
        // 合同
        Map<String, Contract> contractMap = fetchParts.contains("contract") ? contractClient.findAllByIds(contractUuids).getData() : new HashMap<>();
        records.stream().forEach(record -> {
            record.setStore(storeMap.get(record.getStoreUuid()));
            record.setTenant(tenantMap.get(record.getTenantUuid()));
            record.setContract(contractMap.get(record.getContractUuid()));
        });
    }
}