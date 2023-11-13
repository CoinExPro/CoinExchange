package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 委托参数表
 */
@Data
public class EntrustParam implements Serializable {

	@ApiModelProperty(value = "交易对id（BTC/USDT）")
	private String exchangeCoinStr;

	@ApiModelProperty(value = "委托价")
	private BigDecimal price;

	@ApiModelProperty(value = "方向（1=买入 2=卖出）")// 合约的方向（1=开多，2=开空，3=平多，4=平 空）
	private String direction;

	@ApiModelProperty(value = "数量(即BTC数量)")//合约为倍数后的
	private BigDecimal amount;

	@ApiModelProperty(value = "USDT数量")//合约为倍数后的
	private BigDecimal money;

	@ApiModelProperty(value = "触发价-止盈止损")
	private BigDecimal triggerPrice;


	@ApiModelProperty(value = "止盈价-动态止盈止损")
	private BigDecimal takeProfitPrice;

	@ApiModelProperty(value = "止损价-动态止盈止损")
	private BigDecimal stopLossPrice;

	//合约相关--------------S
	@ApiModelProperty(value = "逐仓=1，全仓=2")
	private Integer pattern;
	@ApiModelProperty(value = "并仓=1，分仓=2")
	private Integer patternType;
	@ApiModelProperty(value = "合约类型（1=U本位，2=币本位）")
	private Integer contractType;
	//合约相关--------------E
}
