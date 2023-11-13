package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 移除冻结资产
 */
@Data
public class RemoveWalletParam {
	private String tableName;//钱包表名
	private String walletId;//钱包id
	private BigDecimal removefrozenBalance=BigDecimal.ZERO;//移除的资产 总资产
	private BigDecimal addBalance=BigDecimal.ZERO;//增加的可用资产   总移除资产-增加可用资产=剩余移除资产
	private BigDecimal removeBalance=BigDecimal.ZERO;//移除可用资产
	private BigDecimal feeAmount=BigDecimal.ZERO;//手续费
	private String serviceType;//业务类型
	private String remark;//备注

	private String contractSymbolName;
	private boolean messageRefreshFlag=true;//消息刷新

}
