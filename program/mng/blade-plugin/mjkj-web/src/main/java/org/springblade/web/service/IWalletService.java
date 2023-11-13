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


import org.springblade.entity.BuySellWalletParam;
import org.springblade.entity.OperateWalletParam;
import org.springblade.web.model.param.TransParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 钱包服务
 *
 *
 */
public interface IWalletService {

	//获取钱包资产，（杠杠账户-逐仓 比较特殊）
	Map<String,Object> getWalletMap(String type,String memberId, String coinId);


}
