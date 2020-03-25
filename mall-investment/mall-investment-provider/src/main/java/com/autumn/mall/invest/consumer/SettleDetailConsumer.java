/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.consumer
 * 文件名: SettleDetailConsumer
 * 日期: 2020/3/24 21:46
 * 说明:
 */
package com.autumn.mall.invest.consumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.autumn.mall.commons.mq.Exchanges;
import com.autumn.mall.commons.mq.RoutingKeys;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.repository.SettleDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 出账成功销售费
 *
 * @author Anbang713
 * @create 2020/3/24
 */
@Slf4j
@Component
public class SettleDetailConsumer {

    private static final String SETTLE_SUCCESSFUL_QUEUE = "mini-mall.invest.settleSuccessful.queue";
    private static final String STATEMENT_DELETED_QUEUE = "mini-mall.invest.statementDeleted.queue";

    @Autowired
    private SettleDetailRepository settleDetailRepository;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = SETTLE_SUCCESSFUL_QUEUE, durable = "true"),
            exchange = @Exchange(value = Exchanges.MALL_ACCOUNT_PROVIDER_EXCHANGE,
                    ignoreDeclarationExceptions = "true"),
            key = {RoutingKeys.SETTLE_SUCCESSFUL}))
    public void settleSuccessful(Map<String, String> msg) throws Exception {
        JSONObject jsonObject = JSONUtil.parseFromMap(msg);
        log.info("接收到来自：" + Exchanges.MALL_ACCOUNT_PROVIDER_EXCHANGE + "的出账成功消息，消息体：" + jsonObject.toString());
        String statementUuid = jsonObject.getStr("statementUuid");
        String settleDetails = jsonObject.getStr("settleDetails");
        List<String> settleDetailUuids = JSONUtil.toList(JSONUtil.parseArray(settleDetails), String.class);
        Iterable<SettleDetail> iterable = settleDetailRepository.findAllById(settleDetailUuids);
        List<SettleDetail> details = new ArrayList<>();
        iterable.forEach(detail -> {
            detail.setStatementUuid(statementUuid);
            details.add(detail);
        });
        settleDetailRepository.saveAll(details);
        log.info("账单uuid回写成功");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = STATEMENT_DELETED_QUEUE, durable = "true"),
            exchange = @Exchange(value = Exchanges.MALL_ACCOUNT_PROVIDER_EXCHANGE,
                    ignoreDeclarationExceptions = "true"),
            key = {RoutingKeys.STATEMENT_DELETED}))
    public void statementDeleted(Map<String, String> msg) throws Exception {
        JSONObject jsonObject = JSONUtil.parseFromMap(msg);
        log.info("接收到来自：" + Exchanges.MALL_ACCOUNT_PROVIDER_EXCHANGE + "的账单删除消息，消息体：" + jsonObject.toString());
        String statementUuid = jsonObject.getStr("statementUuid");
        List<SettleDetail> details = settleDetailRepository.findAllByStatementUuid(statementUuid);
        details.stream().forEach(detail -> detail.setStatementUuid(SettleDetail.NONE_STATEMENT));
        settleDetailRepository.saveAll(details);
        log.info("合同结算明细已经恢复出账");
    }
}