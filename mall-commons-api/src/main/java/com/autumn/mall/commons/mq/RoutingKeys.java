package com.autumn.mall.commons.mq;

/**
 * 路由键常量定义
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public interface RoutingKeys {

    /**
     * 实体被创建或者被更新，通常用于记录操作日志
     */
    String ENTITY_UPDATED = "entityUpdated";
}
