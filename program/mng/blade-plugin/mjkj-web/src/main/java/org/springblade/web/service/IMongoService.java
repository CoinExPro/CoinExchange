/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.web.service;


import org.springblade.web.model.KLine;
import org.springblade.web.model.MongoDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * mongodb 服务类
 *
 *
 */
public interface IMongoService {

	//获取所有k线图

	/**
	 *
	 * @param type 类型
	 * @param symbolName 交易对 btc/usdt
	 * @param fromTime  开始时间
	 * @param toTime 结束时间
	 * @param period 时间类型
	 * @return
	 */
	List<KLine> findAllKLine(String type,String symbolName, long fromTime, long toTime, String period);

	//获取最新价格

	/**
	 *
	 * @param type xh   ubw  bbw
	 * @param symbolName BTC/USDT
	 * @return
	 */
	BigDecimal getPriceBySymbol(String type,String symbolName);

	//获取更新时间
	Long getUpdateTimeBySymbol(String type,String symbolName);


	BigDecimal getPriceBySymbol(String symbolName);

	//获取详情
	MongoDetail getPriceBySymbolDetail(String type,String symbolName);
}
