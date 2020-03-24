/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.client
 * 文件名: SalesInputApi
 * 日期: 2020/3/22 15:16
 * 说明:
 */
package com.autumn.mall.sales.client;

import com.autumn.mall.commons.client.ClientApi;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.commons.utils.DateRange;
import com.autumn.mall.sales.model.SalesInput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
public interface SalesInputApi extends ClientApi<SalesInput> {


    /**
     * 查询指定合同和时间范围内的销售总额
     *
     * @param contractUuid 合同uuid
     * @param dateRange    时间范围
     * @return 销售总额
     */
    @PostMapping("/{contractUuid}/total")
    ResponseResult<BigDecimal> getTotalByContract(@PathVariable("contractUuid") String contractUuid, @RequestBody DateRange dateRange);
}