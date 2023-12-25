package org.springblade.web.config.engine.contract;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.model.ContractForceModel;
import org.springblade.web.model.EntrustModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 合约撮合引擎
 */
@Data
public class CoinMatchContract {

	private String symbolName;//交易对 BTC/USDT
	private BigDecimal lastPrice;//上一次同步价格
	private Long lastTime;//上一次时间
	private List<EntrustModel> entrustList;//委托列表
	private BigDecimal miniPriceChangeRefresh=BigDecimal.ZERO;

	private List<ContractForceModel> fixedForceList;//逐仓爆仓列表


	/**
	 * 构造函数
	 *
	 * @param symbolName 交易对
	 */
	public CoinMatchContract(String symbolName) {
		this.symbolName = symbolName;
		this.entrustList=new ArrayList<>();//委托列表
		this.fixedForceList =new ArrayList<>();//逐仓列表
	}

	//初始化
	public  void init(IMjkjBaseSqlService baseSqlService) {
		synchronized (this){
			this.entrustList=new ArrayList<>();//委托列表
			this.fixedForceList =new ArrayList<>();//逐仓列表

			//加入委托引擎
			this.addEntrust(baseSqlService);

			//加入逐仓引擎
			this.addFixedForce(baseSqlService);
		}
	}


	/**
	 * 重置逐仓爆仓价
	 * @param contractLogId 仓位id
	 * @param direction 方向 1=开多，2=开空
	 */
	public void resetFixedForce(String contractLogId,String direction) {
		List<ContractForceModel> forceList = this.fixedForceList;
		Iterator<ContractForceModel> iterator = forceList.iterator();
		boolean addFlag = true;
		while (iterator.hasNext()) {
			ContractForceModel next = iterator.next();
			if (Func.equals(contractLogId, next.getContractLogId())) {
				next.setForcePrice(BigDecimal.ZERO);//重置
				addFlag = false;
			}
		}
		if (addFlag) {
			ContractForceModel forceModel = new ContractForceModel();
			forceModel.setForcePrice(BigDecimal.ZERO);//重置
			forceModel.setContractLogId(contractLogId);
			forceModel.setDirection(direction);
			forceList.add(forceModel);//加入引擎
		}
	}

	//加入逐仓爆仓引擎
	private void addFixedForce(IMjkjBaseSqlService baseSqlService) {
		//获取我的逐仓仓位
		QueryWrapper<Object> wrrapper = new QueryWrapper<>();
		wrrapper.eq("symbol_name", symbolName);
		wrrapper.eq("order_status", "0");//委托中
		wrrapper.eq("is_deleted", 0);
		wrrapper.eq("pattern","1");//逐仓
		List<Map<String, Object>> contractList = baseSqlService.getDataListByFieldParams("coin_log_contract", wrrapper);

		if (Func.isEmpty(contractList)) {
			return;
		}
		for (Map<String, Object> contractMap : contractList) {
			String contractLogId = MjkjUtils.getMap2Str(contractMap, "id");
			String direction = MjkjUtils.getMap2Str(contractMap, "direction");//方向
			//重置引擎
			this.resetFixedForce(contractLogId,direction);
		}
	}

	//加入委托引擎
	private void addEntrust(IMjkjBaseSqlService baseSqlService) {
		//先启动委托引擎-现货交易
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("symbol_name",symbolName);
		wrapper.eq("entrust_status", 0);//委托中
		wrapper.eq("is_deleted", 0);
		List<Map<String, Object>> selectEntrustList = baseSqlService.getDataListByFieldParams("coin_entrust", wrapper);
		if (Func.isEmpty(selectEntrustList)) {
			return;
		}
		for (Map<String, Object> entrustMap : selectEntrustList) {
			String entrustCode = MjkjUtils.getMap2Str(entrustMap, "entrust_code");
			String entrustType = MjkjUtils.getMap2Str(entrustMap, "entrust_type");
			String calculationMethod = MjkjUtils.getMap2Str(entrustMap, "calculation_method");
			List<EntrustModel.CalculationModel> list = JsonUtil.parseArray(calculationMethod, EntrustModel.CalculationModel.class);

			EntrustModel entrustModel = new EntrustModel();
			entrustModel.setEntrustCode(entrustCode);//委托号
			entrustModel.setEntrustType(entrustType);//委托类型
			entrustModel.setCalculationList(list);//计算方式
			this.entrustList.add(entrustModel);
		}
	}


}
