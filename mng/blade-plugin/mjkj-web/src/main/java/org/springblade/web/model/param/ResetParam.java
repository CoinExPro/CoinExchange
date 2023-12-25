package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author: @Wai
 * Date: 2022-8-29
 */
@Data
public class ResetParam {
	@NotBlank
	@ApiModelProperty("账号")
	private String account;

	@NotBlank
	@ApiModelProperty("类型(1:手机 2:邮箱")
	private String type;

	@NotBlank
	@ApiModelProperty("验证码")
	private String code;

	@NotBlank
	@ApiModelProperty("密码")
	private String newPassword;

	@NotBlank
	@ApiModelProperty("确认密码")
	private String confirmPassword;
}
