/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.repository
 * 文件名: PaymentTypeRepository
 * 日期: 2020/3/15 16:45
 * 说明:
 */
package com.autumn.mall.sales.repository;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.sales.model.PaymentType;

import java.util.Optional;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface PaymentTypeRepository extends BaseRepository<PaymentType> {

    /**
     * 根据代码查找
     *
     * @param code
     * @return
     */
    Optional<PaymentType> findByCode(String code);
}