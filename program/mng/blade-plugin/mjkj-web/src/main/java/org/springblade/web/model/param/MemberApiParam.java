package org.springblade.web.model.param;

import lombok.Data;

@Data
public class MemberApiParam {
	private String id;
	private String name;
	private String apiRestrictions;
	private String ipRestrictions;
	private Integer checkType;
	private String code;
}
