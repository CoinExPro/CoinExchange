package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Author: @Wai
 * Date: 2022-7-5
 */
@Data
public class TradePwdParam implements Serializable {
	@ApiModelProperty(value = "资金密码")
	private String tradePwd;
	@ApiModelProperty("登录密码")
	private String loginPwd;
	@ApiModelProperty("邮箱验证码")
	private String emailCode;
	@ApiModelProperty("手机验证码")
	private String phoneCode;
	@ApiModelProperty("谷歌验证码")
	private String totpCode;
	@ApiModelProperty("交易验证类型")
	private String valType;
}
