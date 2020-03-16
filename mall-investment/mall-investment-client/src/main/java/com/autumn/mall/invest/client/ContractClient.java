/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.client
 * 文件名: ContractClient
 * 日期: 2020/3/16 19:51
 * 说明:
 */
package com.autumn.mall.invest.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 合同客户端接口
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@FeignClient(name = "mall-investment-provider", path = "/contract")
public interface ContractClient extends ContractApi {

}