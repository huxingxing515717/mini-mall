/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.repository
 * 文件名: SettleDetailRepository
 * 日期: 2020/3/16 21:17
 * 说明:
 */
package com.autumn.mall.invest.repository;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.invest.model.SettleDetail;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface SettleDetailRepository extends BaseRepository<SettleDetail> {

    void deleteByContractUuid(String contractUuid);

    List<SettleDetail> findAllByContractUuid(String contractUuid);
}