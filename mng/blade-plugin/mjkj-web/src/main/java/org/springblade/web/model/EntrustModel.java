package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/***
 * 委托列表
 */
@Data
public class EntrustModel {
	private String entrustCode;//委托号
	private String entrustType;//委托类型
	private List<CalculationModel> calculationList;

	@Data
	public static class CalculationModel{
		private String rule;//规则  > >=  = < <=
		private BigDecimal triggerPrice;//触发价格
	}

}
