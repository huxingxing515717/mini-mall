/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: OperationLogService
 * 日期: 2020/3/14 21:22
 * 说明:
 */
package com.autumn.mall.basis.service;

import com.autumn.mall.basis.model.OperationLog;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
public interface OperationLogService {

    /**
     * 新建实体，新增一条记录
     *
     * @param entity 实体
     * @return 新增记录的主键值
     */
    String save(OperationLog entity);

    List<OperationLog> findAllByEntityKey(String entityKey);
}