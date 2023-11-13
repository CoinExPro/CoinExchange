package org.springblade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Author: @Wai
 * Date: 2022-10-10
 */
@Data
public class Appeal {
	/**
	 * id
	 */
	@ApiModelProperty("id")
	private String id;

	/**
	 * 订单id
	 */
	@ApiModelProperty("订单id")
	@NotNull
	private Long orderId;

	/**
	 * 申述人id
	 */
	@ApiModelProperty("申述人id")
	private Long initiatorId;

	/**
	 * 被申述人id
	 */
	@ApiModelProperty("被申述人id")
	private Long associateId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型(1:申述 2:协商一致 3:协商无果)")
	@NotNull
	private Integer type;

	/**
	 * 原因
	 */
	@ApiModelProperty("原因")
	private String reason;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	@Size(max = 500)
	private String description;

	/**
	 * 凭证
	 */
	@ApiModelProperty("证明材料")
	private String proof;

	/**
	 * 联系人
	 */
	@ApiModelProperty("联系人")
	private String contact;

	/**
	 * 电话
	 */
	@ApiModelProperty("电话")
	private String phone;
}
