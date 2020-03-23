/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.client
 * 文件名: StatementClient
 * 日期: 2020/3/23 21:16
 * 说明:
 */
package com.autumn.mall.account.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 账单客户端接口
 *
 * @author Anbang713
 * @create 2020/3/23
 */
@FeignClient(name = "mall-account-provider", contextId = "mall-account-StatementClient", path = "/statement")
public interface StatementClient extends StatementApi {

}