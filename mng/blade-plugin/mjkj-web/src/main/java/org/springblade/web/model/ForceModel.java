package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 逐仓
 */
@Data
public class ForceModel {
	private BigDecimal forcePrice;//爆仓价
	private String memberId;//用户id
}
