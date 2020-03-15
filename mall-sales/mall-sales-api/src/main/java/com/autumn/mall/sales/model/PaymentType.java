/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.model
 * 文件名: PaymentType
 * 日期: 2020/3/15 16:37
 * 说明:
 */
package com.autumn.mall.sales.model;

import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.commons.model.EntityState;
import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 销售付款方式
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@Entity
@Table(name = "sales_paymenttype")
@ApiModel(description = "付款方式")
public class PaymentType implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "Long")
    private String id;

    @NotBlank
    @Length(max = 32, message = "代码最大长度不超过32")
    @ApiModelProperty(value = "代码", dataType = "String")
    private String code;

    @NotBlank
    @Length(max = 64, message = "名称最大长度不超过64")
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "状态", dataType = "EntityState")
    private EntityState entityState;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;

    @Transient
    private List<OperationLog> operationLogs = new ArrayList<>();
}