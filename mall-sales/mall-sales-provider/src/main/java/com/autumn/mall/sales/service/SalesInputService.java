/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.service
 * 文件名: SalesInputService
 * 日期: 2020/3/22 15:25
 * 说明:
 */
package com.autumn.mall.sales.service;

import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.sales.model.SalesInput;
import com.autumn.mall.sales.model.SalesInputDetail;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
public interface SalesInputService extends CrudService<SalesInput>, CacheService {

    /**
     * 根据录入单uuid查询明细，结果按行号升序返回
     *
     * @param uuid
     * @return
     */
    List<SalesInputDetail> findDetailsByUuid(String uuid);

    /**
     * 生效录入单
     *
     * @param uuid
     */
    void doEffect(String uuid);
}