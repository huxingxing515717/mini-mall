/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: PositionService
 * 日期: 2020/3/15 15:02
 * 说明:
 */
package com.autumn.mall.invest.service;

import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import com.autumn.mall.invest.model.Position;

/**
 * 铺位业务层接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
public interface PositionService extends CrudService<Position>, CacheService, SupportStateService<UsingState> {

}