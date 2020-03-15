/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.model
 * 文件名: QueryDefinition
 * 日期: 2020/3/14 19:28
 * 说明:
 */
package com.autumn.mall.commons.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 查询定义
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@NoArgsConstructor
public class QueryDefinition {

    private int page = 0;
    private int pageSize = 10;
    private String keyword;
    private Map<String, Object> params = new HashMap<>();
    private List<Order> orders = new ArrayList<>();

    public Map<String, Object> getParams() {
        return this.params == null ? new HashMap<>() : this.params;
    }

    public List<Order> getOrders() {
        if (this.orders == null || this.orders.isEmpty()) {
            Order order = new Order("id", OrderDirection.desc);
            return Arrays.asList(order);
        }
        return this.orders;
    }
}