/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.client
 * 文件名: SubjectClient
 * 日期: 2020/3/15 19:33
 * 说明:
 */
package com.autumn.mall.account.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 科目客户端接口
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@FeignClient(name = "mall-account-provider", path = "/subject")
public interface SubjectClient extends SubjectApi {

}