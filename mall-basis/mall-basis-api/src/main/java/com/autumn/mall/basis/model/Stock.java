/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.model
 * 文件名: Stock
 * 日期: 2020/3/15 21:05
 * 说明:
 */
package com.autumn.mall.basis.model;

import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 库存
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@Entity
@Table(name = "basis_stock")
@ApiModel(description = "库存")
public class Stock implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @ApiModelProperty(value = "实体标识", dataType = "String")
    private String entityKey;

    @NotBlank
    @ApiModelProperty(value = "仓库", dataType = "String")
    private String warehouse;

    @NotNull
    @Min(value = 0, message = "库存不能小于0")
    @ApiModelProperty(value = "库存", dataType = "BigDecimal")
    private BigDecimal quantity;
}