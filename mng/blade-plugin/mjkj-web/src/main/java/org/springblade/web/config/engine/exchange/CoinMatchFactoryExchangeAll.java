package org.springblade.web.config.engine.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.web.model.ForceModelAll;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *现货全仓
 */
@Slf4j
public class CoinMatchFactoryExchangeAll {

	//symbolName 为交易对  BTC/USDT
	private ConcurrentHashMap<String, CoinMatchXhExchange> matchMap;

	private String symbolName="marginAll";

	public CoinMatchFactoryExchangeAll(){
		this.matchMap = new ConcurrentHashMap<>();
	}

	public void addExchangeCoinMatch(CoinMatchXhExchange match) {
		if(!this.containsExchangeCoinMatch(symbolName)) {
			this.matchMap.put(symbolName, match);
		}
	}

	public boolean containsExchangeCoinMatch(String symbolName) {
		return this.matchMap != null && this.matchMap.containsKey(symbolName);
	}



	public Map<String, CoinMatchXhExchange> getMatchMap() {
		return this.matchMap;
	}

}
