/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.repository
 * 文件名: ContractRepository
 * 日期: 2020/3/16 19:57
 * 说明:
 */
package com.autumn.mall.invest.repository;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.invest.model.Contract;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface ContractRepository extends BaseRepository<Contract> {

    /**
     * 根据铺位id查询所有合同
     *
     * @param positionId
     * @return
     */
    List<Contract> findAllByPositionId(String positionId);
}