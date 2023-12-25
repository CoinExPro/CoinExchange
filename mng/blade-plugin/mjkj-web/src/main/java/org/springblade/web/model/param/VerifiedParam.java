package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/17 9:40
 */
@Data
public class VerifiedParam implements Serializable {

	@NotBlank
	@ApiModelProperty("国家")
	private String country;
	@NotBlank
	@ApiModelProperty("证件类型")
	private String cardType;
	@NotBlank
	@ApiModelProperty("姓氏")
	private String surname;
	@NotBlank
	@ApiModelProperty("名字")
	private String name;
	@NotBlank
	@ApiModelProperty("身份证号")
	private String cardCode;
	@NotBlank
	@ApiModelProperty("证件正面")
	private String cardFront;
	@NotBlank
	@ApiModelProperty("证件反面")
	private String cardBack;
	@NotBlank
	@ApiModelProperty("证件手持")
	private String cardHand;


}
