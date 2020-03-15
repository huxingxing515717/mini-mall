/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.repository
 * 文件名: GoodsRepository
 * 日期: 2020/3/15 20:35
 * 说明:
 */
package com.autumn.mall.product.repository;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.product.model.Goods;

import java.util.Optional;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface GoodsRepository extends BaseRepository<Goods> {

    /**
     * 根据代码查找
     *
     * @param code
     * @return
     */
    Optional<Goods> findByCode(String code);
}