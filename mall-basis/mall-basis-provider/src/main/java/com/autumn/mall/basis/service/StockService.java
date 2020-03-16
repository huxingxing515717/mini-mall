/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: StockService
 * 日期: 2020/3/15 21:32
 * 说明:
 */
package com.autumn.mall.basis.service;

import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.model.Stock;

import java.util.List;
import java.util.Map;

/**
 * 库存业务层接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
public interface StockService {

    boolean save(Stock entity);

    StockInfo findByEntityKey(String entityKey);

    StockInfo findByEntityKeyAndWarehouse(String entityKey, String warehouse);

    Map<String, StockInfo> findByEntityKeys(List<String> entityKeys);
}