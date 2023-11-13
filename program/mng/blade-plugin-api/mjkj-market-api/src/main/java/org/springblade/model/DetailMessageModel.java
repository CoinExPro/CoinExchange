package org.springblade.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailMessageModel {

	private String contractType;//合约类型
	private String symbolName;//币种交易对  BTC/USDT
	private BigDecimal amount;//24小时成交量
	private BigDecimal open;//24小时开盘价
	private BigDecimal close;//最新价
	private BigDecimal high;//	24小时最高价
	private BigDecimal count;//24小时成交笔数
	private BigDecimal low;//24小时最低价
	private BigDecimal vol;//24小时成交额
	private BigDecimal tradeTurnover;//U本位成交额度
}
