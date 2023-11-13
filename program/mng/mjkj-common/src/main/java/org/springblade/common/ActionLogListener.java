package org.springblade.common;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class ActionLogListener {
	private static final String filter = "execute_sql_string, create_time, create_user, update_time, update_user, tenant_id, create_dept, status";
	private static final String include = "coin_market_symbol, coin_market_period, coin_chain_list";
	private final IMjkjBaseSqlService sqlService;

	@Order
	@EventListener({ActionLogEvent.class})
	public void actionLogListener(ActionLogEvent event) {
		Map<String, Object> map =  (Map)event.getSource();
		String sqlString = MjkjUtils.getMap2Str(map, "execute_sql_string");
		int index = sqlString.indexOf("coin_");
		String tableName = sqlString.substring(index, sqlString.indexOf(" ", index));
		if (include.contains(tableName)) {
			String id = MjkjUtils.getMap2Str(map, "id");
			Map<String, Object> head = sqlService.getDataOneByField("onl_cgform_head", "table_name", tableName);
			String action = sqlString.contains("insert") ? getActionInsert(MjkjUtils.getMap2Str(head, "id"), map) :
				getActionUpdate(MjkjUtils.getMap2Str(head, "id"), map, sqlService.getTableById(tableName, id));
			if (action.length() == 4) return;
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("table_name", tableName);
			hashMap.put("log_id", id);
			hashMap.put("action", action.substring(0, action.length() - 1));
			hashMap.put("create_user", AuthUtil.getUserId());
			sqlService.baseInsertData("coin_log_action", hashMap);
		}
	}

	private String getActionInsert(String headId, Map<String, Object> map) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("新增: ");
		for (String s : map.keySet()) {
			if (filter.contains(s)) {
				continue;
			}
			Map<String, Object> field = sqlService.getDataOneByFieldParams("onl_cgform_field", Wrappers
				.query()
				.eq("is_deleted", 0)
				.eq("cgform_head_id", headId)
				.eq("db_field_name", s));

			String var1 = MjkjUtils.getMap2Str(map, s);

			String dictField = MjkjUtils.getMap2Str(field, "dict_field");
			String dictTable = MjkjUtils.getMap2Str(field, "dict_table");
			String fieldExtendJson = MjkjUtils.getMap2Str(field, "field_extend_json");
			String dbFieldTxt = MjkjUtils.getMap2Str(field, "db_field_txt");

			stringBuilder.append(dbFieldTxt).append(":");
			if (Func.isNotEmpty(dictField)) { //字典
				if (Func.isNotEmpty(dictTable)) {
					String dictText = MjkjUtils.getMap2Str(map, "dict_text");
					String var1Text = MjkjUtils.getMap2Str(sqlService.getDataOneByField(dictTable, dictField, var1), dictText);
					stringBuilder.append(var1Text).append(",");
				} else {
					String var1Text = sqlService.getSysDictItemValue(dictField, var1, false);
					stringBuilder.append(var1Text).append(",");
				}
			} else if (Func.isNotEmpty(fieldExtendJson) && fieldExtendJson.contains("title")) { //扩展参数
				Map<String, Object> kv = JsonUtil.toMap(fieldExtendJson);
				List<Map<String, Object>> list = MjkjUtils.getMapAll2List(kv, "dicData");
				for (Map<String, Object> m : list) {
					String title = MjkjUtils.getMap2Str(m, "title");
					String value = MjkjUtils.getMap2Str(m, "value");
					if (value.equals(var1)) {
						stringBuilder.append(title).append(",");
					}
				}
			} else {
				stringBuilder.append(var1).append(",");
			}
		}
		return stringBuilder.toString();
	}

	private String getActionUpdate(String headId, Map<String, Object> map, Map<String, Object> data) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("修改: ");
		for (String s : map.keySet()) {
			if (filter.contains(s)) {
				continue;
			}
			Map<String, Object> field = sqlService.getDataOneByFieldParams("onl_cgform_field", Wrappers
				.query()
				.eq("is_deleted", 0)
				.eq("cgform_head_id", headId)
				.eq("db_field_name", s));

			String var1 = MjkjUtils.getMap2Str(data, s);
			String var2 = MjkjUtils.getMap2Str(map, s);
			if (var1.equals(var2)) {
				continue;
			}
			String dictField = MjkjUtils.getMap2Str(field, "dict_field");
			String dictTable = MjkjUtils.getMap2Str(field, "dict_table");
			String fieldExtendJson = MjkjUtils.getMap2Str(field, "field_extend_json");
			String dbFieldTxt = MjkjUtils.getMap2Str(field, "db_field_txt");

			stringBuilder.append(dbFieldTxt).append(":");
			if (Func.isNotEmpty(dictField)) {
				if (Func.isNotEmpty(dictTable)) {
					String dictText = MjkjUtils.getMap2Str(map, "dict_text");
					String var1Text = MjkjUtils.getMap2Str(sqlService.getDataOneByField(dictTable, dictField, var1), dictText);
					String var2Text = MjkjUtils.getMap2Str(sqlService.getDataOneByField(dictTable, dictField, var2), dictText);
					stringBuilder.append(var1Text).append("->").append(var2Text).append(",");
				} else {
					String var1Text = sqlService.getSysDictItemValue(dictField, var1, false);
					String var2Text = sqlService.getSysDictItemValue(dictField, var2, false);
					stringBuilder.append(var1Text).append("->").append(var2Text).append(",");
				}
			} else if (Func.isNotEmpty(fieldExtendJson) && fieldExtendJson.contains("title")) {
				Map<String, Object> kv = JsonUtil.toMap(fieldExtendJson);
				List<Map<String, Object>> list = MjkjUtils.getMapAll2List(kv, "dicData");
				String var1Text = "";
				String var2Text = "";
				for (Map<String, Object> m : list) {
					String title = MjkjUtils.getMap2Str(m, "title");
					String value = MjkjUtils.getMap2Str(m, "value");
					if (value.equals(var1)) {
						var1Text = title;
					}
					if (value.equals(var2)) {
						var2Text = title;
					}
				}
				stringBuilder.append(var1Text).append("->").append(var2Text).append(",");

			} else {
				stringBuilder.append(var1).append("->").append(var2).append(",");
			}
		}
		return stringBuilder.toString();
	}
}
