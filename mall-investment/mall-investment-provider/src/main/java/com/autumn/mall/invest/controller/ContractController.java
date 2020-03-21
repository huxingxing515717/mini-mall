/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: ContractController
 * 日期: 2020/3/16 20:02
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.account.client.SubjectClient;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.client.ContractApi;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.model.Tenant;
import com.autumn.mall.invest.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 合同控制器
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Api(value = "合同管理")
@RestController
@RequestMapping("/contract")
public class ContractController implements ContractApi {

    @Autowired
    private ContractService contractService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BizTypeService bizTypeService;
    @Autowired
    private SubjectClient subjectClient;

    @PostMapping
    @ApiOperation(value = "新增或编辑合同", httpMethod = "POST")
    @ApiImplicitParam(name = "entity", value = "合同实体类", required = true, dataType = "Contract")
    public ResponseResult<String> save(@Valid @RequestBody Contract entity) {
        return new ResponseResult(CommonsResultCode.SUCCESS, contractService.save(entity));
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "删除合同", httpMethod = "DELETE")
    @ApiImplicitParam(name = "uuid", value = "合同uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Contract> deleteById(@PathVariable("uuid") String uuid) {
        contractService.deleteById(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "合同uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Contract> findById(@PathVariable("uuid") String uuid) {
        Contract contract = contractService.findById(uuid);
        obtainContractInfo(contract);
        return new ResponseResult(CommonsResultCode.SUCCESS, contract);
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "生效合同", httpMethod = "PUT")
    @ApiImplicitParam(name = "uuid", value = "合同uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult doEffect(@PathVariable("uuid") String uuid) {
        contractService.doEffect(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询合同", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<SummaryQueryResult<Contract>> query(@RequestBody QueryDefinition definition) {
        QueryResult<Contract> queryResult = contractService.query(definition);
        fetchParts(queryResult.getRecords(), definition.getFetchParts());
        SummaryQueryResult summaryQueryResult = SummaryQueryResult.newInstance(queryResult);
        summaryQueryResult.getSummary().putAll(querySummary(definition));
        return new ResponseResult(CommonsResultCode.SUCCESS, summaryQueryResult);
    }

    private void obtainContractInfo(Contract contract) {
        contract.setStore(storeService.findById(contract.getStoreUuid()));
        contract.setBuilding(buildingService.findById(contract.getBuildingUuid()));
        contract.setFloor(floorService.findById(contract.getFloorUuid()));
        contract.setTenant(tenantService.findById(contract.getTenantUuid()));
        contract.setPosition(positionService.findById(contract.getPositionUuid()));
        contract.setBrand(brandService.findById(contract.getBrandUuid()));
        contract.setBizType(bizTypeService.findById(contract.getBiztypeUuid()));
        contract.setSubject(subjectClient.findById(contract.getSubjectUuid()).getData());
    }

    private void fetchParts(List<Contract> contracts, List<String> fetchParts) {
        if (contracts.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        Set<String> storeUuids = new HashSet<>();
        Set<String> tenantUuids = new HashSet<>();
        contracts.stream().forEach(contract -> {
            storeUuids.add(contract.getStoreUuid());
            tenantUuids.add(contract.getTenantUuid());
        });
        // 项目
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeService.findAllByIds(storeUuids) : new HashMap<>();
        // 商户
        Map<String, Tenant> tenantMap = fetchParts.contains("tenant") ? tenantService.findAllByIds(tenantUuids) : new HashMap<>();
        contracts.stream().forEach(contract -> {
            contract.setStore(storeMap.get(contract.getStoreUuid()));
            contract.setTenant(tenantMap.get(contract.getTenantUuid()));
        });
    }

    private Map<String, Object> querySummary(QueryDefinition definition) {
        Map<String, Object> result = new HashMap<>();
        if (definition.isQuerySummary() == false) {
            return result;
        }
        definition.setPageSize(1);
        definition.getFilter().put("state", null);
        result.put("all", contractService.query(definition).getTotal());
        definition.getFilter().put("state", BizState.ineffect.name());
        result.put(BizState.ineffect.name(), contractService.query(definition).getTotal());
        definition.getFilter().put("state", BizState.effect.name());
        result.put(BizState.effect.name(), contractService.query(definition).getTotal());
        return result;
    }
}