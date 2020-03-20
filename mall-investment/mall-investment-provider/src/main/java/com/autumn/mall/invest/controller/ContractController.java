/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: ContractController
 * 日期: 2020/3/16 20:02
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.account.client.SubjectClient;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.ContractApi;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Override
    @GetMapping("/{uuid}")
    @ApiOperation(value = "根据uuid获取实体对象", httpMethod = "GET")
    @ApiImplicitParam(name = "uuid", value = "合同id", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Contract> findById(@PathVariable("uuid") String uuid) {
        Contract contract = contractService.findById(uuid);
        obtainContractInfo(contract);
        return new ResponseResult(CommonsResultCode.SUCCESS, contract);
    }

    @Override
    @PostMapping("/query")
    @ApiOperation(value = "根据查询定义查询合同", httpMethod = "POST")
    @ApiImplicitParam(name = "definition", value = "查询定义", required = true, dataType = "QueryDefinition")
    public ResponseResult<QueryResult<Contract>> query(@RequestBody QueryDefinition definition) {
        return new ResponseResult(CommonsResultCode.SUCCESS, contractService.query(definition));
    }

    private void obtainContractInfo(Contract contract) {
        contract.setStore(storeService.findById(contract.getStoreId()));
        contract.setBuilding(buildingService.findById(contract.getBuildingId()));
        contract.setFloor(floorService.findById(contract.getFloorId()));
        contract.setTenant(tenantService.findById(contract.getTenantId()));
        contract.setPosition(positionService.findById(contract.getPositionId()));
        contract.setBrand(brandService.findById(contract.getBrandId()));
        contract.setBizType(bizTypeService.findById(contract.getBiztypeId()));
        contract.setSubject(subjectClient.findById(contract.getSubjectId()).getData());
    }
}