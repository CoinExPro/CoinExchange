package org.springblade.web.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TradePwdVerificationParam {
	@NotBlank
	private String tradePwd;
}
