package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/17 9:40
 */
@Data
public class AccountBindingParam implements Serializable {

	@ApiModelProperty("账号")
	private String account;
	@ApiModelProperty("账号类型")
	private String accountType;
	@ApiModelProperty("账号验证码")
	private String verificationCode;
	@ApiModelProperty("用途1.校验，2.修改账号，3.解绑")
	private String checkType;
	@ApiModelProperty("区号")
	private String phoneRegion;
	@ApiModelProperty("登录免验证")
	private String loginAulh;
	@ApiModelProperty("设备ID")
	private String deviceId;

}
