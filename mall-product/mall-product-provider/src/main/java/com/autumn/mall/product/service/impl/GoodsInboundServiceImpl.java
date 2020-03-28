/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.service
 * 文件名: GoodsInboundServiceImpl
 * 日期: 2020/3/17 8:10
 * 说明:
 */
package com.autumn.mall.product.service.impl;

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
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.commons.utils.RedisUtils;
import com.autumn.mall.product.model.GoodsInbound;
import com.autumn.mall.product.model.GoodsInboundDetail;
import com.autumn.mall.product.repository.GoodsInboundDetailRepository;
import com.autumn.mall.product.repository.GoodsInboundRepository;
import com.autumn.mall.product.response.ProductResultCode;
import com.autumn.mall.product.service.GoodsInboundService;
import com.autumn.mall.product.specification.GoodsInboundSpecificationBuilder;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 商品入库单业务层实现类
 *
 * @author Anbang713
 * @create 2020/3/17
 */
@Slf4j
@Service
public class GoodsInboundServiceImpl extends AbstractServiceImpl<GoodsInbound> implements GoodsInboundService {

    @Autowired
    private GoodsInboundRepository goodsInboundRepository;
    @Autowired
    private GoodsInboundDetailRepository goodsInboundDetailRepository;
    @Autowired
    private GoodsInboundSpecificationBuilder specificationBuilder;
    @Autowired
    private StockClient stockClient;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<GoodsInboundDetail> findDetailsByIdOrderByLineNumber(String uuid) {
        return goodsInboundDetailRepository.findAllByGoodsInboundUuidOrderByLineNumber(uuid);
    }

    @Override
    @Transactional
    @GlobalTransactional(rollbackFor = Exception.class)
    public void doEffect(String uuid) {
        // 获取分布式锁
        try {
            while (redisUtils.tryLock(getLockKeyPrefix() + uuid) == false) {
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            MallExceptionCast.cast(CommonsResultCode.TRY_LOCKED_ERROR);
        }
        Optional<GoodsInbound> optional = goodsInboundRepository.findById(uuid);
        if (optional.isPresent() == false) {
            MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
        }
        if (optional.get().getState().equals(BizState.effect)) {
            MallExceptionCast.cast(ProductResultCode.ENTITY_IS_EQUALS_TARGET_STATE);
        }
        // 生效入库单
        GoodsInbound entity = optional.get();
        entity.setState(BizState.effect);
        getRepository().save(entity);
        // 商品入库
        List<GoodsInboundDetail> details = goodsInboundDetailRepository.findAllByGoodsInboundUuidOrderByLineNumber(entity.getUuid());
        List<Stock> stocks = new ArrayList<>();
        details.stream().forEach(detail -> {
            Stock stock = new Stock();
            stock.setEntityKey(MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS + detail.getGoodsUuid());
            stock.setWarehouse(entity.getWarehouse());
            stock.setQuantity(detail.getQuantity());
            stocks.add(stock);
        });
        ResponseResult responseResult = stockClient.inbound(stocks);
        // 删除分布式锁
        redisUtils.remove(getLockKeyPrefix() + uuid);
        // 如果入库失败，需要事务回滚。
        if (responseResult.isSuccess() == false) {
            log.info("入库失败，分布式事务回滚。");
            MallExceptionCast.cast(new CustomResultCode(responseResult.isSuccess(), responseResult.getCode(), responseResult.getMessage()));
        }
        saveOperationLog(uuid, "生效");
        // 更新缓存，key的过期时间为1天
        redisUtils.set(getModuleKeyPrefix() + entity.getUuid(), entity, RandomUtil.randomLong(3600, 86400));
    }

    @Override
    protected void doBeforeSave(GoodsInbound entity) {
        super.doBeforeSave(entity);
        // 把商品为空的明细删除
        Iterator<GoodsInboundDetail> iterator = entity.getDetails().iterator();
        int lineNumber = 1;
        List<String> goodsUuids = new ArrayList<>();
        while (iterator.hasNext()) {
            GoodsInboundDetail detail = iterator.next();
            if (StringUtils.isBlank(detail.getGoodsUuid())) {
                iterator.remove();
            } else {
                detail.setLineNumber(lineNumber++);
                goodsUuids.add(detail.getGoodsUuid());
            }
        }
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            MallExceptionCast.cast(ProductResultCode.GOODS_DETAILS_IS_EMPTY);
        }
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setBillNumber(new IdWorker().nextId());
        }
        entity.setGoodsUuids(CollectionUtil.join(goodsUuids, ","));
    }

    @Override
    protected void doAfterSave(GoodsInbound entity) {
        IdWorker idWorker = new IdWorker();
        entity.getDetails().stream().forEach(detail -> {
            if (StringUtils.isBlank(detail.getUuid())) {
                detail.setUuid(idWorker.nextId());
                detail.setGoodsInboundUuid(entity.getUuid());
            }
        });
        goodsInboundDetailRepository.deleteByGoodsInboundUuid(entity.getUuid());
        goodsInboundDetailRepository.saveAll(entity.getDetails());
        super.doAfterSave(entity);
    }

    @Override
    public void doAfterDeleted(String uuid) {
        goodsInboundDetailRepository.deleteByGoodsInboundUuid(uuid);
        super.doAfterDeleted(uuid);
    }

    @Override
    public BaseRepository<GoodsInbound> getRepository() {
        return goodsInboundRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getModuleKeyPrefix() {
        return MallModuleKeyPrefixes.PRODUCT_KEY_PREFIX_OF_GOODS_INBOUND;
    }
}