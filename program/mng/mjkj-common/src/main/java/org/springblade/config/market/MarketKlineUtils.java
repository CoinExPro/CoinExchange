package org.springblade.config.market;


import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.CoinhouseConfig;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * K线相关
 */
@Slf4j
public class MarketKlineUtils {

	private static KlineTimeModel preKlineTimeModel;
	private static String[] PERIOD_HUOBI = {"1min", "5min", "15min", "30min", "60min", "4hour", "1day", "1mon", "1week"};//k线周期
	private static String[] PERIOD_BINANCE = {"1m", "5m", "15m", "30m", "1h", "4h", "1d", "1M", "1w"};//k线周期

	public static String[] getKLinePeriodStr(String jysName){
		if(jysName.equals(MarketConstant.MARKET_JYS_HUOBI)){
			return PERIOD_HUOBI;
		}else if(jysName.equals(MarketConstant.MARKET_JYS_BINANCE)){
			return PERIOD_BINANCE;
		}else{
			return null;
		}
	}

	public static Integer clientId=0;
	/**
	 * 获取最大客户端序号
	 * @return
	 */
	public static synchronized Integer getMaxClientId(){
		++clientId;
		return clientId;
	}

	//获取当前交易所
	public static String getNowJys(){
		return CoinhouseConfig.getMarketJysName();
	}

	//币安转火币
	public static String getHuobiPeriod(String period){
		//中转
		if(Func.equals(period,"1m")){
			period="1min";
		}else if(Func.equals(period,"5m")){
			period="5min";
		}else if(Func.equals(period,"15m")){
			period="15min";
		}else if(Func.equals(period,"30m")){
			period="30min";
		}else if(Func.equals(period,"1h")){
			period="60min";
		}else if(Func.equals(period,"4h")){
			period="4hour";
		}else if(Func.equals(period,"1d")){
			period="1day";
		}else if(Func.equals(period,"1M")){
			period="1mon";
		}else if(Func.equals(period,"1w")){
			period="1week";
		}
		return period;
	}

	//币安转火币
	public static String getBinancePeriod(String period){
		//中转
		if(Func.equals(period,"1min")){
			period="1m";
		}else if(Func.equals(period,"5min")){
			period="5m";
		}else if(Func.equals(period,"15min")){
			period="15m";
		}else if(Func.equals(period,"30min")){
			period="30m";
		}else if(Func.equals(period,"60min")){
			period="1h";
		}else if(Func.equals(period,"4hour")){
			period="4h";
		}else if(Func.equals(period,"1day")){
			period="1d";
		}else if(Func.equals(period,"1mon")){
			period="1M";
		}else if(Func.equals(period,"1week")){
			period="1w";
		}
		return period;
	}

	/**
	 *
	 *
	 * @param type 类型
	 * @param symbolName 交易对
	 * @return
	 */
	public static String getNowUseJys(String type,String symbolName){
		try{

			if(!Func.equals(type.toUpperCase(),MarketConstant.MARKET_XH_TYPE)){//当前不是现货，直接返回默认
				return CoinhouseConfig.getMarketJysName();
			}
			BladeRedis bladeRedis = SpringContextUtils.getBean(BladeRedis.class);
			String redisKey="market:useJys:"+symbolName;

			if(bladeRedis.exists(redisKey)){
				Object result =bladeRedis.get(redisKey);
				return Func.toStr(result);
			}
			IMjkjBaseSqlService mjkjBaseSqlService = SpringContextUtils.getBean(IMjkjBaseSqlService.class);
			Map<String, Object> dataMap = mjkjBaseSqlService.getDataOneByField("coin_coin_exchange", "symbol_name", symbolName);
			String exchangeType = MjkjUtils.getMap2Str(dataMap, "exchange_type");//交易模式  1=走货模式  2=撮合模式
			if(Func.equals(exchangeType,"2")){
				bladeRedis.setEx(redisKey,MarketConstant.MARKET_JYS_COINHOUSE,300L);
				return MarketConstant.MARKET_JYS_COINHOUSE;
			}
			bladeRedis.setEx(redisKey,CoinhouseConfig.getMarketJysName(),300L);
		}catch (Exception e){
			log.info("出错了==========="+e.getMessage());
			e.printStackTrace();
		}

		return CoinhouseConfig.getMarketJysName();
	}

