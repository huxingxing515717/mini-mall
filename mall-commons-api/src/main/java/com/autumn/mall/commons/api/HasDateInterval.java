/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.api
 * 文件名: HasDateInterval
 * 日期: 2020/3/16 20:32
 * 说明:
 */
package com.autumn.mall.commons.api;

import java.util.Date;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
public interface HasDateInterval {

    /**
     * 起始日期
     */
    Date getBeginDate();

    void setBeginDate(Date beginDate);

    /**
     * 截止日期
     */
    Date getEndDate();

    void setEndDate(Date endDate);
}