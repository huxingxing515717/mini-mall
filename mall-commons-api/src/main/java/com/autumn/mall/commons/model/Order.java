/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.model
 * 文件名: Order
 * 日期: 2020/3/14 19:32
 * 说明:
 */
package com.autumn.mall.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * 排序字段
     */
    private String property;

    /**
     * 排序方向
     */
    private OrderDirection direction;
}