/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.service
 * 文件名: SubjectService
 * 日期: 2020/3/15 19:41
 * 说明:
 */
package com.autumn.mall.account.service;

import com.autumn.mall.account.model.Subject;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface SubjectService extends CrudService<Subject>, CacheService, SupportStateService<UsingState> {

}