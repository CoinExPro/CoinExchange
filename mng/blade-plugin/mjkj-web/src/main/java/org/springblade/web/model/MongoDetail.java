package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MongoDetail {
	private String symbol;//币种
	private BigDecimal close;//最新价
	private BigDecimal amount;//24小时成交量
	private BigDecimal open;//24小时开盘价
	private BigDecimal high;//	24小时最高价
	private BigDecimal count;//24小时成交笔数
	private BigDecimal low;//24小时最低价
	private BigDecimal vol;//24小时成交额
	private BigDecimal zdf;//涨跌幅
	private BigDecimal tradeTurnover;//U本位成交额度
	private String contractSize;

	private String base_coin_scale;
	private String coin_scale;
	private String bzicon;
	private String icon;

	private Long updateTime;//更新时间，秒
	/**
	 * 0：默认行情
	 * 1：自定义行情
	 */
	private Integer type;

	/**
	 * 自定义行情 是否已经执行
	 * type=1时候才生效
	 * 0=未执行
	 * 1=已执行
	 */
	private Integer executeFlag;

	//自定义价格id
	private String contractSelfPriceId;
}
