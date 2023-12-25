package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 委托参数表
 */
@Data
public class EntrustContractParam implements Serializable {

	@ApiModelProperty(value = "交易对id（BTC/USDT）")
	private String exchangeCoinStr;

	@ApiModelProperty(value = "委托价")
	private BigDecimal price;
	/**
	 * 1=开仓-做多 相当于买入
	 * 2=开仓-做空 相当于买入
	 * 3=平仓-平多 相当于卖出
	 * 4=平仓-平空 相当于卖出
	 */
	@ApiModelProperty(value = "方向")
	private String direction;

	@ApiModelProperty(value = "数量(即BTC数量)")//合约为倍数后的
	private BigDecimal amount;

	@ApiModelProperty(value = "触发价-止盈止损")
	private BigDecimal triggerPrice;


	@ApiModelProperty(value = "止盈价-动态止盈止损")
	private BigDecimal takeProfitPrice;

	@ApiModelProperty(value = "止损价-动态止盈止损")
	private BigDecimal stopLossPrice;


	@ApiModelProperty(value = "逐仓=1，全仓=2")
	private Integer pattern;
	@ApiModelProperty(value = "并仓=1，分仓=2")
	private Integer patternType;
	@ApiModelProperty(value = "合约类型（1=U本位，2=币本位）")
	private Integer contractType;

	@ApiModelProperty(value = "平仓订单号")
	private String closeLogContractOrderCode;

	@ApiModelProperty(value = "一键平仓交易对")
	private List<String> oneKeySymbolNameList;

	@ApiModelProperty(value = "业务类型：jhwt=计划委托  zyzs=止盈止损")
	private String serviceType;

	@ApiModelProperty(value = "止盈止损类型：1=止盈，2=止损")
	private String zyzsType;
	//合约相关--------------E


}
