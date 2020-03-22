/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.model
 * 文件名: GoodsInboundDetail
 * 日期: 2020/3/17 7:47
 * 说明:
 */
package com.autumn.mall.product.model;

import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品入库明细
 *
 * @author Anbang713
 * @create 2020/3/17
 */
@Data
@Entity
@Table(name = "prod_inbound_detail")
@ApiModel(description = "商品")
public class GoodsInboundDetail implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @ApiModelProperty(value = "入库单uuid", dataType = "String")
    private String goodsInboundUuid;

    @ApiModelProperty(value = "行号", dataType = "Integer")
    private int lineNumber;

    @NotBlank
    @ApiModelProperty(value = "商品uuid", dataType = "String")
    private String goodsUuid;

    @NotNull
    @ApiModelProperty(value = "入库数量", dataType = "BigDecimal")
    private BigDecimal quantity;

    @NotNull
    @ApiModelProperty(value = "库存数量", dataType = "BigDecimal")
    private BigDecimal warehouseQty;

    @Transient
    private Goods goods;
}