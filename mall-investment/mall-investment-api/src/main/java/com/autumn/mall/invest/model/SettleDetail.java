/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.model
 * 文件名: SettleDetail
 * 日期: 2020/3/16 21:09
 * 说明:
 */
package com.autumn.mall.invest.model;

import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算明细
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Data
@Entity
@Table(name = "invest_settle_detail")
@ApiModel(description = "结算明细")
public class SettleDetail implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @ApiModelProperty(value = "合同id", dataType = "String")
    private String contractId;

    @NotNull
    @ApiModelProperty(value = "结算起始日期", dataType = "Date")
    private Date beginDate;

    @NotNull
    @ApiModelProperty(value = "结算结束日期", dataType = "Date")
    private Date endDate;

    @NotNull
    @ApiModelProperty(value = "本次结算金额", dataType = "BigDecimal")
    private BigDecimal total;

    @NotNull
    @ApiModelProperty(value = "本次结算税额", dataType = "BigDecimal")
    private BigDecimal tax;

    @NotBlank
    @ApiModelProperty(value = "科目id", dataType = "String")
    private String subjectId;

    @NotNull
    @ApiModelProperty(value = "税率", dataType = "BigDecimal")
    private BigDecimal taxRate;
}