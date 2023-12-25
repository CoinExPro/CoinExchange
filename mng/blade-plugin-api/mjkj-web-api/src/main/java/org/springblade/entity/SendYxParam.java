package org.springblade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/21 18:01
 */
@Data
public class SendYxParam {


	@ApiModelProperty(value = "ip")
	private String ip;

	@ApiModelProperty(value = "账号")
	private String account;


}
