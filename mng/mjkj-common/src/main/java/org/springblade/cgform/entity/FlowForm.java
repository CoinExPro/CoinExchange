/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 流程表单数据实体类
 *
 *
 * @since 2021-07-15
 */
@Data
@TableName("blade_flow_form")
@ApiModel(value = "FlowForm对象", description = "流程表单数据")
public class FlowForm {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;

    /**
     * 流程实例主键
     */
    @ApiModelProperty(value = "流程实例主键")
    private String processInstanceId;


    /**
     * 流程定义主键
     */
    @ApiModelProperty(value = "流程定义主键")
    private String processDefinitionId;

    /**
     * 流程步骤id
     */
    @ApiModelProperty(value = "流程步骤id")
    private String resourceId;
    /**
     * 设计器数据id
     */
    @ApiModelProperty(value = "设计器数据id")
    private Long desformDataId;


    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 流程时间
     */
    private Long flowTime;
}
