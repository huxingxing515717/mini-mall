/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.controller
 * 文件名: SettleDetailController
 * 日期: 2020/3/16 21:19
 * 说明:
 */
package com.autumn.mall.invest.controller;

import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.ResponseResult;
import com.autumn.mall.invest.client.SettleDetailApi;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.service.SettleDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/16
 */
@Api(value = "结算明细管理")
@RestController
@RequestMapping("/setttledetail")
public class SettleDetailController implements SettleDetailApi {

    @Autowired
    private SettleDetailService settleDetailService;

    @Override
    public ResponseResult<List<SettleDetail>> findAllByContractUuid(String contractUuid) {
        return new ResponseResult(CommonsResultCode.SUCCESS, settleDetailService.findAllByContractUuid(contractUuid));
    }
}