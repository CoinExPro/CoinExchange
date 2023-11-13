package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 增加冻结资产
 */
@Data
public class AddFrozenWalletParam {
	private String tableName;//钱包表名
	private String walletId;//钱包id
	private BigDecimal addfrozenBalance;//增加的冻结资产
	private boolean messageRefreshFlag=true;//消息刷新

/*	private BigDecimal feeAmount;//手续费
	private String serviceType;//业务类型
	private String remark;//备注*/
}
