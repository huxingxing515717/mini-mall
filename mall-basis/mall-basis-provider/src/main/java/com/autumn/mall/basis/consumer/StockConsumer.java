/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.consumer
 * 文件名: StockConsumer
 * 日期: 2020/3/15 21:44
 * 说明:
 */
package com.autumn.mall.basis.consumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.model.Stock;
import com.autumn.mall.basis.service.StockService;
import com.autumn.mall.commons.mq.Exchanges;
import com.autumn.mall.commons.mq.RoutingKeys;
import com.autumn.mall.commons.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 库存消费者
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Slf4j
@Component
public class StockConsumer {

    private static final String QUERY_NAME = "mini-mall.basis.stock.queue";

    @Autowired
    private StockService stockService;
    @Autowired
    private RedisUtils redisUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUERY_NAME, durable = "true"),
            exchange = @Exchange(value = Exchanges.MALL_PRODUCT_PROVIDER_EXCHANGE,
                    ignoreDeclarationExceptions = "true"),
            key = {RoutingKeys.STOCK_UPDATED}))
    public void updateStock(Map<String, String> msg) throws Exception {
        JSONObject jsonObject = JSONUtil.parseFromMap(msg);
        log.info("接收到来自：" + Exchanges.MALL_PRODUCT_PROVIDER_EXCHANGE + "的库存更新消息，消息体：" + jsonObject.toString());
        String entityKey = jsonObject.getStr("entityKey");
        String warehouse = jsonObject.getStr("warehouse");
        if (StringUtils.isBlank(entityKey) || StringUtils.isBlank(warehouse)) {
            // TODO 抛出异常
        }
        BigDecimal quantity = jsonObject.getBigDecimal("quantity");
        if (quantity == null) {
            //TODO 抛出异常
        }
        // 本次更新库存数量等于0，不处理
        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        // 分布式锁
        String key = entityKey + "_" + warehouse;
        while (redisUtils.tryLock(key) == false) {
            TimeUnit.SECONDS.sleep(3);
        }
        Stock stock = new Stock();
        stock.setEntityKey(entityKey);
        stock.setWarehouse(warehouse);

        StockInfo stockInfo = stockService.findByEntityKeyAndWarehouse(entityKey, warehouse);
        if (stockInfo == null) {
            if (quantity.compareTo(BigDecimal.ZERO) < 0) {
// TODO 数量小于0，说明是减库存，但stockInfo等于null，说明此时还没有库存，抛出异常。
            }
            stock.setQuantity(quantity);
        } else {
            BigDecimal tempQty = stockInfo.getQuantity().add(quantity);
            if (tempQty.compareTo(BigDecimal.ZERO) < 0) {
                // TODO 此时库存数量不够减了，抛出异常
            }
            stock.setId(stockInfo.getId());
            stock.setQuantity(tempQty);
        }
        boolean saved = stockService.save(stock);
        // 释放分布式锁
        if (saved) {
            redisUtils.remove(key);
        }
    }
}