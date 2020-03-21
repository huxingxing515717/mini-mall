/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.model
 * 文件名: Goods
 * 日期: 2020/3/15 20:18
 * 说明:
 */
package com.autumn.mall.product.model;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.model.UsingState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 商品
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@Entity
@Table(name = "prod_goods")
@ApiModel(description = "商品")
public class Goods implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uuid;

    @NotBlank
    @Length(max = 32, message = "代码最大长度不超过32")
    @ApiModelProperty(value = "代码", dataType = "String")
    private String code;

    @NotBlank
    @Length(max = 64, message = "名称最大长度不超过64")
    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "状态", dataType = "UsingState")
    private UsingState state;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;
}