package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 减少冻结资产
 */
@Data
public class SubWalletParam {
	private String tableName;//钱包表名
	private String walletId;//钱包id
	private BigDecimal subBalance;//减少的资产
	private BigDecimal feeAmount=BigDecimal.ZERO;//手续费
	private String serviceType;//业务类型
	private String remark;//备注

	private BigDecimal borrowBalance=BigDecimal.ZERO;//借入金额
	private BigDecimal subBorrowBalance=BigDecimal.ZERO;//减少借入金额

	private String contractSymbolName;

	private boolean messageRefreshFlag=true;//消息刷新

}
