package org.springblade.web.service;

import org.springblade.web.model.MarketGoodsModel;
import org.springblade.web.model.param.MarketJoinParam;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 市值维护
 */
public interface IMarketService {


	//获取消息初始数据
	Object messageInitdata(String type);
}
