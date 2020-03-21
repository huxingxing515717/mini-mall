/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: ContractService
 * 日期: 2020/3/16 19:57
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.invest.model.Contract;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface ContractService extends CrudService<Contract>, CacheService {

    /**
     * 生效合同
     *
     * @param uuid
     */
    void doEffect(String uuid);

}