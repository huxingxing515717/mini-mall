/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: OperationLogService
 * 日期: 2020/3/14 21:22
 * 说明:
 */
package com.autumn.mall.basis.service;

import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
public interface OperationLogService extends CrudService<OperationLog>, CacheService {

    List<OperationLog> findAllByEntityKey(String entityKey);
}