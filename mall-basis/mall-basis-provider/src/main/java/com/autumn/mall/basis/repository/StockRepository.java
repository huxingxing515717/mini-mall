/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.repository
 * 文件名: StockRepository
 * 日期: 2020/3/15 21:24
 * 说明:
 */
package com.autumn.mall.basis.repository;

import com.autumn.mall.basis.model.Stock;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface StockRepository extends Repository<Stock, String> {

    /**
     * 新增库存或者减少库存
     *
     * @param entity
     * @return 是否操作成功
     */
    boolean save(Stock entity);

    /**
     * 通过实体标识查询库存信息
     *
     * @param entityKey
     * @return
     */
    List<Stock> findAllByEntityKey(String entityKey);

    /**
     * 通过实体标识和仓库查询库存信息
     *
     * @param entityKey
     * @param warehouse
     * @return
     */
    Stock findByEntityKeyAndWarehouse(String entityKey, String warehouse);

}