package org.springblade.web.config.engine.contract;

import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.web.model.ForceModelContractAll;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *合约-U本位-全仓
 */
@Slf4j
public class CoinMatchContractFactoryAll {

	//symbolName 为交易对  BTC/USDT
	private ConcurrentHashMap<String, CoinMatchContractAll> matchMap;

	//symbolName -》 UBW   BBW

	public CoinMatchContractFactoryAll(){
		this.matchMap = new ConcurrentHashMap<>();
	}

	public void addExchangeCoinMatch(String contractTypeStr, CoinMatchContractAll match) {
		contractTypeStr=contractTypeStr.toUpperCase();
		if(!this.containsExchangeCoinMatch(contractTypeStr)) {
			this.matchMap.put(contractTypeStr, match);
		}
	}

	public boolean containsExchangeCoinMatch(String contractTypeStr) {
		contractTypeStr=contractTypeStr.toUpperCase();
		return this.matchMap != null && this.matchMap.containsKey(contractTypeStr);
	}

	public ForceModelContractAll reset(String memberId,String contractTypeStr,IMjkjBaseSqlService baseSqlService) {
		contractTypeStr=contractTypeStr.toUpperCase();
		CoinMatchContractAll match=this.getExchangeCoinMatchAuto(contractTypeStr,baseSqlService);
		ForceModelContractAll reset = match.reset(memberId, baseSqlService);
		return reset;
	}

	public void resetAll(String contractTypeStr,IMjkjBaseSqlService baseSqlService) {
		try{
			contractTypeStr=contractTypeStr.toUpperCase();
			CoinMatchContractAll match=this.getExchangeCoinMatchAuto(contractTypeStr,baseSqlService);
			match.resetAll(baseSqlService);
		}catch (Exception e){

		}

	}


	public CoinMatchContractAll getExchangeCoinMatchAuto(String contractTypeStr, IMjkjBaseSqlService baseSqlService) {
		contractTypeStr=contractTypeStr.toUpperCase();
		if(!this.containsExchangeCoinMatch(contractTypeStr)) {
			CoinMatchContractAll match=new CoinMatchContractAll();
			match.init(baseSqlService);
			this.matchMap.put(contractTypeStr, match);
		}
		return this.matchMap.get(contractTypeStr);
	}

	public Map<String, CoinMatchContractAll> getMatchMap() {
		return this.matchMap;
	}

}
