/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: StockServiceImpl
 * 日期: 2020/3/15 21:32
 * 说明:
 */
package com.autumn.mall.basis.service;

import com.autumn.mall.basis.api.StockInfo;
import com.autumn.mall.basis.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Service
public class StockServiceImpl implements StockService {

    @Override
    public boolean save(Stock entity) {
        return false;
    }

    @Override
    public StockInfo findByEntityKey(String entityKey) {
        return null;
    }

    @Override
    public StockInfo findByEntityKeyAndWarehouse(String entityKey, String warehouse) {
        return null;
    }

    @Override
    public Map<String, StockInfo> findByEntityKeys(List<String> entityKeys) {
        return null;
    }
}