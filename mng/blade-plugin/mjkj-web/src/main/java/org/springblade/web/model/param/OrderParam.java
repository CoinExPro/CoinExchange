package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.support.Query;

import java.io.Serializable;

/**
 * Author: @Wai
 * Date: 2022-6-25
 */
@Data
public class OrderParam extends Query implements Serializable {

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "订单编号")
	private String orderCode;

	private String memberId;

	@ApiModelProperty(value = "服务商类型")
	private String serviceType;

	@ApiModelProperty(value = "订单状态")
	private String status;
}
