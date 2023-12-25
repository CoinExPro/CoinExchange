package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * 合约逐仓爆仓
 */
@Data
public class ContractForceModel {
	private BigDecimal forcePrice;//爆仓价
	private String contractLogId;//仓位id
	private String direction;//1=开多，2=开空
}
