package org.springblade.web.enhance;

import lombok.RequiredArgsConstructor;
import org.springblade.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("paymentEnhanceList")
@RequiredArgsConstructor
public class PaymentEnhanceList implements CgformEnhanceJavaListInter {
	private final IMjkjBaseSqlService sqlService;

	@Override
	public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params) throws BusinessException {

	}
}
