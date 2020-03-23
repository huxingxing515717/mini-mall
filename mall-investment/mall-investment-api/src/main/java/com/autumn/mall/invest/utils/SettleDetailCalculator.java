/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.utils
 * 文件名: SettleDetailCalculator
 * 日期: 2020/3/16 21:31
 * 说明:
 */
package com.autumn.mall.invest.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.SettleDetail;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 合同结算明细计算器
 *
 * @author Anbang713
 * @create 2020/3/16
 */
public class SettleDetailCalculator {

    public static List<SettleDetail> calculate(Contract contract) {
        List<SettleDetail> details = new ArrayList<>();
        List<DateRange> dateRanges = splitContractDate(contract.getBeginDate(), contract.getEndDate());
        for (DateRange dateRange : dateRanges) {
            SettleDetail detail = new SettleDetail();
            detail.setUuid(new IdWorker().nextId());
            detail.setStoreUuid(contract.getStoreUuid());
            detail.setTenantUuid(contract.getTenantUuid());
            detail.setContractUuid(contract.getUuid());
            detail.setBeginDate(dateRange.getBeginDate());
            detail.setEndDate(dateRange.getEndDate());

            // 完整月的周期
            Date tempBeginDate = DateUtil.beginOfMonth(dateRange.getBeginDate());
            Date tempEndDate = DateUtil.endOfMonth(dateRange.getEndDate());
            DateRange monthRange = new DateRange(tempBeginDate, tempEndDate);

            BigDecimal days1 = BigDecimal.valueOf(dateRange.getDays());
            BigDecimal days2 = BigDecimal.valueOf(monthRange.getDays());
            detail.setTotal(contract.getMonthRent()
                    .multiply(days1)
                    .divide(days2, 2, BigDecimal.ROUND_HALF_UP));
            detail.setTax(detail.getTotal().multiply(contract.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            detail.setSubjectUuid(contract.getSubjectUuid());
            detail.setTaxRate(contract.getTaxRate());
            detail.setSalesRate(contract.getSalesRate());
            details.add(detail);
        }
        return details;
    }

    private static List<DateRange> splitContractDate(Date beginDate, Date endDate) {
        List<DateRange> dateRanges = new ArrayList<>();

        boolean isMultiMonth = false;
        Date tempBeginDate = beginDate;
        Date tempEndDate = DateUtil.endOfMonth(beginDate);
        while (DateUtils.truncatedCompareTo(tempEndDate, endDate, Calendar.DAY_OF_MONTH) < 0) {
            isMultiMonth = true;
            DateRange dateRange = new DateRange(DateUtil.beginOfDay(tempBeginDate), DateUtil.endOfDay(tempEndDate));
            dateRanges.add(dateRange);

            tempBeginDate = DateUtils.addDays(tempEndDate, 1);
            tempEndDate = DateUtil.endOfMonth(tempBeginDate);
        }

        if (isMultiMonth == false) {
            DateRange dateRange = new DateRange(DateUtil.beginOfDay(beginDate), DateUtil.endOfDay(endDate));
            dateRanges.add(dateRange);
        } else {
            DateRange dateRange = new DateRange(DateUtil.beginOfDay(tempBeginDate), DateUtil.endOfDay(endDate));
            dateRanges.add(dateRange);
        }
        return dateRanges;
    }

    public static void main(String[] args) {
        List<DateRange> dateRanges = splitContractDate(DateUtil.parse("2019-08-31"), DateUtil.parse("2020-03-30"));
        System.out.println(JSONUtil.toJsonStr(dateRanges));
    }
}