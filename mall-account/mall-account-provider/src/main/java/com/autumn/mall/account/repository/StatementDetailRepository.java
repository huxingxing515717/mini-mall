/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.repository
 * 文件名: StatementDetailRepository
 * 日期: 2020/3/23 21:22
 * 说明:
 */
package com.autumn.mall.account.repository;

import com.autumn.mall.account.model.StatementDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/23
 */
public interface StatementDetailRepository extends CrudRepository<StatementDetail, String> {

    /**
     * 根据账单uuid查询，结果按行号升序返回
     *
     * @param statementUuid
     * @return
     */
    List<StatementDetail> findAllByStatementUuidOrderByLineNumber(String statementUuid);

    /**
     * 删除指定账单uuid下的所有明细
     *
     * @param statementUuid
     */
    void deleteByStatementUuid(String statementUuid);
}