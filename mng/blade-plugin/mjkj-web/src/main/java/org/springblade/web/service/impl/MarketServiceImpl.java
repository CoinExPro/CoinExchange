package org.springblade.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.WalletConstant;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.entity.AddWalletParam;
import org.springblade.entity.OperateWalletParam;
import org.springblade.entity.SubWalletParam;
import org.springblade.model.DeptHModel;
import org.springblade.web.mapper.MarketMapper;
import org.springblade.web.model.ContractTrade;
import org.springblade.web.model.InviteRewardModel;
import org.springblade.web.model.MarketGoodsModel;
import org.springblade.web.model.param.MarketJoinParam;
import org.springblade.web.service.IMarketService;
import org.springblade.web.service.IMongoService;
import org.springblade.web.service.IWalletService;
import org.springblade.web.service.IWebService;
import org.springblade.web.utils.GeneratorUtil;
import org.springblade.web.utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 市值维护
 */
@Slf4j
@Service
public class MarketServiceImpl implements IMarketService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private MarketMapper marketMapper;


	//获取消息初始数据
	@Override
	public Object messageInitdata(String type) {
		Object result = null;
		if (Func.equals(type, "index")) {//首页
			result = MarketUtils.indexDataMap.get(type);
		} else if (type.startsWith("deptH_")) {
			DeptHModel deptHModel = MarketUtils.dhcpHDataMap.get(type);
			result = deptHModel;
		} else if (type.startsWith("tract_")) {
			CircularFifoQueue<ContractTrade> contractTradeQueue = (CircularFifoQueue<ContractTrade>) MarketUtils.tradeHDataMap.get(type);
			List<ContractTrade> list = null;
			if (Func.isNotEmpty(contractTradeQueue)) {
				list = contractTradeQueue.stream().collect(Collectors.toList());
				Collections.reverse(list);
			}
			if (type.startsWith("tract_xh_trade_")) {//只是特殊处理现货
				if (Func.isEmpty(list)) {
					if (Func.isEmpty(list)) {
						list = new ArrayList<>();
					}
					String symbolName = type.replaceAll("tract_xh_trade_", "");
					QueryWrapper<Object> wrapper=new QueryWrapper<>();
					wrapper.eq("is_deleted",0);
					wrapper.eq("symbol_name",symbolName);
					wrapper.select("exchange_type");
					Map<String, Object> coinExchangeMap = baseSqlService.getDataOneByFieldParams("coin_coin_exchange", wrapper);
					if(Func.isNotEmpty(coinExchangeMap)){
						//1=走货模式 2=撮合模式
						String exchangeType = MjkjUtils.getMap2Str(coinExchangeMap, "exchange_type");
						if(Func.equals(exchangeType,"2")){//撮合模式，则自己查
							//获取最近20
							List<ContractTrade> detailList = marketMapper.getExchangeDetailList(symbolName);
							if (Func.isNotEmpty(detailList)) {
								list.addAll(detailList);
							}
							list = list.stream().sorted(Comparator.comparing(ContractTrade::getTime)).collect(Collectors.toList());
							Collections.reverse(list);
						}
					}

				}
			}


			result = list;
		}
		return result;
	}

}
