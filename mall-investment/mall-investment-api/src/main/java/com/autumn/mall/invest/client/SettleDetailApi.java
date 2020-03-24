/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: SettleDetailApi
 * 日期: 2020/3/16 21:13
 * 说明:
 */
package com.autumn.mall.invest.client;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.response.SummaryQueryResult;
import com.autumn.mall.invest.model.SettleDetail;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface SettleDetailApi {

    /**
     * 根据合同uuid查询该合同的结算明细
     *
     * @param contractUuid
     * @return
     */
    @GetMapping("/{contractUuid}")
    ResponseResult<List<SettleDetail>> findAllByContractUuid(@PathVariable("contractUuid") String contractUuid);

    /**
     * 查找指定合同在某个日期前是否有未出账的数据
     *
     * @param contractUuid 合同uuid
     * @param beginDate    起始日期
     * @return
     */
    @GetMapping("/{contractUuid}/check")
    ResponseResult<Boolean> existsNoStatement(@PathVariable("contractUuid") String contractUuid, @RequestParam("beginDate") Date beginDate);

    /**
     * 根据查询定义查询
     *
     * @param definition 查询定义
     * @return
     */
    @PostMapping("/query")
    ResponseResult<SummaryQueryResult<SettleDetail>> query(@RequestBody QueryDefinition definition);
}