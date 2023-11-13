package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取资产列表
 */
@Data
public class WalletGetParam {
	String type;
	String memberId;
	String coinId;

}
