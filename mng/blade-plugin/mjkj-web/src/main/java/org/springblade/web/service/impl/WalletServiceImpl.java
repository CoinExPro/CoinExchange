package org.springblade.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.WalletConstant;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.entity.*;
import org.springblade.plugin.message.feign.IMessageClient;
import org.springblade.plugin.message.model.SocketMsgModel;
import org.springblade.web.config.engine.contract.CoinMatchContractFactory;
import org.springblade.web.config.engine.contract.CoinMatchContractFactoryAll;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchange;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchangeAll;
import org.springblade.web.model.param.TransParam;
import org.springblade.web.service.IMongoService;
import org.springblade.web.service.IWalletService;
import org.springblade.web.service.IWebService;
import org.springblade.web.utils.GeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 钱包服务
 */
@Service
public class WalletServiceImpl implements IWalletService {

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@Autowired
	private IWebService webService;


	@Autowired
	private IMongoService mongoService;

	@Autowired
	private IMessageClient messageClient;

	@Autowired
	private CoinMatchFactoryExchange coinMatchFactoryExchange;

	@Autowired
	private CoinMatchFactoryExchangeAll coinMatchFactoryXhqc;

	@Autowired
	private CoinMatchContractFactory coinMatchContractFactory;

	@Autowired
	private CoinMatchContractFactoryAll coinMatchContractFactoryAll;



	@Override
	public Map<String, Object> getWalletMap(String type, String memberId, String coinId) {
		if(Func.isEmpty(coinId)){
			throw new ServiceException("币种id为空");
		}
		if (Func.isEmpty(type)) {
			type = WalletConstant.WALLET_TYPE_SPOT;
		}
		String marginCode = "";
		String tableName = "";
		if (Func.equals(type, WalletConstant.WALLET_TYPE_SPOT)) {//现货交易
			tableName = "coin_member_wallet_spot";
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_MARGIN_FIXED)) {//杠杠钱包-逐仓
			tableName = "coin_member_wallet_margin_fixed";
			marginCode = GeneratorUtil.getOrderId("MARGIN_FIXED");
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_MAGIN_ALL)) {//杠杠钱包-全仓
			tableName = "coin_member_wallet_margin_all";
			marginCode = GeneratorUtil.getOrderId("MARGIN_ALL");
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_WALLET)) {//用户钱包
			tableName = "coin_member_wallet";
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_SERVICE)) {//商家钱包
			tableName = "coin_service_wallet";
			Map<String, Object> serviceMap = mjkjBaseSqlService.getDataOneByField("coin_pay_service", "member_id", memberId);
			if (Func.isNotEmpty(serviceMap)){
				memberId = MjkjUtils.getMap2Str(serviceMap, "id");
			}
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_CONTRACT)) {//合约钱包
			tableName = "coin_member_wallet_contract";
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_MARKET)) {//市值维护
			tableName = "coin_member_wallet_market";
		} else if (Func.equals(type, WalletConstant.WALLET_TYPE_WEALTH)) {//理财
			tableName = "coin_member_wallet_wealth";
		}

		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("member_id", memberId);
		wrapper.eq("is_deleted", 0);
		wrapper.eq("coin_id", coinId);
		Map<String, Object> walletMap = mjkjBaseSqlService.getDataOneByFieldParams(tableName, wrapper);

		if (Func.isEmpty(walletMap)) {//不存在，自动新建
			synchronized (this) {//有可能存在高并发，导致创建两个一样的钱包
				walletMap = mjkjBaseSqlService.getDataOneByFieldParams(tableName, wrapper);
				if (Func.isNotEmpty(walletMap)) {
					return walletMap;
				}
				Map<String, Object> coinCoin = mjkjBaseSqlService.getTableById("coin_coin", coinId);
				Map<String, Object> insertMap = new HashMap<>();
				insertMap.put("id", IdWorker.getId());
				insertMap.put("member_id", memberId);
				insertMap.put("coin_id", coinId);
				insertMap.put("coin_symbol", MjkjUtils.getMap2Str(coinCoin, "symbol"));
				insertMap.put("balance", BigDecimal.ZERO);
				insertMap.put("frozen_balance", BigDecimal.ZERO);
				if (Func.isNotEmpty(marginCode)) {
					insertMap.put("margin_code", marginCode);
				}
				mjkjBaseSqlService.baseInsertData(tableName, insertMap);
				return insertMap;
			}
		}
		return walletMap;
	}



}
