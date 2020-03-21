/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.order
 * 文件名: PositionOrderBuilder
 * 日期: 2020/3/21 10:57
 * 说明:
 */
package com.autumn.mall.invest.order;

import com.autumn.mall.commons.model.OrderDirection;
import com.autumn.mall.commons.repository.DefaultOrderBuilder;
import org.springframework.data.domain.Sort;

/**
 * @author Anbang713
 * @create 2020/3/21
 */
public class PositionOrderBuilder extends DefaultOrderBuilder {

    @Override
    public Sort.Order build(OrderDirection orderDirection, String property) {
        Sort.Direction direction = Sort.Direction.valueOf(orderDirection.name().toUpperCase());
        if ("store".equals(property)) {
            return new Sort.Order(direction, "storeUuid");
        } else if ("building".equals(property)) {
            return new Sort.Order(direction, "buildingUuid");
        } else if ("floor".equals(property)) {
            return new Sort.Order(direction, "floorUuid");
        }
        return super.build(orderDirection, property);
    }
}