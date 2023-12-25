package org.springblade.web.config.engine.contract;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.tool.utils.Func;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CoinMatchContractFactory {

    private ConcurrentHashMap<String, CoinMatchContract> matchMap;

    public CoinMatchContractFactory(){
        this.matchMap = new ConcurrentHashMap<>();
    }

    public void addExchangeCoinMatch(String symbolName, CoinMatchContract match) {
        log.info("CoinMatchContractFactory add match = {}", symbolName);
        if(!this.containsExchangeCoinMatch(symbolName)) {
            this.matchMap.put(symbolName, match);
        }
    }

    public boolean containsExchangeCoinMatch(String symbolName) {
        return this.matchMap != null && this.matchMap.containsKey(symbolName);
    }


	public CoinMatchContract getExchangeCoinMatchAuto(String symbolName, IMjkjBaseSqlService baseSqlService) {
		if(!this.containsExchangeCoinMatch(symbolName)) {
			CoinMatchContract match=new CoinMatchContract(symbolName);
			match.init(baseSqlService);
			this.matchMap.put(symbolName, match);
		}
		return this.matchMap.get(symbolName);
	}

    public Map<String, CoinMatchContract> getMatchMap() {
        return this.matchMap;
    }

	/**
	 * 刷新用户逐仓数据
	 * @param baseSqlService
	 * contractType  ubw  bbw
	 * @return
	 */
	public void resetMemberForceFixed(String memberId,String contractTypeStr,IMjkjBaseSqlService baseSqlService) {
		String contractType =Func.equals(contractTypeStr,"UBW")?"1":"2";
		QueryWrapper<Object> wrapper=new QueryWrapper<>();
		wrapper.eq("member_id",memberId);
		wrapper.eq("order_status","0");//进行中
		wrapper.eq("contract_type",contractType);//合约类型
		wrapper.eq("pattern","1");//逐仓
		List<Map<String, Object>> contractMapLogList = baseSqlService.getDataListByFieldParams("coin_log_contract", wrapper);
		if(Func.isNotEmpty(contractMapLogList)){
			return;
		}
		for (Map<String,Object> contractMap:contractMapLogList) {
			String id = MjkjUtils.getMap2Str(contractMap, "id");
			String symbolName = MjkjUtils.getMap2Str(contractMap, "symbol_name");
			String direction = MjkjUtils.getMap2Str(contractMap, "direction");
			this.getExchangeCoinMatchAuto(symbolName,baseSqlService).resetFixedForce(id,direction);
		}
	}
	/**
	 * 重置所有
	 * @param baseSqlService
	 * contractType  ubw  bbw
	 * @return
	 */
	public void resetAll(String symbolName,IMjkjBaseSqlService baseSqlService) {
		try{
			this.getExchangeCoinMatchAuto(symbolName,baseSqlService).init(baseSqlService);
		}catch (Exception e){

		}

	}

}
