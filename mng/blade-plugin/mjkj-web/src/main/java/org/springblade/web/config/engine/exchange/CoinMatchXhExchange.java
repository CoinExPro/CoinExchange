package org.springblade.web.config.engine.exchange;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.model.EntrustModel;
import org.springblade.web.model.ForceModel;
import org.springblade.web.model.ForceModelAll;
import org.springblade.web.service.IWebService;

import java.math.BigDecimal;
import java.util.*;

/**
 * 合约撮合引擎-现货-全仓
 */
@Data
public class CoinMatchXhExchange {

	private Map<String,ForceModelAll> forceMap;//全仓爆仓列表

	private boolean isTriggerComplete = true;                        // 价格刷新是否完成，触发委托及爆仓

	/**
	 * 构造函数
	 */
	public CoinMatchXhExchange() {
		this.forceMap = new HashMap<>();
	}

}
