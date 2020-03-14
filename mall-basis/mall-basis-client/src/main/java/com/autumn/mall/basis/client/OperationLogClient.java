/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.client
 * 文件名: OperationLogClient
 * 日期: 2020/3/14 21:13
 * 说明:
 */
package com.autumn.mall.basis.client;

import com.autumn.mall.basis.fallback.OperationLogFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 操作日志客户端接口
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@FeignClient(name = "mall-basis-provider", path = "/operationlog", fallback = OperationLogFallback.class)
public interface OperationLogClient extends OperationLogApi {

}