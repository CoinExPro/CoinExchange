package org.springblade.web.model;

import lombok.Data;

import java.util.List;

/**
 * 合约全仓
 */@Data
public class ForceModelContractAll {

	private String memberId;//会员id

	//仓位交易对列表
	private List<String> contractLogSymbolNameList;
}
