package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/***
 * 计算方法
 */
@Data
public class CalculationModel {
	private String rule;//规则  > >=  = < <=
	private BigDecimal triggerPrice;//触发价格
}
