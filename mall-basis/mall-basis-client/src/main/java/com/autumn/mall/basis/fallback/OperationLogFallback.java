/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.fallback
 * 文件名: OperationLogFallback
 * 日期: 2020/3/14 21:15
 * 说明:
 */
package com.autumn.mall.basis.fallback;

import com.autumn.mall.basis.client.OperationLogClient;
import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志客户端降级处理类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Component
public class OperationLogFallback implements OperationLogClient {

    @Override
    public ResponseResult<List<OperationLog>> findAllByEntityKey(String entityKey) {
        return new ResponseResult<>(CommonsResultCode.SERVER_ERROR, new ArrayList<>());
    }
}