package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 直推管理
 */
@Data
public class InviteRewardModel {
	private BigDecimal monthUsdt=BigDecimal.ZERO;///本月Usdt
	private BigDecimal yesterdayUsdt=BigDecimal.ZERO;//昨日Usdt
}
