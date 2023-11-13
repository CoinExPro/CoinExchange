package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 买入卖出
 */
@Data
public class BuySellWalletParam {

	//卖出
	private RemoveWalletParam sellParam;

	//买入
	private AddWalletParam buyParam;
}
