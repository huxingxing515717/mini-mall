/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: SettleDetailServiceImpl
 * 日期: 2020/3/16 21:20
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.repository.SettleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算明细业务层接口
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Service
public class SettleDetailServiceImpl implements SettleDetailService {

    @Autowired
    private SettleDetailRepository settleDetailRepository;

    @Override
    public void saveAll(List<SettleDetail> details) {
        settleDetailRepository.saveAll(details);
    }

    @Override
    public List<SettleDetail> findAllByContractId(String contractId) {
        return settleDetailRepository.findAllByContractId(contractId);
    }
}