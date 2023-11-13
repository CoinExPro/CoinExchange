package org.springblade.web.model.param;

import lombok.Data;
import org.springblade.web.utils.GeneratorUtil;

import java.math.BigDecimal;

/**
 * 委托-限价委托
 */
@Data
public class EntrustXhZyzsParam {
	String type;//类型  xh=现货委托   xh_zyzs=现货-止盈止损 xh_dtzyzs=现货-动态止盈止损 xh_ggzc=杠杠逐仓 xh_ggzc_zyzs=杠杠逐仓-止盈止损
	String memberId;//会员id
	String exchangeCoinId;//交易对id
	String entrustCode;//委托单号
	String orderCode;//订单号
	String direction;//委托方向（1=买入 2=卖出）

	BigDecimal triggerPrice;//触发价
	BigDecimal price;//委托价
	BigDecimal amount;//委托量

}
