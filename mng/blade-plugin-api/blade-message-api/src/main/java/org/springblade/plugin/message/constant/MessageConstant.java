package org.springblade.plugin.message.constant;


/**
 * 消息常量
 */
public interface MessageConstant {

	//公共常量
	String socketPublic="socketPublic";

	//消息主题
	String SocketTopic_INDEX="index";//首页行情

	//消息主题
	String SocketTopic_XH_DEPTH="xh_deptH_";//现货-深度
	String SocketTopic_UBW_DEPTH="ubw_deptH_";//U本位-深度
	String SocketTopic_BBW_DEPTH="bbw_deptH_";//币本位-深度
	String SocketTopic_BBWJG_DEPTH="bbwjg_deptH_";//币本位交割-深度

	String SocketTopic_XH_DETAIL="xh_detail_";//市场概要-深度
	String SocketTopic_UBW_DETAIL="ubw_detail_";//市场概要-深度
	String SocketTopic_BBW_DETAIL="bbw_detail_";//市场概要-深度
	String SocketTopic_BBWJG_DETAIL="bbwjg_detail_";//市场概要-深度

	String SocketTopic_XH_TRADE="xh_trade_";//成交明细-深度
	String SocketTopic_UBW_TRADE="ubw_trade_";//成交明细-深度
	String SocketTopic_BBW_TRADE="bbw_trade_";//成交明细-深度
	String SocketTopic_BBWJG_TRADE="bbwjg_trade_";//成交明细-深度

	String SocketTopic_XH_KLINE="xh_kline_";//kline-深度
	String SocketTopic_UBW_KLINE="ubw_kline_";//kline-深度
	String SocketTopic_BBW_KLINE="bbw_kline_";//kline-深度
	String SocketTopic_BBWJG_KLINE="bbwjg_kline_";//kline-深度

	String Sockettopic_ALL_SYMBOL="all_symbol_detail";//kline-深度
}
