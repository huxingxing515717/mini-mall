/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.service
 * 文件名: StatementService
 * 日期: 2020/3/23 21:24
 * 说明:
 */
package com.autumn.mall.account.service;

import com.autumn.mall.account.model.Statement;
import com.autumn.mall.account.model.StatementDetail;
import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/23
 */
public interface StatementService extends CrudService<Statement>, CacheService {

    /**
     * 根据账单uuid查询明细，结果按行号升序返回
     *
     * @param uuid
     * @return
     */
    List<StatementDetail> findDetailsByUuid(String uuid);

    /**
     * 生效账单
     *
     * @param uuid
     */
    void doEffect(String uuid);

    /**
     * 账单收款，只有生效的账单可以收款
     *
     * @param uuid
     */
    void doPay(String uuid);
}