package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Author: @Wai
 * Date: 2022-8-17
 */
@Data
public class AdvertiseParam {

	@ApiModelProperty("id")
	private Long id;

	@ApiModelProperty("类型（1=买入，2=卖出）")
	@NotNull
	private Integer direction;

	@ApiModelProperty("交易币种id")
	@NotBlank
	private String coinId;

	@ApiModelProperty("国家id")
	@NotBlank
	private String countryId;

	@ApiModelProperty("交易数量")
	@NotNull
	@DecimalMin(value = "0")
	private BigDecimal coinCou;

	@ApiModelProperty("兑率类型(1:固定价格 2:百分比)")
	@NotNull
	private Integer rateType;

	@ApiModelProperty("兑换率")
	@NotNull
	private String rate;

	@ApiModelProperty("支持的支付方式")
	private String supportMethodList;

	@ApiModelProperty("最小交易额")
	@NotNull
	@DecimalMin(value = "0")
	private BigDecimal minTransaction;

	@ApiModelProperty("最大交易额")
	@DecimalMin(value = "0")
	private BigDecimal maxTransaction;

	@ApiModelProperty("账户")
	private String account;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("开户行")
	private String bank;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty("是否删除")
	private Integer isDeleted;

	@ApiModelProperty("备注")
	private String remarks;

}
