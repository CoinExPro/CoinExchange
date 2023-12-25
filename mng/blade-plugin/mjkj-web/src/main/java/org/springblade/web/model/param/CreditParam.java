package org.springblade.web.model.param;

import lombok.Data;

import java.util.Map;

@Data
public class CreditParam {
	private String cmd;
	private Map<String,Object> body;
}
