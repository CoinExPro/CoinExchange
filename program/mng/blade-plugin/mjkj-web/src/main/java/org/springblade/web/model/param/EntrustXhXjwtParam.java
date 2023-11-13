package org.springblade.web.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 委托-限价委托
 */
@Data
public class EntrustXhXjwtParam {
	String type;//类型  xh=现货委托   xh_zyzs=现货-止盈止损 xh_dtzyzs=现货-动态止盈止损 xh_ggzc=杠杠逐仓 xh_ggzc_zyzs=杠杠逐仓-止盈止损
	String exchangeCoinId;//交易对id
	String memberId;//会员id
	String direction;//委托方向（1=买入 2=卖出）
	String entrustCode;//委托订单
	String orderCode;//订单号
	BigDecimal price;//委托价
	BigDecimal amount;//委托量
	boolean sjwt;//是否是市价委托

	String walletTableName;//钱包名称 冻结
	String walletId;//钱包id 冻结
	String exchangeType;//交易类型  xh=现货  zc=逐仓   qc=全仓
}
