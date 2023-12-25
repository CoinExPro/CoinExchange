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
package org.springblade.web.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.WalletConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.util.EmailTemplateUtils;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.entity.*;
import org.springblade.feign.IMjkjWebClient;
import org.springblade.web.config.engine.contract.CoinMatchContractFactory;
import org.springblade.web.config.engine.contract.CoinMatchContractFactoryAll;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchange;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchangeAll;

import org.springblade.web.model.param.CancelExchangeParam;
import org.springblade.web.model.param.EntrustParam;
import org.springblade.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@ApiIgnore
@RestController
@AllArgsConstructor
public class WebClient implements IMjkjWebClient {


	@Override
	public R<Map<String, Object>> getWalletInfo(WalletGetParam param) {
		return null;
	}

	@Override
	public R<Map<String, Object>> getMemberAssert(String memberId) {
		return null;
	}

	@Override
	public R<Map<String, Object>> createWallet(String id) {
		return null;
	}

	@Override
	public R addWallet(AddWalletParam param) {
		return null;
	}

	@Override
	public R subWallet(SubWalletParam param) {
		return null;
	}

	@Override
	public R addFrozenWallet(AddFrozenWalletParam param) {
		return null;
	}

	@Override
	public R subFrozenWallet(SubFrozenWalletParam param) {
		return null;
	}

	@Override
	public R removeFrozenWallet(RemoveWalletParam param) {
		return null;
	}

	@Override
	public R walletBuySell(BuySellWalletParam param) {
		return null;
	}

	@Override
	public Boolean sendYx(SendYxParam param) {
		return null;
	}

	@Override
	public Boolean sendEmail(SendEmailParam param) {
		return null;
	}

	@Override
	public Boolean sendPhone(SendPhoneParam param) {
		return null;
	}

	@Override
	public void orderTimeOutJobHandler() {

	}

	@Override
	public void orderAppealTimeOutJobHandler() {

	}

	@Override
	public void resetMath() {

	}

	@Override
	public void marketProfit() {

	}

	@Override
	public void inviteReward() {

	}

	@Override
	public void contractProfit(String param) {

	}

	@Override
	public void resetMemberLevel(String operateMemberI) {

	}

	@Override
	public R changeOrderStatus(String id, String status) {
		return null;
	}

	@Override
	public void wealthCbsxProfit() {

	}

	@Override
	public void changeCbsxGoodsStatus() {

	}

	@Override
	public void appeal(Appeal appeal) {

	}

	@Override
	public void handleNightFee() {

	}

	@Override
	public R handleContractRg(String logContractId, String type) {
		return null;
	}

	@Override
	public R entrust(EntrustParamApi param) {
		return null;
	}

	@Override
	public R cancelEntrust(CancelExchangeParamDTO param) {
		return null;
	}

	@Override
	public R queryEntrust(String entrustType, String entrustFlag, String symbol) {
		return null;
	}

	@Override
	public R<Map<String, Object>> getApiKey(String apiKey) {
		return null;
	}
}
