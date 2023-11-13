package org.springblade.web.model.param;

import lombok.Data;
import org.springblade.core.mp.support.Query;

@Data
public class ProxyChildParam extends Query {
	private String UID;
	private String startDate;
	private String endDate;
}
