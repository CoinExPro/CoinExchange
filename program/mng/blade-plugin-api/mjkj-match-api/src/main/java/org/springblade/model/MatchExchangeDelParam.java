package org.springblade.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchExchangeDelParam {

	//交易类型  buy=买入  sell=卖出
	private String exchangeType;

	//交易对 BTC/USDT
	private String symbolName;

	//委托id
	private String  entrustId;

}
