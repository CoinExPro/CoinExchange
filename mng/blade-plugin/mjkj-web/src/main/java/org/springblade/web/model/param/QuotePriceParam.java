package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Author: @Wai
 * Date: 2022-6-24
 */
@Data
public class QuotePriceParam implements Serializable {
	@ApiModelProperty(name = "source",value = "交易币",required = true)
	@NotBlank
	private String source;
	@ApiModelProperty(name = "target",value = "兑换币", required = true)
	private String target;
	@ApiModelProperty(name = "amount",value = "兑换数量")
	private String amount;
	@ApiModelProperty(name = "serviceIds",value = "服务商id")
	private String[] serviceIds;
}
