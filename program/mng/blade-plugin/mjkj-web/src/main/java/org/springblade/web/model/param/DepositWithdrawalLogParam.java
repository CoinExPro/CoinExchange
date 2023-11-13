package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.mp.support.Query;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/22 16:40
 */
@Data
public class DepositWithdrawalLogParam extends Query {
	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("币id")
	private String coinId;
	@ApiModelProperty("类型")
	private String type;//1=钱包  2=币币 3=杠杠 4=合约  5=市值

}
