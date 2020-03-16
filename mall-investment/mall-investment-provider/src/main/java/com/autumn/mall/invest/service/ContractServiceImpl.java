/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: ContractServiceImpl
 * 日期: 2020/3/16 19:58
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.repository.ContractRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.ContractSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Service
public class ContractServiceImpl extends AbstractServiceImpl<Contract> implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Contract entity) {
        super.doBeforeSave(entity);
        validBeforeSave(entity);
        // 新建时，生成合同号
        if (StringUtils.isBlank(entity.getId())) {
            entity.setSerialNumber(new IdWorker().nextId());
        }
    }

    @Override
    public Map<String, String> doEffect(List<String> contractIds) {
        Map<String, String> errorMap = new HashMap<>();

        return errorMap;
    }

    private void validBeforeSave(Contract entity) {
        // 一些基础的业务逻辑判断就不写了

        // 同一铺位，合同期不允许交叉
        List<Contract> contracts = contractRepository.findAllByPositionId(entity.getPositionId());
        DateRange contractRange = new DateRange(entity.getBeginDate(), entity.getEndDate());
        for (Contract contract : contracts) {
            DateRange tempRange = new DateRange(contract.getBeginDate(), contract.getEndDate());
            if (tempRange.overlapExists(contractRange)) {
                MallExceptionCast.cast(InvestResultCode.POSITION_IS_REPEAT);
            }
        }
    }

    @Override
    public ContractRepository getRepository() {
        return contractRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:invest:contract:";
    }
}