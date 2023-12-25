package org.springblade.web.utils;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import org.springblade.model.DeptHModel;

import org.springblade.web.model.ContractTrade;
import org.springblade.web.model.DetailModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MarketUtils {


	public static Long indexTime=0L;
	public static Map<String,Boolean> subKlineFlagMap=new HashMap<>();
	public static Map<String,Boolean> subFlagMap=new HashMap<>();


	public static Map<String,Object> indexDataMap=new HashMap<>();//首页
	public static Map<String, DeptHModel> dhcpHDataMap=new HashMap<>();//dhcpH
	public static Map<String, CircularFifoQueue<ContractTrade>> tradeHDataMap=new HashMap<>();//成交明细

	//获取涨跌幅
	public static BigDecimal getZdf(DetailModel detailModel){
		try{
			BigDecimal zdf = detailModel.getClose().subtract(detailModel.getOpen()).divide(detailModel.getOpen(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L));
			return zdf;
		}catch (Exception e){

		}
		return BigDecimal.ZERO;
	}
}
