package org.springblade.web.config.engine.contract;

import lombok.Data;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.model.ForceModelContractAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合约撮合引擎-全仓
 */
@Data
public class CoinMatchContractAll {

	//key 为用户id
	private Map<String, ForceModelContractAll> forceMap;//全仓爆仓列表

	private boolean isTriggerComplete = true;                        // 价格刷新是否完成，触发委托及爆仓

	private boolean isSelfPriceTriggerComplete = true;                        // 价格刷新是否完成，触发委托及爆仓(自定义)

	/**
	 * 构造函数
	 */
	public CoinMatchContractAll() {
		this.forceMap = new HashMap<>();
	}

	//初始化
	public void init(IMjkjBaseSqlService baseSqlService) {
		synchronized (this){
			//加入到引擎
			this.addAllForceContrct(baseSqlService);
		}

	}

	//重置所有
	public void resetAll(IMjkjBaseSqlService baseSqlService) {
		synchronized (this){
			List<String> memberIdList = baseSqlService.getAllContractMemberId();
			if(Func.isEmpty(memberIdList)){
				return;
			}
			//遍历所有用户
			for (String memberId:memberIdList) {
				ForceModelContractAll model = this.getForceModelAll(memberId, baseSqlService);
				if(Func.isEmpty(model)){
					continue;
				}
				forceMap.put(memberId,model);
			}
		}

	}
	//重置
	public  ForceModelContractAll reset(String memberId,IMjkjBaseSqlService baseSqlService) {
		synchronized (this){
			ForceModelContractAll model = this.getForceModelAll(memberId, baseSqlService);
			if(Func.isNotEmpty(model)){
				forceMap.put(memberId,model);
			}
			return model;
		}

	}

	//加入全仓爆仓引擎
	private void addAllForceContrct(IMjkjBaseSqlService baseSqlService) {
		List<String> memberIdList = baseSqlService.getAllContractMemberId();
		if(Func.isEmpty(memberIdList)){
			return;
		}
		//遍历所有用户
		for (String memberId:memberIdList) {
			ForceModelContractAll model = this.getForceModelAll(memberId, baseSqlService);
			if(Func.isEmpty(model)){
				continue;
			}
			forceMap.put(memberId,model);
		}

	}


	/**
	 * 获取模型
	 * @param memberId
	 * @param baseSqlService
	 * @return
	 */
	private ForceModelContractAll getForceModelAll(String memberId,IMjkjBaseSqlService baseSqlService){
		//获取我的所有全仓交易对
		List<String> symbolNameList = baseSqlService.getMyAllContractSymbolName(memberId);
		if(Func.isEmpty(symbolNameList)){
			forceMap.remove(memberId);
			return null;
		}
		ForceModelContractAll model=new ForceModelContractAll();
		model.setMemberId(memberId);
		model.setContractLogSymbolNameList(symbolNameList);
		return model;

	}

}
