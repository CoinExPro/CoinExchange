package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.support.Query;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/23 11:38
 */
@Data
public class MyBillParam extends Query {


	@ApiModelProperty("币种")
	private String coinId;
	@ApiModelProperty("账单类型")
	private String billsType;//1=钱包账户 2=币币 3=杠杠 4=U本位 5=币本位  6=市值
	@ApiModelProperty("类型")
	private String type;
	@ApiModelProperty("开始时间")
	private String startTime;
	@ApiModelProperty("结束时间")
	private String endTime;
}
