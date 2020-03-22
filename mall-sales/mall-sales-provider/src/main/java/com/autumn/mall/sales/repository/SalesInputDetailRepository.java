/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.repository
 * 文件名: SalesInputDetailRepository
 * 日期: 2020/3/22 15:22
 * 说明:
 */
package com.autumn.mall.sales.repository;

import com.autumn.mall.sales.model.SalesInputDetail;
import org.springframework.data.repository.CrudRepository;

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
}