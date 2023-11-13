package org.springblade.web.config.engine.exchange;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.model.EntrustModel;
import org.springblade.web.model.ForceModel;
import org.springblade.web.model.ForceModelAll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 合约撮合引擎-现货
 */
@Data
public class CoinMatchExchange {

	private String symbolName;//交易对 BTC/USDT
	private BigDecimal lastPrice;//上一次同步价格
	private Long lastTime;//上一次时间
	private BigDecimal miniPriceChangeRefresh;//最小变动单位

	private List<EntrustModel> entrustList;//委托列表

	private List<ForceModel> zcForceList;//逐仓爆仓列表

	private boolean allSymbolNameFlag = false;//是否是全仓交易对



	/**
	 * 构造函数
	 *
	 * @param symbolName 交易对
	 */
	public CoinMatchExchange(String symbolName) {
		this.symbolName = symbolName;
		this.entrustList = new ArrayList<>();
		this.zcForceList =new ArrayList<>();
		this.miniPriceChangeRefresh = BigDecimal.ZERO;
	}

	//初始化
	public  void init(IMjkjBaseSqlService baseSqlService) {
		synchronized (this){
			this.entrustList = new ArrayList<>();
			this.zcForceList =new ArrayList<>();
			this.miniPriceChangeRefresh = BigDecimal.ZERO;
			//加入委托引擎
			this.addEntrust(baseSqlService);

			//加入逐仓引擎
			this.addFixedBorrow(baseSqlService);
			//--加入全仓交易对列表
			this.addFllSymbolNameFlag(baseSqlService);
		}


	}

	//重置逐仓爆仓价
	public void resetZcForce(String memberId) {
		List<ForceModel> forceList = this.zcForceList;

		Iterator<ForceModel> iterator = forceList.iterator();
		boolean addFlag = true;
		while (iterator.hasNext()) {
			ForceModel next = iterator.next();
			if (Func.equals(memberId, next.getMemberId())) {
				next.setForcePrice(BigDecimal.ZERO);//重置
				addFlag = false;
			}
		}
		if (addFlag) {
			ForceModel forceModel = new ForceModel();
			forceModel.setMemberId(memberId);
			forceModel.setForcePrice(BigDecimal.ZERO);//重置
			forceList.add(forceModel);//加入引擎
		}
	}


	//加入委托引擎
	private void addEntrust(IMjkjBaseSqlService baseSqlService) {
		if(Func.isEmpty(this.entrustList)){
			this.entrustList=new ArrayList<>();
		}
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("symbol_name", symbolName);
		wrapper.eq("entrust_status", 0);//委托中
		wrapper.eq("is_deleted", 0);
		wrapper.eq("type", "xh");//现货
		List<Map<String, Object>> selectEntrustList = baseSqlService.getDataListByFieldParams("coin_entrust", wrapper);
		if (Func.isNotEmpty(selectEntrustList)) {
			for (Map<String, Object> entrustMap : selectEntrustList) {
				String entrustCode = MjkjUtils.getMap2Str(entrustMap, "entrust_code");
				String entrustType = MjkjUtils.getMap2Str(entrustMap, "entrust_type");
				String calculationMethod = MjkjUtils.getMap2Str(entrustMap, "calculation_method");
				List<EntrustModel.CalculationModel> list = JsonUtil.parseArray(calculationMethod, EntrustModel.CalculationModel.class);
				EntrustModel entrustModel = new EntrustModel();
				entrustModel.setEntrustCode(entrustCode);
				entrustModel.setEntrustType(entrustType);
				entrustModel.setCalculationList(list);
				this.entrustList.add(entrustModel);
			}
		}
	}

	//加入逐仓爆仓引擎
	private void addFixedBorrow(IMjkjBaseSqlService baseSqlService) {
		this.zcForceList=new ArrayList<>();
		QueryWrapper<Object> borrowWrapper = new QueryWrapper<>();
		borrowWrapper.eq("symbol_name", symbolName);
		borrowWrapper.eq("margin_type", "fixed");
		borrowWrapper.eq("borrow_status", "0");//借款中
		borrowWrapper.eq("is_deleted", 0);
		List<Map<String, Object>> borrowList = baseSqlService.getDataListByFieldParams("coin_member_wallet_margin_borrow", borrowWrapper);

		if (Func.isNotEmpty(borrowList)) {
			for (Map<String, Object> borrowMap : borrowList) {
				String memberId = MjkjUtils.getMap2Str(borrowMap, "member_id");
				this.resetZcForce(memberId);
			}
		}
	}

	//加入全仓币种
	private void addFllSymbolNameFlag(IMjkjBaseSqlService baseSqlService) {
		QueryWrapper<Object> exchangeWrapper = new QueryWrapper<>();
		exchangeWrapper.eq("symbol_name", symbolName);
		exchangeWrapper.eq("enable_full_lever", "1");//全仓
		exchangeWrapper.eq("is_deleted", 0);
		Map<String, Object> exchangeMap = baseSqlService.getDataOneByFieldParams("coin_coin_exchange", exchangeWrapper);
		if (Func.isNotEmpty(exchangeMap)) {
			this.allSymbolNameFlag = true;
		} else {
			this.allSymbolNameFlag = false;
		}
	}

}
