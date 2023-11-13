package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Author: @Wai
 * Date: 2022-7-6
 */
@Data
public class AntiCodeParam {
	@ApiModelProperty("钓鱼码")
	@NotBlank(message = "钓鱼码不能为空")
	@Pattern(regexp = "^[0-9A-Za-z\\u4e00-\\u9fa5]{4,20}$",message = "钓鱼码需为4-20位大写，小写字母，数字或者中文字符(不含特殊字符)")
	private String antiCode;
	@ApiModelProperty("短信验证码")
	private String phoneCode;
	@ApiModelProperty("邮箱验证码")
	private String emailCode;
	@ApiModelProperty("google验证码")
	private String googleCode;

}
