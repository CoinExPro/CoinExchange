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


import org.springblade.config.exception.BusinessException;
import org.springblade.web.model.EntrustModel;
import org.springblade.web.model.param.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 *
 */
public interface IWebService {

	//获取语言
	String getLanguage(String code);

	//获取语言
	String getLanguage(String lang,String code);

	void handleEntrust(List<EntrustModel> entrustList, BigDecimal nowPrice, String symbolName);

	//获取会员id
	String getMemberId();

	//获取会员id
	Map<String,Object> getMemberData();

	// 账号校验绑定
	Boolean accountVerificationBinding(AccountBindingParam param) throws BusinessException;
	// 查询登录日志
	List<Map<String, Object>> getLoginLog();
	// 查询登录设备
	List<Map<String, Object>> getLoginDevice();
	// 删除登录设备
	Boolean deleteDevice(String id);
	// 修改登录免认证
	Boolean updateDevice(AccountBindingParam param) throws BusinessException;
	// 实名认证
    Boolean verified(VerifiedParam param) throws BusinessException;
	// Google验证
	Map<String, Object> getGoogleVerification(String type);
	// Google 绑定/重置
	Boolean googleVerificationBindingOrReset(String type, String code) throws BusinessException;
	// 资金密码
	void tradePwd(TradePwdParam param) throws BusinessException;
	// 钓鱼码
	void antiCode(AntiCodeParam param) throws BusinessException;
	// 币转换BTC
	BigDecimal coinConversion(String coinSymbol,BigDecimal amount);

	//添加收款信息
	void payment(PaymentParam paymentParam) throws BusinessException;

	//获取收款信息
	List<Map<String, Object>> getPayment() throws BusinessException;

	//会员认证商家
	void auditBusiness(Integer codeType, String code, String name, String email, String coinSymbol, String coinCou, Integer status);

	//获取用户推广信息
	List<Map<String, Object>> getMemeberTgInfo() throws BusinessException;

	//获取用户推广详情
	List<Map<String, Object>> getMemeberTgList(String type) throws BusinessException;

	//二次验证 1手机 2邮箱 3google 4资金密码
	public void validation(Integer codeType, String code);

	Map<String, Map<String, Object>> getCoinMap();

	Map<String, Map<String, Object>> getFiatMap();

	Map<String, Map<String, Object>> getPaymentMap();

	//获取区号
	String getAreaCode(String phone);

	void tradePwdVerification(TradePwdVerificationParam param);

	/**
	 * 校验资金密码
	 * @return true 错误   false 正确
	 */
	Boolean tradePwdVerification();

	//根据交易对获取现货模式（1=走货模式 2=撮合模式）
	String getXhExchangeModel(String symbolName);
}
