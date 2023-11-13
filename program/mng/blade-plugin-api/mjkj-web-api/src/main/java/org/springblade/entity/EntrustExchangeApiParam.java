package org.springblade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EntrustExchangeApiParam {

	//类型 xh=现货  ggzc=杠杠逐仓
	private String serviceType;

	@ApiModelProperty(value = "委托价")
	private BigDecimal price;

	@ApiModelProperty(value = "方向（1=买入 2=卖出）")
	private String direction;

	@ApiModelProperty(value = "数量(即BTC数量)")
	private BigDecimal amount;

	@ApiModelProperty(value = "会员id")
	private String memberId;

	@ApiModelProperty(value = "交易对id")
	private String exchangeCoinId;
}