	/**
	 * @param type
	 * @param symbolName BTC-USDT
	 * @param period
	 * @return
	 */
	public static String getKLineMongdbKey(String type, String symbolName, String period) {
		String jys =getNowUseJys(type,symbolName);
		String startTitle="";
		if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_HUOBI)){//火币
			startTitle="huobi";
		}else if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_BINANCE)){//币安
			startTitle="binance";
		}else if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_COINHOUSE)){//coinhouse
			startTitle="coinhouse";
		}

		String symbolNameTmp = symbolName.replaceAll("/", "_");
		String mangodbkey = "";
		//获取key
		if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_XH_TYPE)) {//现货
			mangodbkey = String.format(startTitle+"_xh_kline_%s_%s", symbolNameTmp, period).toLowerCase();//coinhouse_xh_kline_axs_usdt_1d
		} else if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_BBW_TYPE)) {//币本位
			mangodbkey = String.format(startTitle+"_bbw_kline_%s_%s", symbolNameTmp, period).toLowerCase();
		} else if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_UBW_TYPE)) {//U本位
			mangodbkey = String.format(startTitle+"_ubw_kline_%s_%s", symbolNameTmp, period).toLowerCase();
		}
		return mangodbkey;
	}

	/**
	 * @param type
	 * @param symbolName BTC-USDT
	 * @return
	 */
	public static String getDetailMongdbKey(String type, String symbolName) {
		//当前使用的交易所
		String jys =getNowUseJys(type,symbolName);

		String startTitle="";
		if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_HUOBI)){//火币
			startTitle="huobi";
		}else if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_BINANCE)){//币安
			startTitle="binance";
		}else if(Func.equals(jys.toUpperCase(),MarketConstant.MARKET_JYS_COINHOUSE)){//coinhouse
			startTitle="coinhouse";
		}

		//获取Key
		symbolName = symbolName.replaceAll("/", "_");
		String mangodbkey = "";
		if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_XH_TYPE)) {//现货
			mangodbkey = String.format(startTitle+"_xh_detail_%s", symbolName).toLowerCase();
		} else if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_BBW_TYPE)) {//币本位
			mangodbkey = String.format(startTitle+"_bbw_detail_%s", symbolName).toLowerCase();
		} else if (Func.equals(type.toUpperCase(), MarketConstant.MARKET_UBW_TYPE)) {//U本位
			mangodbkey = String.format(startTitle+"_ubw_detail_%s", symbolName).toLowerCase();
		}
		return mangodbkey;
	}

	//获取K线
	public synchronized static KlineTimeModel getKlineTime() {
		Date now = DateUtil.now();
		return getKlineTime(now);
	}
	//获取单曲K线时间
	public synchronized static KlineTimeModel getKlineTime(Date now) {
		Long min1 = get1Min(now);
		if(Func.isNotEmpty(preKlineTimeModel)){//判断分钟是否一样，一样则不需要重新生成
			Long preMin1 = preKlineTimeModel.getMin1();
			if(Func.isNotEmpty(preMin1)){//不为空
				if(preMin1.longValue()==min1.longValue()){
					return preKlineTimeModel;//直接走缓存，不重新计算
				}
			}
		}

		KlineTimeModel model=new KlineTimeModel();
		model.setMin1(min1);
		model.setMin5(get5Min(now));
		model.setMin15(get15Min(now));
		model.setMin30(get30Min(now));
		model.setMin60(get1Hour(now));
		model.setHour4(get4Hour(now));
		model.setDay1(get1Day(now));
		model.setWeek1(get1Week(now));
		model.setMon1(get1Month(now));

		preKlineTimeModel=model;
		return model;
	}

	//获取当前时间
	private static Calendar getCalendar(Date now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		return calendar;
	}

	//获取1分钟k线
	private static Long get1Min(Date now) {
		try{
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取5分钟k线
	private static Long get5Min(Date now) {
		try{
			String mmStr = DateUtil.format(now, "mm");
			int nowmm = Func.toInt(mmStr);
			Integer[] mms = {55, 50, 45, 40, 35, 30, 25, 20, 15, 10, 5, 0};
			for (Integer mm : mms) {
				if (nowmm >= mm) {
					nowmm = mm;
					break;
				}
			}
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.MINUTE, nowmm);
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取15分钟k线
	private static Long get15Min(Date now) {
		try{
			String mmStr = DateUtil.format(now, "mm");
			int nowmm = Func.toInt(mmStr);
			Integer[] mms = {45, 30, 15, 0};
			for (Integer mm : mms) {
				if (nowmm >= mm) {
					nowmm = mm;
					break;
				}
			}
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.MINUTE, nowmm);
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取30分钟k线
	private static Long get30Min(Date now) {
		try{
			String mmStr = DateUtil.format(now, "mm");
			int nowmm = Func.toInt(mmStr);
			Integer[] mms = {30, 0};
			for (Integer mm : mms) {
				if (nowmm >= mm) {
					nowmm = mm;
					break;
				}
			}
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.MINUTE, nowmm);
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取1小时K线
	private static Long get1Hour(Date now) {
		try{
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取4小时k线
	private static Long get4Hour(Date now) {
		try{
			String hhStr = DateUtil.format(now, "HH");
			int nowhh = Func.toInt(hhStr);
			Integer[] hhs = {20, 16, 12, 8, 4, 0};
			for (Integer hh : hhs) {
				if (nowhh >= hh) {
					nowhh = hh;
					break;
				}
			}
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.HOUR_OF_DAY, nowhh);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取1天K线
	private static Long get1Day(Date now) {
		try{
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取1周K线
	private static Long get1Week(Date now) {
		try{
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}

	//获取1月K线
	private static Long get1Month(Date now) {
		try{
			Calendar calendar = getCalendar(now);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			Date time = calendar.getTime();
			Long result = time.getTime() / 1000;
			return result;
		}catch (Exception e){

		}
		return 0L;
	}
}
