package org.springblade.web.model.param;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Author: @Wai
 * Date: 2022-10-11
 */
@Data
public class SortListParam {
	private List<Map<String, Object>> sortList;
}
