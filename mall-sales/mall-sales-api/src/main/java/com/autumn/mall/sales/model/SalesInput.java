/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.model
 * 文件名: SalesInput
 * 日期: 2020/3/22 14:38
 * 说明:
 */
package com.autumn.mall.sales.model;

import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.invest.model.Contract;
import com.autumn.mall.invest.model.Store;
import com.autumn.mall.invest.model.Tenant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
@Data
@Entity
@Table(name = "sales_input")
@ApiModel(description = "销售数据录入单")
public class SalesInput implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @ApiModelProperty(value = "单号", dataType = "String")
    private String billNumber;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "业务状态", dataType = "BizState")
    private BizState state;

    @NotBlank
    @ApiModelProperty(value = "项目uuid", dataType = "String")
    private String storeUuid;

    @NotBlank
    @ApiModelProperty(value = "商户uuid", dataType = "String")
    private String tenantUuid;

    @NotBlank
    @ApiModelProperty(value = "合同uuid", dataType = "String")
    private String contractUuid;

    @NotBlank
    @ApiModelProperty(value = "付款方式uuid", dataType = "String")
    private String paymentTypeUuid;

    @NotNull
    @ApiModelProperty(value = "付款总金额", dataType = "BigDecimal")
    private BigDecimal payTotal;

    @ApiModelProperty(value = "商品uuid集合", dataType = "String")
    private String goodsUuids;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;

    @Transient
    private List<SalesInputDetail> details = new ArrayList<>();
    @Transient
    private Store store;
    @Transient
    private Tenant tenant;
    @Transient
    private Contract contract;
    @Transient
    private PaymentType paymentType;
}