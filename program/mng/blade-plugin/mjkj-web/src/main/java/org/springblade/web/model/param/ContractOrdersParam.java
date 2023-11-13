package org.springblade.web.model.param;

import lombok.Data;
import org.springblade.core.mp.support.Query;

@Data
public class ContractOrdersParam extends Query {
	private String contractId;
	private String UID;
	private String pUID;
	private String symbol;
	private String feeSymbol;
	private String startDate;
	private String endDate;
}
