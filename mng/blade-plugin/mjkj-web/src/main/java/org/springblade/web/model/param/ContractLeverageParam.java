package org.springblade.web.model.param;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 合约
 */
@Data
public class ContractLeverageParam {
	private String symbol;//BTC
	private String type;//ubw  bbw
	private BigDecimal cou;//数量 杠杠倍数
	private String pattern;//逐仓=1，全仓=2
	private String patternType;//并仓=1，分仓=2
}
