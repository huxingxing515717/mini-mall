/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.api
 * 文件名: StockInfo
 * 日期: 2020/3/15 21:14
 * 说明:
 */
package com.autumn.mall.basis.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存信息
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@NoArgsConstructor
public class StockInfo {

    private String id;

    /**
     * 实体标识
     */
    private String entityKey;

    /**
     * 实体对应各个仓库的库存总数量
     */
    private BigDecimal quantity;

    /**
     * 每个仓库的库存信息
     */
    private List<WarehouseInfo> warehouseInfos = new ArrayList<>();
}