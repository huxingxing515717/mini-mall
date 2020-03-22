/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.model
 * 文件名: SalesInputDetail
 * 日期: 2020/3/22 15:05
 * 说明:
 */
package com.autumn.mall.sales.model;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.product.model.Goods;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
@Data
@Entity
@Table(name = "sales_input_detail")
@ApiModel(description = "商品明细表")
public class SalesInputDetail implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @ApiModelProperty(value = "录入单uuid", dataType = "String")
    private String inputUuid;

    @ApiModelProperty(value = "行号", dataType = "Integer")
    private int lineNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "销售日期", dataType = "Date")
    private Date salesDate;

    @NotBlank
    @ApiModelProperty(value = "录入单uuid", dataType = "String")
    private String goodsUuid;

    @NotNull
    @ApiModelProperty(value = "库存数量", dataType = "BigDecimal")
    private BigDecimal warehouseQty;

    @NotBlank
    @ApiModelProperty(value = "仓库", dataType = "String")
    private String warehouse;

    @NotNull
    @ApiModelProperty(value = "销售数量", dataType = "BigDecimal")
    private BigDecimal quantity;

    @NotNull
    @ApiModelProperty(value = "销售金额", dataType = "BigDecimal")
    private BigDecimal total;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;

    @Transient
    private Goods goods;
}