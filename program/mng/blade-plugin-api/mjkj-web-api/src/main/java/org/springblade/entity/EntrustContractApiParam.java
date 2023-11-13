package org.springblade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 委托合约
 */
@Data
public class EntrustContractApiParam {

	//类型 ubw=U本位  bbw=币本位
	private String serviceType;

	@ApiModelProperty(value = "委托价")
	private BigDecimal price;

	@ApiModelProperty(value = "方向（1=买入 2=卖出）")
	private String direction;

	@ApiModelProperty(value = "逐仓=1，全仓=2")
	private String pattern;

	@ApiModelProperty(value = "并仓=1，分仓=2")
	private String patternType;

	@ApiModelProperty(value = "合约类型（1=U本位，2=币本位）")
	private String contractType;

	@ApiModelProperty(value = "数量(即BTC数量)")
	private BigDecimal amount;

	@ApiModelProperty(value = "会员id")
	private String memberId;

	@ApiModelProperty(value = "交易对id")
	private String contractCoinId;

	@ApiModelProperty(value = "业务类型:jhwt计划委托  zyzs止盈止损")
	private String jhwtServiceType;
}
