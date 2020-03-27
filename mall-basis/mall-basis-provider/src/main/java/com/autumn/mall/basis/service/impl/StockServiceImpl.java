/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: StockServiceImpl
 * 日期: 2020/3/15 21:32
 * 说明:
 */
package com.autumn.mall.basis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.api.WarehouseInfo;
import com.autumn.mall.basis.model.Stock;
import com.autumn.mall.basis.repository.StockRepository;
import com.autumn.mall.basis.service.StockService;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public boolean save(Stock entity) {
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setUuid(new IdWorker().nextId());
        }
        stockRepository.save(entity);
        return true;
    }

    @Override
    @Transactional
    public void inbound(List<Stock> stocks) {
        if (CollectionUtil.isEmpty(stocks)) {
            return;
        }
        for (Stock stock : stocks) {
            log.info("实体标识：" + stock.getEntityKey() + "，仓库：" + stock.getWarehouse() + "，增加库存：" + stock.getQuantity());
            Optional<Stock> optional = stockRepository.findByEntityKeyAndWarehouse(stock.getEntityKey(), stock.getWarehouse());
            if (optional.isPresent() == false) {
                save(stock);
            } else {
                Stock entity = optional.get();
                entity.setQuantity(entity.getQuantity().add(stock.getQuantity()));
                save(entity);
            }
        }
        log.info("本次增加库存成功");
    }

    @Override
    public StockInfo findByEntityKey(String entityKey) {
        if (StringUtils.isBlank(entityKey)) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        StockInfo stockInfo = new StockInfo();
        stockInfo.setEntityKey(entityKey);
        List<Stock> stocks = stockRepository.findAllByEntityKey(entityKey);
        if (stocks.isEmpty()) {
            return stockInfo;
        }
        stockInfo = convert(stocks);
        return stockInfo;
    }

    @Override
    public StockInfo findByEntityKeyAndWarehouse(String entityKey, String warehouse) {
        if (StringUtils.isBlank(entityKey) || StringUtils.isBlank(warehouse)) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        StockInfo stockInfo = new StockInfo();
        stockInfo.setEntityKey(entityKey);

        Optional<Stock> optional = stockRepository.findByEntityKeyAndWarehouse(entityKey, warehouse);
        if (optional.isPresent() == false) {
            return stockInfo;
        }
        Stock stock = optional.get();
        stockInfo.setUuid(stock.getUuid());
        stockInfo.setQuantity(stock.getQuantity());
        stockInfo.getWarehouseInfos().add(new WarehouseInfo(warehouse, stock.getQuantity()));
        return stockInfo;
    }

    @Override
    public Map<String, StockInfo> findByEntityKeys(List<String> entityKeys) {
        if (CollectionUtil.isEmpty(entityKeys)) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        Map<String, StockInfo> stockInfoMap = new HashMap<>();
        List<Stock> stocks = stockRepository.findAllByEntityKeyIn(entityKeys);
        if (stocks.isEmpty()) {
            entityKeys.stream().forEach(entityKey -> stockInfoMap.put(entityKey, new StockInfo(entityKey)));
            return stockInfoMap;
        }
        Map<String, List<Stock>> stockMap = stocks.stream().collect(Collectors.groupingBy(Stock::getEntityKey, Collectors.toList()));
        entityKeys.stream().forEach(entityKey -> {
            List<Stock> list = stockMap.get(entityKey);
            stockInfoMap.put(entityKey, CollectionUtil.isEmpty(list) ? new StockInfo(entityKey) : convert(list));
        });
        return stockInfoMap;
    }

    private StockInfo convert(List<Stock> stocks) {
        StockInfo stockInfo = new StockInfo();
        if (CollectionUtil.isEmpty(stocks)) {
            return stockInfo;
        }
        stockInfo.setEntityKey(stocks.get(0).getEntityKey());

        BigDecimal quantitySum = BigDecimal.ZERO;
        Iterator<Stock> iterator = stocks.iterator();
        while (iterator.hasNext()) {
            Stock stock = iterator.next();
            String warehouse = stock.getWarehouse();
            BigDecimal quantity = stock.getQuantity();
            //计算总库存量
            quantitySum = quantitySum.add(quantity);
            //每个仓库的数量
            stockInfo.getWarehouseInfos().add(new WarehouseInfo(warehouse, quantity));
        }
        stockInfo.setQuantity(quantitySum);
        return stockInfo;
    }
}