package org.springblade.config.constant;

/**
 * 钱包
 */
public interface WalletConstant {

	String WALLET_TYPE_SERVICE="service";//服务商钱包


	String WALLET_TYPE_WALLET = "wallet"; //钱包
	String WALLET_TYPE_SPOT="spot";//现货钱包
	String WALLET_TYPE_MARGIN_FIXED="marginFixed";//杠杠钱包-逐仓
	String WALLET_TYPE_MAGIN_ALL="marginAll";//杠杠钱包-全仓
	String WALLET_TYPE_CONTRACT="contract";//合约
	String WALLET_TYPE_MARKET="market";//市值
	String WALLET_TYPE_WEALTH="wealth";//理财
}
