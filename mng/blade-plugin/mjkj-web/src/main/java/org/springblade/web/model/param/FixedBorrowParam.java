package org.springblade.web.model.param;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 租仓-借款
 */
@Data
public class FixedBorrowParam {
	private String symbolName;//BTC/USDT
	private String symbol;//BTC
	private BigDecimal depositAmount;//借款金额
}
