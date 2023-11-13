package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 币安下单校验
 */
@Data
public class BinanceFilters {
	private BigDecimal minPrice;//最小单价
	private BigDecimal maxPrice;//最大单价
	private Integer priceScale;//价格精度

	private BigDecimal minQty;//最小委托数量 btc
	private BigDecimal maxQty;//最大委托数量 btc
	private Integer scale;//精度
}
