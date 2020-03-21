/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.repository
 * 文件名: OrderBuilder
 * 日期: 2020/3/21 10:17
 * 说明:
 */
package com.autumn.mall.commons.repository;

import com.autumn.mall.commons.model.OrderDirection;
import org.springframework.data.domain.Sort;

/**
 * @author Anbang713
 * @create 2020/3/21
 */
public interface OrderBuilder {

    Sort.Order build(OrderDirection orderDirection, String property);
}