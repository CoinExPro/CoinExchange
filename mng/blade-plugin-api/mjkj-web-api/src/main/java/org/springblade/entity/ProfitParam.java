package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 增加盈利
 */
@Data
public class ProfitParam {
	private String tableName;//钱包表名
	private String walletId;//钱包id
	private BigDecimal addProfit;//增加的资产  负数为减

}
