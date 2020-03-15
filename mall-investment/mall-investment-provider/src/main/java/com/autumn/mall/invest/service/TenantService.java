/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: TenantService
 * 日期: 2020/3/15 16:06
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.invest.model.Tenant;

/**
 * 商户业务层接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
public interface TenantService extends CrudService<Tenant>, CacheService {

}