/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: SettleDetailService
 * 日期: 2020/3/16 21:20
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.invest.model.SettleDetail;

import java.util.Date;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface SettleDetailService {

    void saveAll(String contractUuid, List<SettleDetail> details);

    List<SettleDetail> findAllByContractUuid(String contractUuid);

    /**
     * 根据关键字分页查询
     *
     * @param definition 查询定义
     * @return
     */
    QueryResult<SettleDetail> query(QueryDefinition definition);

    /**
     * 查找指定合同在某个日期前是否有未出账的数据
     *
     * @param contractUuid 合同uuid
     * @param beginDate    起始日期
     * @return
     */
    Boolean existsNoStatement(String contractUuid, Date beginDate);
}