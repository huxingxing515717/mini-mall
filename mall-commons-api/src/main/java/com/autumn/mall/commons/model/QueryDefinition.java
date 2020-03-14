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

    private int page;
    private int pageSize;
    private String keyword;
    private Map<String, Object> params = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
}