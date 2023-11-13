package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 增加冻结资产
 */
@Data
public class AddWalletParam {
	private String tableName;//钱包表名
	private String walletId;//钱包id
	private BigDecimal addBalance;//增加的资产
	private BigDecimal feeAmount=BigDecimal.ZERO;//手续费
	private String serviceType;//业务类型
	private String remark;//备注

	private BigDecimal borrowBalance=BigDecimal.ZERO;//借入金额

	private String contractSymbolName;//合约交易对

	private boolean messageRefreshFlag=true;//消息刷新
}
