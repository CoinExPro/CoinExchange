package org.springblade.web.model;

import lombok.Data;

@Data
public class EntrustApiModel {
	private String symbolName;//交易对 BTC/USDT
	private String type;//类型 add 新增  del 删除
	private String entrustType;//委托类型
	private String entrustCode;// 委托号
	private String calculationMethod;//计算方法

}
