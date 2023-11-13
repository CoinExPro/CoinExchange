package org.springblade.web.model.param;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CbsxBuyOrderParam {
	private String wealthGoodsId;
	private BigDecimal buyCou;
}
