/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.api
 * 文件名: WarehouseInfo
 * 日期: 2020/3/15 21:14
 * 说明:
 */
package com.autumn.mall.basis.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 仓库信息
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseInfo {

    private String warehouse;

    private BigDecimal quantity;
}