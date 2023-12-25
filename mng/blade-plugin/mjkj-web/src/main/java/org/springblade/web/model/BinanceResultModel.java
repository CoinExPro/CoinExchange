package org.springblade.web.model;

import lombok.Data;

import java.util.List;

@Data
public class BinanceResultModel {
	private  String symbol; // 交易对
	private  Long orderId; // 系统的订单ID
	private  String clientOrderId; // 客户自己设置的ID
	private  String price; // 订单价格
	private  String origQty; // 用户设置的原始订单数量
	private  String executedQty; // 交易的订单数量
	private  String cummulativeQuoteQty; // 累计交易的金额
	private  String status; // 订单状态
	private  String side;// 订单方向，买还是卖
	private List<FillModel> fills;//交易详情
	//错误才有
	private Integer code;
	private String msg;

	@Data
	public static class FillModel{
		private String price;//交易的价格
		private String qty;//交易的数量
		private String commission;//手续费金额
		private String commissionAsset;//手续费的币种
		private String tradeId;//交易id
	}

}
