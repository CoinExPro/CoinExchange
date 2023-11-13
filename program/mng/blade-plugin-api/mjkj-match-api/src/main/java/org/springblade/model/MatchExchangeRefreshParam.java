package org.springblade.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchExchangeRefreshParam {

	//交易对 BTC/USDT
	private String symbolName;

}
