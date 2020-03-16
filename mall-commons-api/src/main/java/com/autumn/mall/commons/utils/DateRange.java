/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.utils
 * 文件名: DateRange
 * 日期: 2020/3/16 20:31
 * 说明:
 */
package com.autumn.mall.commons.utils;

import com.autumn.mall.commons.api.HasDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期区间工具类
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange implements HasDateInterval {

    private Date beginDate;
    private Date endDate;

    /**
     * 是否包含指定的日期
     *
     * @param date 日期
     * @return true=包含，false=不包含
     */
    public boolean include(Date date) {
        if (date == null) {
            return false;
        } else if (getBeginDate() != null && date.before(getBeginDate())) {
            return false;
        } else if (getEndDate() != null && date.after(getEndDate())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否包含指定的日期范围。
     *
     * @param range 日期范围。
     * @return 包含返回true，不包含返回false。
     */
    public boolean include(HasDateInterval range) {
        if (range == null)
            return false;
        if (include(range.getBeginDate()) == false)
            return false;
        if (include(range.getEndDate()) == false)
            return false;
        return true;
    }

    /**
     * 取两个日期区间交叉的区间
     *
     * @param range
     * @return 交叉区间，如果没有交叉返回null
     */
    public DateRange overlap(HasDateInterval range) {
        if (range == null)
            return null;
        if (getBeginDate() == null || getEndDate() == null || range.getBeginDate() == null
                || range.getEndDate() == null)
            return null;
        Date b = max(getBeginDate(), range.getBeginDate());
        Date e = min(getEndDate(), range.getEndDate());
        if (b.after(e))
            return null;
        else
            return new DateRange(b, e);
    }

    /**
     * 两个日期区间是否交叉。
     *
     * @param range 日期范围。
     * @return 交叉返回true，不交叉返回false。
     */
    public boolean overlapExists(HasDateInterval range) {
        if (range == null)
            return false;
        if (getBeginDate() == null || getEndDate() == null || range.getBeginDate() == null
                || range.getEndDate() == null)
            return false;
        if (getBeginDate().after(getEndDate()) || range.getBeginDate().after(range.getEndDate())) {
            return false;
        } else if (getBeginDate().after(range.getEndDate())
                || getEndDate().before(range.getBeginDate())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 两个日期时间的最大值
     *
     * @param d1 时间
     * @param d2 时间
     * @return 最大日期
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return null;

        if (d1.before(d2))
            return d2;
        else
            return d1;
    }

    /**
     * 两个日期时间的最小值
     *
     * @param d1 时间
     * @param d2 时间
     * @return 最小日期
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return null;

        if (d1.before(d2))
            return d1;
        else
            return d2;
    }

    /**
     * 包含的天数
     */
    public int getDays() {
        if (getBeginDate() == null || getEndDate() == null) {
            return 0;
        } else if (getBeginDate().after(getEndDate())) {
            return -1;
        } else {
            Date d2 = beginOfTheDate(getEndDate());
            long days = (d2.getTime() - getBeginDate().getTime()) / (1000 * 60 * 60 * 24);
            return (int) days + 1;
        }
    }

    /**
     * 指定日期当天的开始时间
     *
     * @param date 日期
     * @return 开始时间
     */
    public static Date beginOfTheDate(Date date) {
        if (date == null)
            return null;
        else {
            return DateUtils.truncate(date, Calendar.DATE);
        }
    }

    /**
     * 指定日期当天的结束时间
     *
     * @param date 日期
     * @return 结束时间
     */
    public static Date endOfTheDate(Date date) {
        if (date == null)
            return null;
        else {
            date = DateUtils.truncate(date, Calendar.DATE);
            date = DateUtils.addDays(date, 1);
            return DateUtils.addSeconds(date, -1);
        }
    }

    /**
     * 指定日期前一天的结束时间
     *
     * @param date 日期
     * @return 前一天结束
     */
    public static Date endOfLastDate(Date date) {
        if (date == null)
            return null;
        else {
            date = DateUtils.truncate(date, Calendar.DATE);
            return DateUtils.addSeconds(date, -1);
        }
    }

    /**
     * 去掉毫秒
     *
     * @param date 时间
     * @return 毫秒为0的时间
     */
    public static Date truncateMillisecond(Date date) {
        if (date == null)
            return null;
        else
            return DateUtils.truncate(date, Calendar.MILLISECOND);
    }
}