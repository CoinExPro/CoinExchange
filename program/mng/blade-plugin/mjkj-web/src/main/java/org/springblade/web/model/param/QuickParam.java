package org.springblade.web.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
public class QuickParam implements Serializable {
    /**
     * 会员id
     */
    @ApiModelProperty(value = "支付方式id")
    private String memberId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderCode;


    /**
     * 订单类型
     */
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String coinSymbol;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private String cou;

	/**
	 * 单价
	 */
	@ApiModelProperty(value = "单价")
	private String fiatSymbol;

    /**
     * 实付金额
     */
    @ApiModelProperty(value = "实付金额")
    private String  amount;

    /**
     * 详情
     */
    @ApiModelProperty(value = "详情")
    private String info;


    private static final long serialVersionUID = 1L;
}

