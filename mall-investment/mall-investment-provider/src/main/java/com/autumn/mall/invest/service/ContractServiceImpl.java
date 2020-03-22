/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: ContractServiceImpl
 * 日期: 2020/3/16 19:58
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.OrderBuilder;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.order.ContractOrderBuilder;
import com.autumn.mall.invest.repository.ContractRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.ContractSpecificationBuilder;
import com.autumn.mall.invest.utils.SettleDetailCalculator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 合同业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Slf4j
@Service
public class ContractServiceImpl extends AbstractServiceImpl<Contract> implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private SettleDetailService settleDetailService;
    @Autowired
    private ContractSpecificationBuilder specificationBuilder;

    @Override
    protected void doBeforeSave(Contract entity) {
        super.doBeforeSave(entity);
        validBeforeSave(entity);
        // 新建时，生成合同号
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setSerialNumber(new IdWorker().nextId());
        }
    }

    @Override
    public void deleteById(String uuid) {
        Optional<Contract> optional = getRepository().findById(uuid);
        if (optional.isPresent() && optional.get().getState().equals(BizState.effect)) {
            MallExceptionCast.cast(InvestResultCode.CONTRACT_IS_EFFECT);
        }
        super.deleteById(uuid);
    }

    @Override
    @Transactional
    public void doEffect(String uuid) {
        Optional<Contract> optional = contractRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        Contract contract = optional.get();
        if (BizState.effect.equals(contract.getState())) {
            MallExceptionCast.cast(InvestResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        // 生效合同
        contract.setState(BizState.effect);
        contractRepository.save(contract);
        // 生成结算明细
        settleDetailService.saveAll(contract.getUuid(), SettleDetailCalculator.calculate(contract));
        saveOperationLog(uuid, "生效");
        // 刷新缓存
        doAfterSave(contract);
    }

    private void validBeforeSave(Contract entity) {
        // 一些基础的业务逻辑判断就不写了

        // 同一铺位，合同期不允许交叉
        List<Contract> contracts = contractRepository.findAllByPositionUuid(entity.getPositionUuid());
        DateRange contractRange = new DateRange(entity.getBeginDate(), entity.getEndDate());
        for (Contract contract : contracts) {
            DateRange tempRange = new DateRange(contract.getBeginDate(), contract.getEndDate());
            if (tempRange.overlapExists(contractRange) && (StringUtils.isBlank(entity.getUuid()) || entity.getUuid().equals(contract.getUuid()) == false)) {
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
    public OrderBuilder getOrderBuilder() {
        return new ContractOrderBuilder();
    }

    @Override
    public String getCacheKeyPrefix() {
        return MallModuleKeyPrefixes.INVEST_KEY_PREFIX_OF_CONTRACT;
    }
}