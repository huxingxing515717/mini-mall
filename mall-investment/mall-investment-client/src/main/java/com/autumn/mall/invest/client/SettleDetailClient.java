/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: SettleDetailClient
 * 日期: 2020/3/16 21:16
 * 说明:
 */
package com.autumn.mall.invest.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 结算明细对外接口
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@FeignClient(name = "mall-investment-provider", path = "/settledetail")
public interface SettleDetailClient extends SettleDetailApi {

}