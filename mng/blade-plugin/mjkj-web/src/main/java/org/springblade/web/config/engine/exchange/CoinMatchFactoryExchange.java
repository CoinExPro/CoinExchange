package org.springblade.web.config.engine.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CoinMatchFactoryExchange {

    //symbolName 为交易对  BTC/USDT


    private ConcurrentHashMap<String, CoinMatchExchange> matchMap;

    public CoinMatchFactoryExchange(){
        this.matchMap = new ConcurrentHashMap<>();
    }

    public void addExchangeCoinMatch(String symbolName, CoinMatchExchange match) {
        log.info("ExchangeCoinMatchFactory add match = {}", symbolName);
        if(!this.containsExchangeCoinMatch(symbolName)) {
            this.matchMap.put(symbolName, match);
        }
    }

	public void reset(String symbolName,IMjkjBaseSqlService baseSqlService) {
    	try{
			CoinMatchExchange coinMatchExchange = this.matchMap.get(symbolName);
			coinMatchExchange.init(baseSqlService);
		}catch (Exception e){

		}

	}

    public boolean containsExchangeCoinMatch(String symbolName) {
        return this.matchMap != null && this.matchMap.containsKey(symbolName);
    }

	public CoinMatchExchange getExchangeCoinMatchAuto(String symbolName, IMjkjBaseSqlService baseSqlService) {
		if(!this.containsExchangeCoinMatch(symbolName)) {
			CoinMatchExchange match=new CoinMatchExchange(symbolName);
			match.init(baseSqlService);
			this.matchMap.put(symbolName, match);
		}
		return this.matchMap.get(symbolName);
	}

    public Map<String, CoinMatchExchange> getMatchMap() {
        return this.matchMap;
    }
}
