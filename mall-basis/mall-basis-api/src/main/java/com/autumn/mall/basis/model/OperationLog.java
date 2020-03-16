/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.model
 * 文件名: OperationLog
 * 日期: 2020/3/14 20:59
 * 说明:
 */
package com.autumn.mall.basis.model;

import com.autumn.mall.commons.model.Admin;
import com.autumn.mall.commons.model.IsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 操作日志实体
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@Document(value = "basis_operationlog")
@ApiModel(description = "操作日志")
public class OperationLog implements IsEntity {

    @MongoId
    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "实体标识", dataType = "String")
    private String entityKey;

    @NotBlank
    @ApiModelProperty(value = "操作时间", dataType = "Date")
    private Date time;

    @NotBlank
    @ApiModelProperty(value = "操作人", dataType = "Admin")
    private Admin operator;

    @ApiModelProperty(value = "操作原因", dataType = "String")
    private String reason;
}