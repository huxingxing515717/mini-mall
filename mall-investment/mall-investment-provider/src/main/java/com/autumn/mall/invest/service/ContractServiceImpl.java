/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: ContractServiceImpl
 * 日期: 2020/3/16 19:58
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.commons.utils.RedisUtils;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.repository.ContractRepository;
import com.autumn.mall.invest.response.InvestResultCode;
import com.autumn.mall.invest.specification.ContractSpecificationBuilder;
import com.autumn.mall.invest.utils.SettleDetailCalculator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RedisUtils redisUtils;

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
    public Map<String, String> doEffect(List<String> ids) {
        Map<String, String> errorMap = new HashMap<>();
        Iterable<Contract> contracts = contractRepository.findAllById(ids);
        Iterator<Contract> iterator = contracts.iterator();

        while (iterator.hasNext()) {
            Contract contract = iterator.next();
            if (BizState.effect.equals(contract.getBizState())) {
                errorMap.put(contract.getId(), "合同已经生效，禁止重复操作！");
                continue;
            }
            try {
                // 生效合同
                contract.setBizState(BizState.effect);
                contractRepository.save(contract);
                // 生成结算明细
                settleDetailService.saveAll(contract.getId(), SettleDetailCalculator.calculate(contract));
            } catch (Exception e) {
                log.error("合同:" + contract.getId() + "生效过程报错：{}", e);
                errorMap.put(contract.getId(), e.getMessage());
            }
        }

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