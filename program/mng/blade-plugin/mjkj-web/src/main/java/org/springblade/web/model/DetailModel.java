package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailModel {

	/**
	 * 现货=xh  u本位=ubw 币本位=bbw
	 */
	private String type;
	/**
	 * 币种交易对  BTC/USDT
	 */
	private String symbolName;
	/**
	 * 24小时成交量
	 */
	private BigDecimal amount;
	/**
	 * 24小时开盘价
	 */
	private BigDecimal open;
	/**
	 * 最新价
	 */
	private BigDecimal close;
	/**
	 * 24小时最高价
	 */
	private BigDecimal high;
	/**
	 * 24小时成交笔数
	 */
	private BigDecimal count;
	/**
	 * 24小时最低价
	 */
	private BigDecimal low;
	/**
	 * 24小时成交额
	 */
	private BigDecimal vol;
	/**
	 * 涨跌幅
	 */
	private BigDecimal zdf;

	/**
	 * 币种名称
	 */
	private String bzmc;
	/**
	 * 币种图标
	 */
	private String bzicon;
	/**
	 * 成交额
	 */
	private Long cje;
	/**
	 * 成交量
	 */
	private Long cjl;
	/**
	 * 24小时成交笔数
	 */
	private String coinCoinId;

	/**
	 * U本位成交额度
	 */
	private BigDecimal tradeTurnover;
	/**
	 * 主题
	 */
	private String topic;

	/**
	 * 排序
	 */
	private Integer sort;
}
