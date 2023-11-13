package org.springblade.web.enhance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component("articleEnhanceList")
public class ArticleEnhanceList implements CgformEnhanceJavaListInter {
	private final IMjkjBaseSqlService sqlService;

	@Override
	public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params) throws BusinessException {

	}
}
