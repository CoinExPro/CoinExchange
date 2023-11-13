package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springblade.core.mp.support.Query;

/**
 * Author: @Wai
 * Date: 2022-8-17
 */
@Data
public class AdQueryParam extends Query {
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("类型（1=买入，2=卖出）")
	private Integer type;
	@ApiModelProperty("交易币种id")
	private String coinId;
	@ApiModelProperty("国家id")
	private String countryId;
	@ApiModelProperty("商家id")
	private String merchantId;
	@ApiModelProperty("交易额")
	private String coinCou;
	@ApiModelProperty("状态 0:关闭 1:正常")
	private Integer status;
}
