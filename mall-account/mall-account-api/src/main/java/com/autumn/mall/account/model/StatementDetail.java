/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.model
 * 文件名: StatementDetail
 * 日期: 2020/3/23 21:05
 * 说明:
 */
package com.autumn.mall.account.model;

import com.autumn.mall.commons.model.IsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Anbang713
 * @create 2020/3/23
 */
@Data
@Entity
@Table(name = "acc_statement_detail")
@ApiModel(description = "账单明细")
public class StatementDetail implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @ApiModelProperty(value = "账单uuid", dataType = "String")
    private String statementUuid;

    @ApiModelProperty(value = "行号", dataType = "Integer")
    private int lineNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结算起始日期", dataType = "Date")
    private Date beginDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结算结束日期", dataType = "Date")
    private Date endDate;

    @NotNull
    @ApiModelProperty(value = "本次结算金额", dataType = "BigDecimal")
    private BigDecimal total;

    @NotNull
    @ApiModelProperty(value = "本次结算税额", dataType = "BigDecimal")
    private BigDecimal tax;

    @NotNull
    @ApiModelProperty(value = "本次销售提成总额", dataType = "BigDecimal")
    private BigDecimal salesTotal;

    @NotNull
    @ApiModelProperty(value = "本次销售提成税额", dataType = "BigDecimal")
    private BigDecimal salesTax;
}