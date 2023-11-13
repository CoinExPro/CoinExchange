package org.springblade.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.undertow.websockets.client.WebSocketClient;
import org.springblade.cgform.enums.MjkjAeskey;
import org.springblade.config.constant.CoinhouseConfig;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class MjkjUtils {

	public static String OPEN_XH = "xh";
	public static String OPEN_GG = "gg";
	public static String OPEN_HY = "hy";
	public static String OPEN_SZ = "sz";
	public static String OPEN_LC = "lc";
	public static String OPEN_TB = "tb";


	private static final String mjkjSalt = "DZy9AUnI4kQ98Z2et5mTGrRnCMivH5O8";//加密盐

	public static Boolean marketProfitFlag = false;//收益
	public static Boolean contractProfitFlag = false;//收益
	public static Boolean wealthCbsxProfitFlag = false;//存币生息收益


	public static Map<String, String> syncXhDeptMap = new HashMap<>();
	public static Map<String, String> syncXhKlineMap = new HashMap<>();
	public static Map<String, String> syncXhDetailMap = new HashMap<>();
	public static Map<String, String> syncXhTradeMap = new HashMap<>();


	public static Map<String, String> syncBbwjgDeptMap = new HashMap<>();
	public static Map<String, String> syncBbwjgKlineMap = new HashMap<>();
	public static Map<String, String> syncBbwjgDetailMap = new HashMap<>();
	public static Map<String, String> syncBbwjgTradeMap = new HashMap<>();

	public static Map<String, String> syncBbwDeptMap = new HashMap<>();
	public static Map<String, String> syncBbwKlineMap = new HashMap<>();
	public static Map<String, String> syncBbwDetailMap = new HashMap<>();
	public static Map<String, String> syncBbwTradeMap = new HashMap<>();

	public static Map<String, String> syncUbwDeptMap = new HashMap<>();
	public static Map<String, Long> syncUbwKlineMap = new HashMap<>();
	public static Map<String, String> syncUbwDetailMap = new HashMap<>();
	public static Map<String, String> syncUbwTradeMap = new HashMap<>();

	/**
	 * 获取map值
	 * (map的value是单个的数据时使用)
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static String getMap2Str(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return "";
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return "";
		}
		// object -> String
		String value = Func.toStr(o);
		// 返回
		return value;
	}


	/**
	 * 获取map值
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static <T> List<T> getMapAll2List(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return Collections.emptyList();
		}

		Object o = map.get(key);
		// 根据高查条件的key获取value
		if (o instanceof List)
			return (List<T>) o;
		T[] arr = (T[]) Func.toStr(o).replaceAll("[\\[\\]()]", "").split(",");
		return Arrays.asList(arr);
	}


	/**
	 * 获取map值
	 * (map的value是单个的数据时使用)
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static Date getMap2DateTime(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return null;
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return null;
		}
		// object -> String
		String value = Func.toStr(o);
		return DateUtil.parse(value, DateUtil.DATETIME_FORMAT);
	}

	/**
	 * 是否为昨天
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static boolean isYesterday(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return false;
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return false;
		}
		if (o instanceof Date)
			return cn.hutool.core.date.DateUtil.isSameDay(cn.hutool.core.date.DateUtil.yesterday(), (Date) o);
		// object -> String
		String value = Func.toStr(o);
		return cn.hutool.core.date.DateUtil.isSameDay(cn.hutool.core.date.DateUtil.yesterday(), DateUtil.parse(value, DateUtil.DATETIME_FORMAT));
	}

	/**
	 * 指定日期是否为三天内
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static boolean isBetweenThirdDays(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return false;
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return false;
		}
		if (o instanceof Date)
			return cn.hutool.core.date.DateUtil.betweenDay(cn.hutool.core.date.DateUtil.date(), (Date) o, false) < 3;
		// object -> String
		String value = Func.toStr(o);
		return cn.hutool.core.date.DateUtil.betweenDay(cn.hutool.core.date.DateUtil.date(), DateUtil.parse(value, DateUtil.DATETIME_FORMAT), false) < 3;
	}

	/**
	 * 指定日期是否为一周内
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static boolean isBetweenWeek(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return false;
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return false;
		}
		if (o instanceof Date)
			return cn.hutool.core.date.DateUtil.betweenWeek(cn.hutool.core.date.DateUtil.date(), (Date) o, false) == 0;
		// object -> String
		String value = Func.toStr(o);
		return cn.hutool.core.date.DateUtil.betweenWeek(cn.hutool.core.date.DateUtil.date(), DateUtil.parse(value, DateUtil.DATETIME_FORMAT), false) == 0;
	}

	/**
	 * 指定日期是否为一月内
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static boolean isBetweenMonth(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return false;
		}
		// 根据高查条件的key获取value
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return false;
		}
		if (o instanceof Date)
			return cn.hutool.core.date.DateUtil.betweenMonth(cn.hutool.core.date.DateUtil.date(), (Date) o, false) == 0;
		// object -> String
		String value = Func.toStr(o);
		return cn.hutool.core.date.DateUtil.betweenMonth(cn.hutool.core.date.DateUtil.date(), DateUtil.parse(value, DateUtil.DATETIME_FORMAT), false) == 0;
	}

	/**
	 * 获取map值
	 * (map的value是单个的数据时使用)
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static boolean contains(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return false;
		}
		if (map.containsKey(key) && !Func.toStr(map.get(key)).equals(""))
			return true;
		return false;
	}

	/**
	 * 获取map值
	 * (map的value是单个的数据时使用)
	 *
	 * @param map 查询封装的一条数据
	 * @param key 高查条件的key
	 * @return
	 */
	public static BigDecimal getMap2BigD(Map<String, Object> map, String key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return BigDecimal.ZERO;
		}
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return BigDecimal.ZERO;
		}
		// object -> BigDecimal
		BigDecimal bigDecimal = new BigDecimal(Func.toStr(o));
		// 返回
		return bigDecimal;
	}

	public static BigDecimal getBigDSafe(Map<?, BigDecimal> map, Object key) {
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return BigDecimal.ZERO;
		}
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return BigDecimal.ZERO;
		}
		return map.get(key);
	}

	public static BigDecimal getBigDecimalDefaultOne(Map<?, Object> map, Object key) {
		if (Func.isEmpty(key) || Func.isEmpty(map)) {
			return BigDecimal.ONE;
		}
		Object o = map.get(key);
		if (Func.isEmpty(o)) {
			return BigDecimal.ONE;
		}
		// object -> BigDecimal
		BigDecimal bigDecimal = new BigDecimal(Func.toStr(o));
		// 返回
		return bigDecimal;
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Long getMap2Long(Map<String, Object> map, String key) {
		String str = getMap2Str(map, key);
		if (Func.isEmpty(str)) {
			return -1L;
		}
		return Func.toLong(str);
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Double getMap2Double(Map<String, Object> map, String key) {
		String str = getMap2Str(map, key);
		if (Func.isEmpty(str)) {
			return -1D;
		}
		return Func.toDouble(str);
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Integer getMap2Integer(Map<String, Object> map, String key) {
		String str = getMap2Str(map, key);
		if (Func.isEmpty(str)) {
			return -1;
		}
		return Func.toInt(str);
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Map<String, Object> getMap2Map(Map<String, Object> map, String key) {
		Map<String, Object> map2 = new HashMap<>();
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map) || Func.isEmpty(map.get(key))) {
			return map2;
		}
		map2 = (Map<String, Object>) map.get(key);
		return map2;
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */

	public static Map<String, Object> getMapJson2Map(Map<String, Object> map, String key) {
		if (Func.isEmpty(key) || Func.isEmpty(map) || Func.isEmpty(map.get(key))) {
			return new HashMap<String, Object>();
		}
		return JsonUtil.parse(getMap2Str(map, key), Map.class);
		// 参数校验:
	}

	public static List<Map<String, Object>> getMapJson2List(Map<String, Object> map, String key) {
		if (Func.isEmpty(key) || Func.isEmpty(map) || Func.isEmpty(map.get(key))) {
			return new ArrayList<>();
		}
		return JsonUtil.parse(getMap2Str(map, key), ArrayList.class);
		// 参数校验:
	}

	/**
	 * 获取map值
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static List<Map<String, Object>> getMap2List1(Map<String, Object> map, String key) {
		List<Map<String, Object>> list = new ArrayList<>();
		// 参数校验:
		if (Func.isEmpty(key) || Func.isEmpty(map) || Func.isEmpty(map.get(key))) {
			return list;
		}
		list = (List<Map<String, Object>>) map.get(key);
		return list;
	}

	/**
	 * 获取分页信息
	 *
	 * @param params
	 * @return 为空则不分页
	 */
	public static Page getPage(Map<String, Object> params) {

		Integer pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
		Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());

		if (pageSzie == -521) {
			Page<Map<String, Object>> page = new Page<>(pageNo, pageSzie);
			return page;
		}
		Page<Map<String, Object>> page = new Page<>(pageNo, pageSzie);
		return page;
	}

	/**
	 * 设置分页返回结果
	 *
	 * @param params
	 * @param ipage
	 */
	public static void setPageResult(Map<String, Object> params, IPage ipage) {
		params.put("dataTotal", ipage.getTotal());
		params.put("dataRecords", ipage.getRecords());
	}

	public static void setPageResult(Map<String, Object> params, IPage ipage, Map<String, Object> otherMap) {
		params.put("dataTotal", ipage.getTotal());
		params.put("dataRecords", ipage.getRecords());
		params.put("dataOther", otherMap);
	}

	/**
	 * 封装查询条件
	 *
	 * @param obj 字段，值 ...
	 * @return
	 */
	public static QueryWrapper queryWrapper(Object... obj) {
		if (obj.length % 2 != 0)
			throw new RuntimeException("参数个数错误！");
		QueryWrapper queryWrapper = new QueryWrapper();
		for (int i = 0; i < obj.length / 2; i++) {
			queryWrapper.eq(obj[i * 2], obj[(i * 2 + 1)]);
		}
		return queryWrapper;
	}

	/**
	 * 使用stream流去比较两个数组是否相等
	 * 方法5
	 */
	public static boolean checkDiffrent4(List<String> list, List<String> list1) {
		long st = System.nanoTime();
		/** 先将集合转成stream流进行排序然后转成字符串进行比较 */
		return list.stream().sorted().collect(Collectors.joining())
			.equals(list1.stream().sorted().collect(Collectors.joining()));
	}

	/**
	 * 计算价格
	 *
	 * @param quoteSetting
	 * @return
	 */
	public static BigDecimal calcAmount(JSONArray quoteSetting, BigDecimal amount) {
		if (null == quoteSetting) {
			return amount;
		}
		BigDecimal coefficient = quoteSetting.getBigDecimal(1);
		if (null == coefficient) {
			return amount;
		}
		BigDecimal calcAmount = amount.multiply(coefficient).setScale(6, BigDecimal.ROUND_UP);
		if (calcAmount.compareTo(new BigDecimal("0")) == 0) {
			return amount;
		}
		return calcAmount.stripTrailingZeros();
	}

	public static String getId(JSONObject jsonObject) {
		String id = "";
		if (jsonObject.containsKey("ch")) {
			id = jsonObject.getString("ch");
			if (id == null || id.split("\\.").length < 3) {
				return null;
			}
		}
		if (jsonObject.containsKey("rep")) {
			id = jsonObject.getString("rep");
			if (id == null || id.split("\\.").length < 3) {
				return null;
			}
		}
		if (id.equals("")) {
			return null;
		}
		return id;
	}

	public static long getSecond(String period) {
		long second = 0;
		if (period.equals("1min")) {
			second = 1 * 60L;
		} else if (period.equals("5min")) {
			second = 5 * 60L;
		} else if (period.equals("15min")) {
			second = 15 * 60L;
		} else if (period.equals("30min")) {
			second = 30 * 60L;
		} else if (period.equals("60min")) {
			second = 60 * 60L;
		} else if (period.equals("4hour")) {
			second = 4 * 3600L;
		} else if (period.equals("1day")) {
			second = 24 * 3600L;
		} else if (period.equals("1mon")) {
			second = 30 * 24 * 3600L;
		} else if (period.equals("1week")) {
			second = 7 * 24 * 3600L;
		}
		return second;
	}

	//获取7天后
	public static Date get7Day(Date now) {
		String format = DateUtil.format(now, "yyyy-MM-dd HH");
		format = format + ":00:00";
		Date newDate = DateUtil.parse(format, DateUtil.PATTERN_DATETIME);
		Date result = DateUtil.plusDays(newDate, 7);
		return result;
	}

	public static long getTimeSubHour(Date startDate, Date endDate) {
		//String startDateStr = DateUtil.format(startDate, "yyyy-MM-dd HH");
		//startDateStr=startDateStr+":00:00";
		//startDate = DateUtil.parse(startDateStr, "yyyy-MM-dd HH:mm:ss");

		long diff = endDate.getTime() - startDate.getTime();
		long hours = diff / 60 / 60 / 1000;
		hours = hours + 1;//向上取整
		return hours;
	}

	/**
	 * 转为树形结构
	 *
	 * @param items
	 * @param idKey       转换id键名
	 * @param parentIdKey 父id键名
	 * @return
	 */
	public static List<Map<String, Object>> forestNodeManager(List<Map<String, Object>> items, String idKey, String parentIdKey) {
		ImmutableMap<Long, Map<String, Object>> nodeMap = Maps.uniqueIndex(items, m -> getMap2Long(m, "id"));
		Map<Long, Object> parentIdMap = Maps.newHashMap();
		items.forEach((forestNode) -> {
			if (getMap2Long(forestNode, parentIdKey) != 0L) {
				Map<String, Object> node = nodeMap.get(getMap2Long(forestNode, parentIdKey));
				if (node != null) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) node.get("children");
					if (Func.isEmpty(list)) {
						list = new ArrayList<>();
						list.add(forestNode);
						node.put("children", list);
					} else
						list.add(forestNode);
				} else {
					parentIdMap.put(getMap2Long(forestNode, idKey), "");
				}
			}
		});
		List<Map<String, Object>> roots = new ArrayList();
		nodeMap.forEach((key, node) -> {
			if (getMap2Long(node, parentIdKey) == 0L || parentIdMap.containsKey(getMap2Long(node, idKey))) {
				roots.add(node);
			}
		});
		return roots;
	}

	public static Map<String, Map<String, Object>> list2Map(List<Map<String, Object>> dataList, String key) {
		Map<String, Map<String, Object>> resultMap = new HashMap<>();
		if (Func.isNotEmpty(dataList)) {
			dataList.forEach(map -> {
				resultMap.put(Func.toStr(map.get(key)), map);
			});
		}
		return resultMap;
	}


	public static Map<String, Map<String, Object>> list2Map2UpperCase(List<Map<String, Object>> dataList, String key) {
		Map<String, Map<String, Object>> resultMap = new HashMap<>();
		if (Func.isNotEmpty(dataList)) {
			dataList.forEach(map -> {
				resultMap.put(Func.toStr(map.get(key)).toUpperCase(), map);
			});
		}
		return resultMap;
	}

	/**
	 * 获取加密key
	 *
	 * @return
	 */
	public static String getSalt() {
		String key = AesUtil.genAesKey();
		System.out.println(key);
		return key;
	}

	//数据库加密
	public static String getDbJiaMi(String str) {
		String encrypt = AesUtil.encryptToHex(str, MjkjAeskey.db_aes_key);
		return encrypt;
	}

	//数据库解密
	public static String getDbJieMi(String str) {
		String encrypt = AesUtil.decryptFormHexToString(str, MjkjAeskey.db_aes_key);
		return encrypt;
	}

	/**
	 * 自定义加密
	 *
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String mjkjEncrypt(String str, String salt) {
		String str64 = Base64Util.encode(str);
		String encrypt1 = AesUtil.encryptToHex(str64, mjkjSalt);
		String encrypt2 = AesUtil.encryptToHex(encrypt1, salt);
		String encrypt3 = AesUtil.encryptToHex(encrypt2, CoinhouseConfig.getSalt());
		return encrypt3;
	}

	/**
	 * 自定义解密
	 *
	 * @return
	 */
	public static String mjkjDecrypt(String encrypt3, String salt) {
		String encrypt2 = AesUtil.decryptFormHexToString(encrypt3, CoinhouseConfig.getSalt());
		String encrypt1 = AesUtil.decryptFormHexToString(encrypt2, salt);
		String str64 = AesUtil.decryptFormHexToString(encrypt1, mjkjSalt);
		String str = Base64Util.decode(str64);
		return str;
	}

	//获取手机缓存Key
	public static String getPhoneRedisKey(String type, String phone, String areaCode) {
		String redisKey = "SMS:PHONE_" + type + "_areaCode_" + areaCode + "_phone_" + phone;
		return redisKey;
	}

	//获取验证码key
	public static String getEmailRedisKey(String type, String email) {
		String redisKey = "SMS:EMAIL_" + type + "_email_" + email;
		return redisKey;
	}

	public static boolean getMap2Boolean(Map<String, Object> map, String key) {
		if (Func.isEmpty(map))
			return false;
		if (!map.containsKey(key))
			return false;
		return Func.toBoolean(map.get(key));
	}

	public static <T> List<T> mergeList(List<T> dataList, List<T> dataList2) {
		ArrayList<T> list = new ArrayList<>();
		if (Func.isNotEmpty(dataList))
			list.addAll(dataList);
		if (Func.isNotEmpty(dataList2))
			list.addAll(dataList2);
		return list;
	}

	/**
	 * 获取小数位
	 *
	 * @param num
	 * @return
	 */
	public static Integer getDecimalNum(BigDecimal num) {
		String str = num.stripTrailingZeros().toPlainString();
		if (str.indexOf(".") != -1) {//是小数
			String subStr = str.split("\\.")[1];
			return subStr.length();
		} else {
			return 0;
		}
	}
	//获取订阅参数
	public static Map<String,List<String>> getBinanceSubParamMap(List<String> list){
		//处理同类的
		Map<String,List<String>> symbolMap=new HashMap<>();
		for (String symbol:list){
			String symbolName = symbol.split("@")[0];
			List<String> subList = symbolMap.get(symbolName);
			if(Func.isEmpty(subList)){
				subList=new ArrayList<>();
			}
			subList.add(symbol);
			symbolMap.put(symbolName,subList);
		}
		return symbolMap;
	}

/*
	public static void main(String[] args) throws Exception {


		Date date = new Date();//当前时间
		String otherTime = "2022-06-15 17:25:00";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = sf.parse(otherTime);

		System.out.println(getTimeSubHour(time,date));
	}
*/


}
