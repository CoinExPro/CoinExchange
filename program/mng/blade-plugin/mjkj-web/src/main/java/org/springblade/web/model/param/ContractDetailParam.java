package org.springblade.web.model.param;

import lombok.Data;

import java.util.List;

@Data
public class ContractDetailParam {
	String type;
	List<String> symbolNameList;
}
