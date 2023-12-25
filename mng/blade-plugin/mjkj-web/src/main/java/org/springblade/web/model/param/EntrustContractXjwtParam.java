package org.springblade.web.model.param;

import lombok.Data;
import org.springblade.web.utils.GeneratorUtil;

import java.math.BigDecimal;

/**
 * 委托-限价委托(合约)
 */
@Data
public class EntrustContractXjwtParam {
	String type;//类型  ubw_xjwt=U本位限价委托
	String concatCoinId;//交易对id
	String memberId;//会员id
	Integer pattern;//逐仓=1，全仓=2
	Integer patternType;//并仓=1，分仓=2
	Integer contractType;//合约类型（1=U本位，2=币本位）
	String direction;//委托方向 合约的方向（1=开多，2=开空，3=平多，4=平 空）
	String entrustCode;//委托订单
	String orderCode;//订单号
	BigDecimal price;//委托价
	BigDecimal takeProfitPrice;//止盈价
	BigDecimal stopLossPrice;//止损价
	BigDecimal amount;//委托量  多少
	String walletId;//钱包id 冻结
	BigDecimal totalFrozenBalance;
	Boolean refreshFactoryFlag=true;//是否加入引擎

}
