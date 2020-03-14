/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.utils
 * 文件名: RabbitMQUtils
 * 日期: 2020/3/14 20:28
 * 说明:
 */
package com.autumn.mall.commons.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ操作工具类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Component
public class RabbitMQUtils {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(String exchange, String routingKey, Map<String, String> msgMap) {
        amqpTemplate.convertAndSend(exchange, routingKey, msgMap);
    }
}