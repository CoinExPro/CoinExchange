package org.springblade.config.market;

public interface MarketConstant {
	String MARKET_JYS_LIST[]={"BINANCE"};//交易所列表，和下面对应
	String MARKET_TYPE_LIST[]={"XH","UBW","BBW"};//类型列表，和下面对应 剔除现货，现货用自己模式


	String MARKET_JYS_HUOBI="HUOBI";//交易所，火币(HUOBI)
	String MARKET_JYS_BINANCE="BINANCE";//交易所 币安（BINANCE）
	String MARKET_JYS_COINHOUSE="COINHOUSE";//交易所 coinhouse



	String MARKET_XH_TYPE="XH";//现货
	String MARKET_BBW_TYPE="BBW";//币本位永续合约
	String MARKET_UBW_TYPE="UBW";//U本位合约

	String PERIOD[] = {"1min", "5min", "15min", "30min", "60min", "4hour", "1day", "1mon", "1week"};//k线周期

	//--------------------------------------------
}
