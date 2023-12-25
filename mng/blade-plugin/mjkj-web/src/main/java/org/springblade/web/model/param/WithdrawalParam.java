package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Author: @Wai
 * Date: 2022-7-12
 */
@Data
public class WithdrawalParam {

	@ApiModelProperty("币种")
	@NotBlank(message = "不能为空")
	private String coinSymbol;

	@NotBlank(message = "不能为空")
	@ApiModelProperty("地址")
	private String address;

	@NotBlank(message = "不能为空")
	@ApiModelProperty("链路类型")
	private String chainType;

	@ApiModelProperty("备注")
	private String remark;

	@Positive(message = "数量必须大于零")
	@ApiModelProperty("提现数量")
	private BigDecimal amount;
}
