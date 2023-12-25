package org.springblade.web.model.param;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 合约-保证金
 */
@Data
public class ContractBondParam {
	private String logContractId;//合约id
	private String type;//add   sub
	private BigDecimal amount;//数量
}
