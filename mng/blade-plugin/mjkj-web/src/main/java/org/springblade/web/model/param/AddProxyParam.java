package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddProxyParam {
	@NotBlank
	@ApiModelProperty("用户id")
	private String memberId;
	@ApiModelProperty("返佣比例")
	private String commissionRate;
}
