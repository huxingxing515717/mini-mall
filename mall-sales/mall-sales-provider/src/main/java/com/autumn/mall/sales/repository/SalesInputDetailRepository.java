/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.repository
 * 文件名: SalesInputDetailRepository
 * 日期: 2020/3/22 15:22
 * 说明:
 */
package com.autumn.mall.sales.repository;

import com.autumn.mall.sales.model.SalesInputDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
public interface SalesInputDetailRepository extends CrudRepository<SalesInputDetail, String> {

    /**
     * 根据录入单uuid查询，结果按行号升序返回
     *
     * @param inputUuid
     * @return
     */
    List<SalesInputDetail> findAllByInputUuidOrderByLineNumber(String inputUuid);

    /**
     * 删除指定录入单uuid下的所有明细
     *
     * @param inputUuid
     */
    void deleteByInputUuid(String inputUuid);

    /**
     * 对指定合同且销售日期大于等于{@param beginDate}的销售总额进行汇总
     *
     * @param contractUuid 合同uuid
     * @param beginDate    销售日期起始值
     * @return
     */
    @Query(value = "select sum(a.total) from sales_input_detail a where a.sales_date>=:beginDate and " +
            "exists(select 1 from sales_input b where a.input_uuid = b.uuid " +
            "and b.contract_uuid = :contractUuid)", nativeQuery = true)
    BigDecimal summaryTotalByBeginDate(@Param("contractUuid") String contractUuid, @Param("beginDate") Date beginDate);

    /**
     * 对指定合同且销售日期小于等于{@param endDate}的销售总额进行汇总
     *
     * @param contractUuid 合同uuid
     * @param endDate      销售日期截止值
     * @return
     */
    @Query(value = "select sum(a.total) from sales_input_detail a where a.sales_date<=:endDate and " +
            "exists(select 1 from sales_input b where a.input_uuid = b.uuid " +
            "and b.contract_uuid = :contractUuid)", nativeQuery = true)
    BigDecimal summaryTotalByEndDate(@Param("contractUuid") String contractUuid, @Param("endDate") Date endDate);

    /**
     * 对指定合同且销售日期在指定日期范围内的销售总额进行汇总
     *
     * @param contractUuid 合同uuid
     * @param beginDate    销售日期起始值
     * @param endDate      销售日期截止值
     * @return
     */
    @Query(value = "select sum(a.total) from sales_input_detail a where a.sales_date<=:endDate and a.sales_date>=:beginDate and " +
            "exists(select 1 from sales_input b where a.input_uuid = b.uuid " +
            "and b.contract_uuid = :contractUuid)", nativeQuery = true)
    BigDecimal summaryTotalByDateRange(@Param("contractUuid") String contractUuid, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}