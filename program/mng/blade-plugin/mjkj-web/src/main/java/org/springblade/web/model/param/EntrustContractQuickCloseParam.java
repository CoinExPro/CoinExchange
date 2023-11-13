package org.springblade.web.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 委托-限价委托(合约)-平仓
 */
@Data
public class EntrustContractQuickCloseParam {
	String type;//类型  ubw_quick=U本位闪电平仓  bbw_quick=U本位闪电平仓
	String concatCoinId;//交易对id
	String memberId;//会员id
	String contractType;//合约类型（1=U本位，2=币本位）
	String direction;//委托方向 合约的方向（1=开多，2=开空，3=平多，4=平 空）
	String entrustCode;//委托订单
	String orderCode;//订单号
	BigDecimal price;//委托价
	BigDecimal amount;//委托量  多少
	String logContractId;//持仓id
}
