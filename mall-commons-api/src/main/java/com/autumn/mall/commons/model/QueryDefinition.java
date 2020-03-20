/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.model
 * 文件名: QueryDefinition
 * 日期: 2020/3/14 19:28
 * 说明:
 */
package com.autumn.mall.commons.model;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询定义
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@NoArgsConstructor
public class QueryDefinition {

    private int page = 1;
    private int pageSize = 10;
    private String keyword;
    private Map<String, Object> filter = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private boolean querySummary = false;

    public Map<String, Object> getFilter() {
        return this.filter == null ? new HashMap<>() : this.filter;
    }

    public void setSort(String sort) {
        orders.addAll(JSONUtil.toList(JSONUtil.parseArray(sort), Order.class));
    }

    public int getCurrentPage() {
        return page <= 1 ? 0 : page - 1;
    }
}