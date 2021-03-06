/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.service
 * 文件名: SalesInputServiceImpl
 * 日期: 2020/3/22 15:25
 * 说明:
 */
package com.autumn.mall.sales.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.autumn.mall.basis.client.StockClient;
import com.autumn.mall.basis.model.Stock;
import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.CustomResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.commons.utils.RedisUtils;
import com.autumn.mall.sales.model.SalesInput;
import com.autumn.mall.sales.model.SalesInputDetail;
import com.autumn.mall.sales.repository.SalesInputDetailRepository;
import com.autumn.mall.sales.repository.SalesInputRepository;
import com.autumn.mall.sales.response.SalesResultCode;
import com.autumn.mall.sales.service.SalesInputService;
import com.autumn.mall.sales.specification.SalesInputSpecificationBuilder;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 销售数据录入单业务层接口
 *
 * @author Anbang713
 * @create 2020/3/22
 */
@Slf4j
@Service
public class SalesInputServiceImpl extends AbstractServiceImpl<SalesInput> implements SalesInputService {

    @Autowired
    private SalesInputRepository salesInputRepository;
    @Autowired
    private SalesInputDetailRepository salesInputDetailRepository;
    @Autowired
    private SalesInputSpecificationBuilder specificationBuilder;
    @Autowired
    private StockClient stockClient;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<SalesInputDetail> findDetailsByUuid(String uuid) {
        return salesInputDetailRepository.findAllByInputUuidOrderByLineNumber(uuid);
    }

    @Override
    public BigDecimal getTotalByContract(String contractUuid, DateRange dateRange) {
        if (StringUtils.isBlank(contractUuid) || dateRange == null) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        BigDecimal result = BigDecimal.ZERO;
        if (dateRange.getBeginDate() != null && dateRange.getEndDate() == null) {
            result = salesInputDetailRepository.summaryTotalByBeginDate(contractUuid, dateRange.getBeginDate());
        } else if (dateRange.getBeginDate() == null && dateRange.getEndDate() != null) {
            result = salesInputDetailRepository.summaryTotalByEndDate(contractUuid, dateRange.getEndDate());
        } else if (dateRange.getBeginDate() != null && dateRange.getEndDate() != null) {
            result = salesInputDetailRepository.summaryTotalByDateRange(contractUuid, dateRange.getBeginDate(), dateRange.getEndDate());
        }
        if (result == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }

    @Override
    @Transactional
    @GlobalTransactional
    public void doEffect(String uuid) {
        try {
            while (redisUtils.tryLock(getLockKeyPrefix() + uuid) == false) {
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            MallExceptionCast.cast(CommonsResultCode.TRY_LOCKED_ERROR);
        }
        Optional<SalesInput> optional = salesInputRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        if (optional.get().getState().equals(BizState.effect)) {
            MallExceptionCast.cast(SalesResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        // 生效录入单
        SalesInput entity = optional.get();
        entity.setState(BizState.effect);
        getRepository().save(entity);
        // 商品出库
        List<SalesInputDetail> details = salesInputDetailRepository.findAllByInputUuidOrderByLineNumber(entity.getUuid());
        List<Stock> stocks = new ArrayList<>();
        details.stream().forEach(detail -> {
            Stock stock = new Stock();
            stock.setEntityKey(MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS + detail.getGoodsUuid());
            stock.setWarehouse(detail.getWarehouse());
            stock.setQuantity(detail.getQuantity());
            stocks.add(stock);
        });
        ResponseResult responseResult = stockClient.outbound(stocks);
        // 删除分布式锁
        redisUtils.remove(getLockKeyPrefix() + uuid);
        // 如果出库失败，需要事务回滚。
        if (responseResult.isSuccess() == false) {
            log.info("出库失败，分布式事务回滚。");
            MallExceptionCast.cast(new CustomResultCode(responseResult.isSuccess(), responseResult.getCode(), responseResult.getMessage()));
        }
        saveOperationLog(uuid, "生效");
        // 更新缓存
        redisUtils.set(getModuleKeyPrefix() + entity.getUuid(), entity, RandomUtil.randomLong(3600, 86400));
    }

    @Override
    protected void doBeforeSave(SalesInput entity) {
        super.doBeforeSave(entity);
        // 把商品为空的明细删除
        Iterator<SalesInputDetail> iterator = entity.getDetails().iterator();
        int lineNumber = 1;
        List<String> goodsUuids = new ArrayList<>();
        while (iterator.hasNext()) {
            SalesInputDetail detail = iterator.next();
            if (StringUtils.isBlank(detail.getGoodsUuid())) {
                iterator.remove();
            } else {
                detail.setLineNumber(lineNumber++);
                goodsUuids.add(detail.getGoodsUuid());
            }
        }
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            MallExceptionCast.cast(SalesResultCode.GOODS_DETAILS_IS_EMPTY);
        }
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setBillNumber(new IdWorker().nextId());
        }
        entity.setGoodsUuids(CollectionUtil.join(goodsUuids, ","));
    }

    @Override
    protected void doAfterSave(SalesInput entity) {
        IdWorker idWorker = new IdWorker();
        entity.getDetails().stream().forEach(detail -> {
            if (StringUtils.isBlank(detail.getUuid())) {
                detail.setUuid(idWorker.nextId());
                detail.setInputUuid(entity.getUuid());
            }
        });
        salesInputDetailRepository.deleteByInputUuid(entity.getUuid());
        salesInputDetailRepository.saveAll(entity.getDetails());
        super.doAfterSave(entity);
    }

    @Override
    public void doAfterDeleted(String uuid) {
        salesInputDetailRepository.deleteByInputUuid(uuid);
        super.doAfterDeleted(uuid);
    }

    @Override
    public BaseRepository<SalesInput> getRepository() {
        return salesInputRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getModuleKeyPrefix() {
        return MallModuleKeyPrefixes.SALES_KEY_PREFIX_OF_SALES_INPUT;
    }
}