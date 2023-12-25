package org.springblade.web.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 委托-限价委托(合约)
 */
@Data
public class EntrustContractJhwtParam {
	String type;//类型  ubw_jhwt=U本位计划委托  bbw_jhwt=B本位计划委托
	String memberId;//会员id
	String contractCoinId;//交易对id
	String entrustCode;//委托单号
	String orderCode;//订单号
	String direction;//委托方向（1=买入 2=卖出）
	String pattern;//逐仓=1，全仓=2
	String patternType;//并仓=1，分仓=2
	String contractType;//合约类型（1=U本位，2=币本位）
	BigDecimal triggerPrice;//触发价
	BigDecimal price;//委托价
	BigDecimal amount;//委托量
	Boolean refreshFactoryFlag=true;//是否加入引擎
	String serviceType;//业务类型

	String rule;//方向
	String zyzsType;//止盈止损类型 1=止盈，2=止损
}
