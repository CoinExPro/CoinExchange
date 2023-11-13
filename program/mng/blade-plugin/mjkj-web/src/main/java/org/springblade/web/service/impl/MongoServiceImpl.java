package org.springblade.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springblade.config.market.MarketKlineUtils;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.model.KLine;
import org.springblade.web.model.MongoDetail;
import org.springblade.web.service.IMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * mongodb服务类
 */
@Slf4j
@Service
public class MongoServiceImpl implements IMongoService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 *
	 * @param type 类型
	 * @param fromTime 交易对 btc/usdt
	 * @param fromTime  开始时间
	 * @param toTime 结束时间
	 * @param period 时间类型
	 * @return
	 */
	@Override
	public List<KLine> findAllKLine(String type,String symbolName, long fromTime, long toTime, String period){
		String mangodbKey=MarketKlineUtils.getKLineMongdbKey(type,symbolName,period);//1
		Criteria criteria = Criteria.where("time").gte(fromTime).andOperator(Criteria.where("time").lte(toTime));
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "time"));
		Query query = new Query(criteria).with(sort);
		List<KLine> kLines = mongoTemplate.find(query, KLine.class, mangodbKey);
		return kLines;
	}

	//获取最新价格
	@Override
	public BigDecimal getPriceBySymbol(String type,String symbolName){
		String mangodbkey = MarketKlineUtils.getDetailMongdbKey(type, symbolName);
		Query query = new Query(Criteria.where("symbolName").is(symbolName.toUpperCase())).limit(1);
		MongoDetail selectModel = mongoTemplate.findOne(query, MongoDetail.class, mangodbkey);;
		if(Func.isNotEmpty(selectModel)){
			return selectModel.getClose();
		}
		return BigDecimal.ZERO;
	}

	//获取更新时间
	@Override
	public Long getUpdateTimeBySymbol(String type,String symbolName){
		String mangodbkey = MarketKlineUtils.getDetailMongdbKey(type, symbolName);
		Query query = new Query(Criteria.where("symbolName").is(symbolName.toUpperCase())).limit(1);
		MongoDetail selectModel = mongoTemplate.findOne(query, MongoDetail.class, mangodbkey);;
		if(Func.isNotEmpty(selectModel)){
			return selectModel.getUpdateTime();
		}
		return 0L;
	}

	@Override
	public BigDecimal getPriceBySymbol(String symbolName){
		try{
			return this.getPriceBySymbol("xh", symbolName);
		}catch (Exception e){
			return BigDecimal.ZERO;
		}
	}

	//获取详情
	@Override
	public MongoDetail getPriceBySymbolDetail(String type,String symbolName){
		String mangodbkey = MarketKlineUtils.getDetailMongdbKey(type, symbolName);
		Query query = new Query(Criteria.where("symbolName").is(symbolName.toUpperCase())).limit(1);
		MongoDetail selectModel = mongoTemplate.findOne(query, MongoDetail.class, mangodbkey);
		return selectModel;
	}

}
