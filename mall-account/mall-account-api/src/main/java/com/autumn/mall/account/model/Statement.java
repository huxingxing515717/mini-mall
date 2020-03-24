/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.model
 * 文件名: Statement
 * 日期: 2020/3/23 20:32
 * 说明:
 */
package com.autumn.mall.account.model;

import com.autumn.mall.account.commons.PayState;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.model.Tenant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/23
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "acc_statement")
@ApiModel(description = "账单")
public class Statement implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @ApiModelProperty(value = "单号，客户端只读", dataType = "String")
    private String billNumber;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "业务状态", dataType = "BizState")
    private BizState state = BizState.ineffect;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "收款状态", dataType = "PayState")
    private PayState payState = PayState.unPay;

    @NotBlank
    @ApiModelProperty(value = "项目uuid", dataType = "String")
    private String storeUuid;

    @NotBlank
    @ApiModelProperty(value = "商户uuid", dataType = "String")
    private String tenantUuid;

    @NotBlank
    @ApiModelProperty(value = "合同uuid", dataType = "String")
    private String contractUuid;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "起始日期", dataType = "Date")
    private Date accountDate;

    @NotNull
    @ApiModelProperty(value = "销售提成率", dataType = "BigDecimal")
    private BigDecimal salesRate;

    @NotNull
    @ApiModelProperty(value = "本次结算金额", dataType = "BigDecimal")
    private BigDecimal total;

    @NotNull
    @ApiModelProperty(value = "本次结算税额", dataType = "BigDecimal")
    private BigDecimal tax;

    @Transient
    private List<StatementDetail> details = new ArrayList<>();
    @Transient
    private Store store;
    @Transient
    private Tenant tenant;
    @Transient
    private Contract contract;
}