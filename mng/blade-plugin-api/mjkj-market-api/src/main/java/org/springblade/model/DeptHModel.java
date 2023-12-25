package org.springblade.model;

import lombok.Data;

import java.util.List;

@Data
public class DeptHModel {
	private List<TradePlateItem> buyItems;//买盘深度
	private List<TradePlateItem> sellItems;//卖盘深度
	private String symbolName;//交易对
	private String topic;//主题

}
