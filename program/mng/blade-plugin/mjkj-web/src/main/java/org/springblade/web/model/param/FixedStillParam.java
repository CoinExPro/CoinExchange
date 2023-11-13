package org.springblade.web.model.param;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 租仓-借款
 */
@Data
public class FixedStillParam {
	private String symbolName;//BTC/USDT
	private String symbol;//BTC
	private BigDecimal cou;//数量
}
