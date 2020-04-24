/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.consumer
 * 文件名: OperationLogConsumer
 * 日期: 2020/3/14 21:28
 * 说明:
 */
package com.autumn.mall.basis.consumer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.basis.service.OperationLogService;
import com.autumn.mall.commons.model.Admin;
import com.autumn.mall.commons.mq.Exchanges;
import com.autumn.mall.commons.mq.RoutingKeys;
import com.autumn.mall.commons.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 操作日志消费者
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Slf4j
@Component
public class OperationLogConsumer {

    @Autowired
    private OperationLogService operationLogService;

    private static final String QUERY_NAME = "mini-mall.basis.operationlog.queue";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUERY_NAME, durable = "true"),
            exchange = @Exchange(value = Exchanges.MALL_COMMONS_EXCHANGE,
                    ignoreDeclarationExceptions = "true"),
            key = {RoutingKeys.ENTITY_UPDATED}))
    public void updateEntityLog(Map<String, String> msg) {
        JSONObject jsonObject = JSONUtil.parseFromMap(msg);
        log.info("接收到操作日志更新消息，消息体：{}", jsonObject.toString());

        OperationLog operationLog = new OperationLog();
        operationLog.setUuid(new IdWorker().nextId());
        operationLog.setEntityKey(jsonObject.getStr("entityKey"));
        operationLog.setTime(jsonObject.getStr("time", DateUtil.now()));
        operationLog.setOperator(JSONUtil.toBean(jsonObject.getStr("operator"), Admin.class));
        operationLog.setActionName(jsonObject.getStr("actionName"));
        operationLog.setReason(jsonObject.getStr("reason"));
        operationLogService.save(operationLog);
    }

}