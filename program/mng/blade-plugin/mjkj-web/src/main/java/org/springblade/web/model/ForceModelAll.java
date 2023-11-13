package org.springblade.web.model;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 逐仓
 */@Data
public class ForceModelAll {
 	private String memberId;//会员id
	private boolean resetFlag=false;//true 需要重置  false 不需要

	private Long lastCheckTime=0L;//上次交易时间
	private BigDecimal lastFxl=BigDecimal.ZERO;//上次风险率

	private BigDecimal totalBjUsdt;//总本金
	private BigDecimal totalYjUsdt;//总已借
	private BigDecimal totalLxUsdt;//总利息


	Map<String,BalanceModel> bjMap;//本金
	Map<String,BorrowModel> yjMap;//已借
	Map<String,InterestModel> lxMap;//利息

	//本金
	@Data
	public static class BalanceModel{
		BigDecimal balance=BigDecimal.ZERO;//总仓位
		BigDecimal balanceUsdt=BigDecimal.ZERO;//总仓位转为usdt
	}
	//已借
	@Data
	public static class BorrowModel{
		BigDecimal balance=BigDecimal.ZERO;//总仓位
		BigDecimal balanceUsdt=BigDecimal.ZERO;//总仓位转为usdt
	}
	//利息
	@Data
	public static class InterestModel{
		BigDecimal balance=BigDecimal.ZERO;//总仓位
		BigDecimal balanceUsdt=BigDecimal.ZERO;//总仓位转为usdt
	}
}
