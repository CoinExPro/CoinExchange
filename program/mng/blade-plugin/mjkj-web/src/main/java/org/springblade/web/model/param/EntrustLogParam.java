package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.support.Query;

import java.io.Serializable;
import java.util.List;

/**
 * 现货交易
 */
@Data
public class EntrustLogParam extends Query {
	/**
	 * 币币交易 1.当前委托 2.历史委托 3.成交记录
	 * 杠杆交易 1.当前委托 2.历史委托 3.成交记录 4.当前借币 5.借币记录
	 * 合约交易 1.当前委托 2.计划委托 3.跟踪委托 4.止盈止损 5.历史委托 6.成交记录
	 */
	@ApiModelProperty("币币交易 1.当前委托 2.历史委托 3.成交记录" +
		"杠杆交易 1.当前委托 2.历史委托 3.成交记录 4.当前借币 5.借币记录")
	private String type;
	@ApiModelProperty("xh.币币交易 xh_ggqc.杠杆-全仓 xh_ggzc.杠杆-逐仓  ubw=U本位  bbw=B本位")
	private String currentType;
	@ApiModelProperty("交易对A")
	private String tradingPairA;
	@ApiModelProperty("交易对B")
	private String tradingPairB;
	@ApiModelProperty("委托类型1.普遍委托2.动态止盈止损3.止盈止损")
	private String entrustType;
	@ApiModelProperty("交易类型 方向 1买2卖")
	private String tradeType;
	@ApiModelProperty("杠杆-1.全仓 2逐仓")
	private String leverType;
	@ApiModelProperty("开始时间")
	private String startTime;
	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("隐藏已撤销=-1")
	private String isShowRevoke;

	@ApiModelProperty("交易对名称")
	private String symbolName;

	private String jhwtType;//1=委托中 2=取消和完成
}
