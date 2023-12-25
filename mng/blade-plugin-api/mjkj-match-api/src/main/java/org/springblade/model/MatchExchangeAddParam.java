package org.springblade.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchExchangeAddParam {

    //用户id
    private String memberId;

    //交易类型  buy=买入  sell=卖出
    private String exchangeType;

    /**
     * 交易对 BTC/USDT
     */
    private String symbolName;

    //委托id
    private String entrustId;

    //委托类型：xh=现货
    private String entrustType;

    //类型：0=委托价  1=市价
    private String type;

	//委托价  -1为市价
	private BigDecimal price;


	//委托数量
	private BigDecimal num;
}
