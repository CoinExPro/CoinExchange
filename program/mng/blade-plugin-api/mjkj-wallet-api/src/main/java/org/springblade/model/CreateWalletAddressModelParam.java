package org.springblade.model;

import lombok.Data;

/**
 *创建钱包地址
 */
@Data
public class CreateWalletAddressModelParam {
	//链路类型BTC  ETH  TRC
	String chainType;

	//账户
	String account;

	//密码
	String password;
}
