package org.springblade.plugin.message.model;

import lombok.Data;

/**
 * 首页行情
 */
@Data
public class IndexHqModel {
	private String symbol;
	private String  bzmc;//币种名称
	private String  bzicon;//币种图标
	private String jg;//价格
	private String zdf;//涨跌幅
	private String cjl;//成交量
	private String cje;//成交额
}
