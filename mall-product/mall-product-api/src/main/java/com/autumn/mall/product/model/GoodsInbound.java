/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.model
 * 文件名: GoodsInbound
 * 日期: 2020/3/17 7:49
 * 说明:
 */
package com.autumn.mall.product.model;

import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品入库单
 *
 * @author Anbang713
 * @create 2020/3/17
 */
@Data
@Entity
@Table(name = "prod_goods_inbound")
@ApiModel(description = "商品入库单")
public class GoodsInbound implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @ApiModelProperty(value = "单号", dataType = "String")
    private String billNumber;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "业务状态", dataType = "BizState")
    private BizState state;

    @NotNull
    @ApiModelProperty(value = "入库日期", dataType = "Date")
    private Date inboundDate;

    @NotBlank
    @ApiModelProperty(value = "仓库", dataType = "String")
    private String warehouse;

    @ApiModelProperty(value = "商品id集合", dataType = "String")
    private String goodsUuids;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;

    @Transient
    @ApiModelProperty(value = "入库明细")
    private List<GoodsInboundDetail> details = new ArrayList<>();
}