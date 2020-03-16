/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: SettleDetailService
 * 日期: 2020/3/16 21:20
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.invest.model.SettleDetail;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface SettleDetailService {

    void saveAll(String contractId, List<SettleDetail> details);

    List<SettleDetail> findAllByContractId(String contractId);
}