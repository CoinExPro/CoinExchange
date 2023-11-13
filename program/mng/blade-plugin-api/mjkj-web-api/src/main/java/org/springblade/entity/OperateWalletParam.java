package org.springblade.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 增加冻结资产
 */
@Data
public class OperateWalletParam {
	AddWalletParam addWalletParam;//增加可用资产
	SubWalletParam subWalletParam;//减少可用资产
	AddFrozenWalletParam addFrozenWalletParam;//增加可用余额
	SubFrozenWalletParam subFrozenWalletParam;// 减少冻结资产;
	RemoveWalletParam removeWalletParam;//移除冻结资产
}
