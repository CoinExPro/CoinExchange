package org.springblade.web.model;

import lombok.Data;

import java.util.List;

@Data
public class MarketGoodsModel {
	private String symbolName;//交易对名称
	private String avatar;//头像

	private List<detail> leftGoodsList;//左商品
	private List<detail> rightGoodsList;//右商品

	@Data
	public static class detail{
		private String marketSymbolId;
		private String coinId;
		private String coinSymbol;
		private String periodDay;//天数
		private String profitRate;//收益率
		private String minMoney;//最低投资金额
	}
}
