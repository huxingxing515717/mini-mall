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

import java.util.List;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface ContractService extends CrudService<Contract>, CacheService {

    /**
     * 批量生效
     *
     * @param ids
     * @return 返回生效失败的合同错误原因
     */
    Map<String, String> doEffect(List<String> ids);

}