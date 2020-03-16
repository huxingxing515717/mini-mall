/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.model
 * 文件名: Floor
 * 日期: 2020/3/15 14:07
 * 说明:
 */
package com.autumn.mall.invest.model;

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
 * 楼层
 *
 * @author Anbang713
 * @create 2020/3/15
 */
@Data
@Entity
@Table(name = "invest_floor")
@ApiModel(description = "楼层")
public class Floor implements IsEntity {

    @Id
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "项目uuid", dataType = "String")
    private String storeUuid;

    @NotBlank
    @ApiModelProperty(value = "楼宇uuid", dataType = "String")
    private String buildingUuid;

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
    private Store store;
    @Transient
    private Building building;
    @Transient
    private List<OperationLog> operationLogs = new ArrayList<>();
}