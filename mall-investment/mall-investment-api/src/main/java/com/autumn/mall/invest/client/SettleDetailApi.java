/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: SettleDetailApi
 * 日期: 2020/3/16 21:13
 * 说明:
 */
package com.autumn.mall.invest.client;

import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.model.SettleDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface SettleDetailApi {

    /**
     * 根据合同id查询该合同的结算明细
     *
     * @param contractId
     * @return
     */
    @GetMapping("/{contractId}")
    ResponseResult<List<SettleDetail>> findAllByContractId(@PathVariable("contractId") String contractId);
}