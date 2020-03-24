/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.order
 * 文件名: StatementOrderBuilder
 * 日期: 2020/3/24 7:55
 * 说明:
 */
package com.autumn.mall.account.order;

import com.autumn.mall.commons.model.OrderDirection;
import com.autumn.mall.commons.repository.DefaultOrderBuilder;
import org.springframework.data.domain.Sort;

/**
 * @author Anbang713
 * @create 2020/3/24
 */
public class StatementOrderBuilder extends DefaultOrderBuilder {

    @Override
    public Sort.Order build(OrderDirection orderDirection, String property) {
        Sort.Direction direction = Sort.Direction.valueOf(orderDirection.name().toUpperCase());
        if ("store".equals(property)) {
            return new Sort.Order(direction, "storeUuid");
        } else if ("tenant".equals(property)) {
            return new Sort.Order(direction, "tenantUuid");
        } else if ("contract".equals(property)) {
            return new Sort.Order(direction, "contractUuid");
        }
        return super.build(orderDirection, property);
    }
}