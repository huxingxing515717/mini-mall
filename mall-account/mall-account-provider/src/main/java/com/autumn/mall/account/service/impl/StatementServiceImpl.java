/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.service
 * 文件名: StatementServiceImpl
 * 日期: 2020/3/23 21:24
 * 说明:
 */
package com.autumn.mall.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.autumn.mall.account.commons.PayState;
import com.autumn.mall.account.model.Statement;
import com.autumn.mall.account.model.StatementDetail;
import com.autumn.mall.account.order.StatementOrderBuilder;
import com.autumn.mall.account.repository.StatementDetailRepository;
import com.autumn.mall.account.repository.StatementRepository;
import com.autumn.mall.account.response.AccountResultCode;
import com.autumn.mall.account.service.StatementService;
import com.autumn.mall.account.specification.StatementSpecificationBuilder;
import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.OrderBuilder;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.commons.utils.RedisUtils;
import com.autumn.mall.invest.client.SettleDetailClient;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.sales.client.SalesInputClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 账单业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/23
 */
@Service
public class StatementServiceImpl extends AbstractServiceImpl<Statement> implements StatementService {

    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private StatementDetailRepository statementDetailRepository;
    @Autowired
    private StatementSpecificationBuilder specificationBuilder;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SettleDetailClient settleDetailClient;
    @Autowired
    private SalesInputClient salesInputClient;

    @Override
    public List<StatementDetail> findDetailsByUuid(String uuid) {
        return statementDetailRepository.findAllByStatementUuidOrderByLineNumber(uuid);
    }

    @Override
    protected void doBeforeSave(Statement entity) {
        super.doBeforeSave(entity);
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setBillNumber(new IdWorker().nextId());
            entity.setState(BizState.ineffect);
            entity.setPayState(PayState.unPay);
        }
    }

    @Override
    protected void doAfterSave(Statement entity) {
        IdWorker idWorker = new IdWorker();
        entity.getDetails().stream().forEach(detail -> {
            if (StringUtils.isBlank(detail.getUuid())) {
                detail.setUuid(idWorker.nextId());
                detail.setStatementUuid(entity.getUuid());
            }
        });
        statementDetailRepository.deleteByStatementUuid(entity.getUuid());
        statementDetailRepository.saveAll(entity.getDetails());
        super.doAfterSave(entity);
    }

    @Override
    @Transactional
    @GlobalTransactional
    public void doAfterDeleted(String uuid) {
        statementDetailRepository.deleteByStatementUuid(uuid);
        settleDetailClient.writeBackWhenStatementDeleted(uuid);
        super.doAfterDeleted(uuid);
    }

    @Override
    public void doEffect(String uuid) {
        Optional<Statement> optional = statementRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        if (optional.get().getState().equals(BizState.effect)) {
            MallExceptionCast.cast(AccountResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        Statement entity = optional.get();
        entity.setState(BizState.effect);
        getRepository().save(entity);
        saveOperationLog(uuid, "生效");
        // 更新缓存
        redisUtils.set(getModuleKeyPrefix() + entity.getUuid(), entity, RandomUtil.randomLong(3600, 86400));
    }

    @Override
    public void doPay(String uuid) {

    }

    @Override
    @Transactional
    @GlobalTransactional
    public Map<String, String> settle(List<SettleDetail> settleDetails) {
        Map<String, String> resultMap = new HashMap<>();
        if (CollectionUtil.isEmpty(settleDetails)) {
            return resultMap;
        }
        // 先按项目+商户+合同进行分组
        Map<String, List<SettleDetail>> detailMap = settleDetails.stream().collect(
                Collectors.groupingBy(settleDetail ->
                                settleDetail.getStoreUuid() + "_" + settleDetail.getTenantUuid() + "_" + settleDetail.getContractUuid()
                        , Collectors.toList()));
        // 对结算明细按起始日期升序
        Iterator<String> iterator = detailMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            // 对键进行分割
            String[] keys = StringUtils.split(key, "_");

            String contractUuid = keys[2];
            // 对合同进行锁定
            try {
                while (redisUtils.tryLock(MallModuleKeyPrefixes.INVEST_KEY_PREFIX_OF_CONTRACT + RedisUtils.LOCK_TAG + contractUuid, "出账锁定……")) {
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (Exception e) {
                resultMap.put(key, e.getMessage());
                continue;
            }
            List<SettleDetail> details = detailMap.get(key).stream().sorted((o1, o2) ->
                    DateUtils.truncatedCompareTo(o1.getBeginDate(), o2.getBeginDate(), Calendar.DAY_OF_MONTH)).collect(Collectors.toList());
            // 只有当前最小的日期前面的结算周期都已经出过账才允许出账
            if (settleDetailClient.existsNoStatement(contractUuid, details.get(0).getBeginDate()).getData()) {
                resultMap.put(key, "请先对历史结算明细进行出账！");
                continue;
            }
            // 将结算明细转换成账单
            Statement statement = generateStatement(details);
            save(statement);
            sendSuccessfulSettleMsg(statement, details);
            resultMap.put(key, "成功出账，账单单号：" + statement.getBillNumber());
            // 释放锁
            redisUtils.remove(MallModuleKeyPrefixes.INVEST_KEY_PREFIX_OF_CONTRACT + RedisUtils.LOCK_TAG + contractUuid);
        }
        return resultMap;
    }

    private void sendSuccessfulSettleMsg(Statement statement, List<SettleDetail> details) {
        List<String> detailUuids = new ArrayList<>();
        details.stream().forEach(detail -> detailUuids.add(detail.getUuid()));
        settleDetailClient.writeBackWhenSettleSuccessful(statement.getUuid(), detailUuids);
    }

    private Statement generateStatement(List<SettleDetail> details) {
        Statement statement = new Statement();
        SettleDetail settleDetail = details.get(0);

        statement.setStoreUuid(settleDetail.getStoreUuid());
        statement.setTenantUuid(settleDetail.getTenantUuid());
        statement.setContractUuid(settleDetail.getContractUuid());
        statement.setAccountDate(new Date());
        statement.setSalesRate(settleDetail.getSalesRate());
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        int lineNumber = 1;
        for (SettleDetail detail : details) {
            total = total.add(detail.getTotal());
            tax = tax.add(detail.getTax());

            StatementDetail statementDetail = new StatementDetail();
            statementDetail.setLineNumber(lineNumber++);
            statementDetail.setBeginDate(detail.getBeginDate());
            statementDetail.setEndDate(detail.getEndDate());
            statementDetail.setTotal(detail.getTotal());
            statementDetail.setTax(detail.getTax());
            statementDetail.setSalesTotal(salesInputClient.getTotalByContract(statement.getContractUuid(),
                    new DateRange(statementDetail.getBeginDate(), statementDetail.getEndDate())).getData());
            statementDetail.setSalesTax(statementDetail.getSalesTotal().multiply(detail.getTaxRate()));
            statementDetail.setSubjectUuid(detail.getSubjectUuid());
            statementDetail.setTaxRate(detail.getTaxRate());
            statement.getDetails().add(statementDetail);
        }
        statement.setTotal(total);
        statement.setTax(tax);
        return statement;
    }

    @Override
    public BaseRepository<Statement> getRepository() {
        return statementRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public OrderBuilder getOrderBuilder() {
        return new StatementOrderBuilder();
    }

    @Override
    public String getModuleKeyPrefix() {
        return MallModuleKeyPrefixes.ACCOUNT_KEY_PREFIX_OF_STATEMENT;
    }
}