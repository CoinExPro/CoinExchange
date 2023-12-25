package org.springblade.entity;

import lombok.Data;

/**
 * 获取杠杠逐仓资产列表
 */
@Data
public class WalletGetMarginFixedParam {
	String memberId;
	String exchangeCoinId;//
	String coinId;

}
