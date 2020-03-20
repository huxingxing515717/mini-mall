/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.model
 * 文件名: Tenant
 * 日期: 2020/3/15 16:01
 * 说明:
 */
package com.autumn.mall.invest.model;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.model.UsingState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 商户
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@Entity
@Table(name = "invest_tenant")
@ApiModel(description = "商户")
public class Tenant implements IsEntity {

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
    private UsingState usingState;

    @Length(max = 1024, message = "说明最大长度不超过64")
    @ApiModelProperty(value = "说明", dataType = "String")
    private String remark;
}