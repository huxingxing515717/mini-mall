/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.repository
 * 文件名: OperationLogRepository
 * 日期: 2020/3/14 21:23
 * 说明:
 */
package com.autumn.mall.basis.repository;

import com.autumn.mall.basis.model.OperationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
public interface OperationLogRepository extends MongoRepository<OperationLog, Long> {

    List<OperationLog> findAllByEntityKey(String entityKey);
}