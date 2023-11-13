package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
public class CoinOrderParam implements Serializable {

	/**
	 * 资金密码
	 */
	@NotNull
	@ApiModelProperty(value = "资金密码")
	private String payPwd;

    /**
     * 类型（1=买入 2=卖出）
     */
    @NotNull
    @ApiModelProperty(value = "类型（1=买入 2=卖出）")
    private String type;

	/**
	 * 服务类型
	 */
	@NotNull
	@ApiModelProperty(value = "1：平台 2：商家")
	private String serviceType;


	/**
	 * 收/付款方式
     */
    @ApiModelProperty(value = "收/付款方式id")
    private String payMethodId;

	/**
	 * 承兑商id
	 */
	//@ApiModelProperty(value = "承兑商id")
	private String payServiceId;

	private String memberId;

	/**
	 * 承兑商
	 */
	//@ApiModelProperty(value = "承兑商")
	private String payService;

	/**
	 * 广告id
	 */
	@ApiModelProperty(value = "广告id")
	private Long advertiseId;

    /**
     * 币种数量
     */
    @Positive
    @ApiModelProperty(value = "币种数量")
    private String coinCou;

    private BigDecimal rate;

	/**
	 * 国家id
	 */
	//@ApiModelProperty(value = "国家id")
	private String countryId;

    /**
     * 法币
     */
    //@ApiModelProperty(value = "法币")
    private String fiatCurrency;

    /**
     * 法币金额
     */
    @ApiModelProperty(value = "法币金额")
    private String fiatCurrencyAmount;


	/**
	 * 币种id
	 */
	//@ApiModelProperty(value = "币种id")
    private String coinId;

    /**
     * 币种
     */
    //@ApiModelProperty(value = "币种")
    private String coinSymbol;


    private static final long serialVersionUID = 1L;
}
