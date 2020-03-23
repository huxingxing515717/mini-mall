/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: ContractController
 * 日期: 2020/3/16 20:02
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.controller.AbstractController;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.invest.client.ContractApi;
import com.autumn.mall.invest.model.*;
import com.autumn.mall.invest.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ContractController extends AbstractController<Contract> implements ContractApi {

    @Autowired
    private ContractService contractService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BizTypeService bizTypeService;

    @Override
    public CrudService<Contract> getCrudService() {
        return contractService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(BizState.ineffect.name(), BizState.effect.name());
    }

    @PutMapping("/{uuid}")
    @ApiOperation(value = "生效合同", httpMethod = "PUT")
    @ApiImplicitParam(name = "uuid", value = "合同uuid", required = true, dataType = "String", paramType = "path")
    public ResponseResult doEffect(@PathVariable("uuid") String uuid) {
        contractService.doEffect(uuid);
        return new ResponseResult(CommonsResultCode.SUCCESS);
    }

    @Override
    protected List<String> getDefaultParts() {
        return Arrays.asList("floor", "position", "brand", "bizType", "subject");
    }

    @Override
    public void fetchParts(List<Contract> contracts, List<String> fetchParts) {
        if (contracts.isEmpty() || fetchParts.isEmpty()) {
            return;
        }
        Set<String> storeUuids = new HashSet<>();
        Set<String> tenantUuids = new HashSet<>();
        Set<String> floorUuids = new HashSet<>();
        Set<String> positionUuids = new HashSet<>();
        Set<String> brandUuids = new HashSet<>();
        Set<String> bizTypeUuids = new HashSet<>();
        Set<String> subjectUuids = new HashSet<>();
        contracts.stream().forEach(contract -> {
            storeUuids.add(contract.getStoreUuid());
            tenantUuids.add(contract.getTenantUuid());
            floorUuids.add(contract.getFloorUuid());
            positionUuids.add(contract.getPositionUuid());
            brandUuids.add(contract.getBrandUuid());
            bizTypeUuids.add(contract.getBiztypeUuid());
            subjectUuids.add(contract.getSubjectUuid());
        });
        // 项目
        Map<String, Store> storeMap = fetchParts.contains("store") ? storeService.findAllByIds(storeUuids) : new HashMap<>();
        // 商户
        Map<String, Tenant> tenantMap = fetchParts.contains("tenant") ? tenantService.findAllByIds(tenantUuids) : new HashMap<>();
        // 楼层
        Map<String, Floor> floorMap = fetchParts.contains("floor") ? floorService.findAllByIds(floorUuids) : new HashMap<>();
        // 铺位
        Map<String, Position> positionMap = fetchParts.contains("position") ? positionService.findAllByIds(positionUuids) : new HashMap<>();
        // 品牌
        Map<String, Brand> brandMap = fetchParts.contains("brand") ? brandService.findAllByIds(brandUuids) : new HashMap<>();
        // 业态
        Map<String, BizType> bizTypeMap = fetchParts.contains("bizType") ? bizTypeService.findAllByIds(bizTypeUuids) : new HashMap<>();
        contracts.stream().forEach(contract -> {
            contract.setStore(storeMap.get(contract.getStoreUuid()));
            contract.setTenant(tenantMap.get(contract.getTenantUuid()));
            contract.setFloor(floorMap.get(contract.getFloorUuid()));
            contract.setPosition(positionMap.get(contract.getPositionUuid()));
            contract.setBrand(brandMap.get(contract.getBrandUuid()));
            contract.setBizType(bizTypeMap.get(contract.getBiztypeUuid()));
        });
    }
}