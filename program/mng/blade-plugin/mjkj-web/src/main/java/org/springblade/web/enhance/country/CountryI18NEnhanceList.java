package org.springblade.web.enhance.country;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springblade.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Date: 2022-9-20
 */
@Component("countryI18NEnhanceList")
@RequiredArgsConstructor
public class CountryI18NEnhanceList implements CgformEnhanceJavaListInter {
	private final IMjkjBaseSqlService sqlService;

	@Override
	public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params) throws BusinessException {

	}
}
