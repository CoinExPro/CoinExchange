/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.cgform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;

import org.springblade.config.util.SqlInjectionUtil;
import org.springblade.cgform.entity.*;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.service.*;
import org.springblade.core.boot.ctrl.BladeController;

import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

/**
 * 系统参数
 */
@RestController
@AllArgsConstructor
@RequestMapping("sys")
@Api(value = "分类字典", tags = "分类字典接口")
public class SystemController extends BladeController {

	private final IDictService dictService;


	private final IMjkjBaseSqlService mjkjBaseSqlService;

	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "根据字典code加载字典text 返回", notes = "根据字典code加载字典text 返回")
	@RequestMapping(value = "/sys/dictitem/loadDictItem/{dictCode}", method = RequestMethod.GET)
	public R loadDictItem(@PathVariable String dictCode,@RequestParam(name="key") String keys,HttpServletRequest request) {
		if(dictCode.indexOf(",")==-1) {
			return R.fail("字典Code格式不正确!");
		}
		String[] params = dictCode.split(",");
		if(params.length!=3) {
			return R.fail("字典Code格式不正确！");
		}
		List<String> list = dictService.queryTableDictByKeys(params[0], params[1], params[2], keys);
		return R.data(list);
	}

	/**
	 * 获取字典数据
	 * @param dictCode 字典code
	 * @param dictCode 表名,文本字段,code字段  | 举例：sys_user,realname,id
	 * @return
	 */
	@ApiOperationSupport(order = 20)
	@ApiOperation(value = "获取字典数据", notes = "获取字典数据")
	@RequestMapping(value = "/sys/dict/getDictItems/{dictCode}", method = RequestMethod.GET)
	public R<List<DictModel>> getDictItems(@PathVariable String dictCode, HttpServletRequest request) {

		List<DictModel> resultList = null;
		try {
			if(dictCode.indexOf(",")!=-1) {
				//关联表字典（举例：sys_user,realname,id）
				String[] params = dictCode.split(",");

				if(params.length<3) {
					return R.fail("字典Code格式不正确!");
				}
				//SQL注入校验（只限制非法串改数据库）
				final String[] sqlInjCheck = {params[0],params[1],params[2]};
				SqlInjectionUtil.filterContent(sqlInjCheck);

				if(params.length==4) {
					//SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
					SqlInjectionUtil.specialFilterContent(params[3]);
					resultList = dictService.queryTableDictItemsByCodeAndFilter(params[0],params[1],params[2],params[3]);
				}else if (params.length==3) {
					resultList = dictService.queryTableDictItemsByCode(params[0],params[1],params[2]);
				}else{
					return R.fail("字典Code格式不正确!");
				}
			}else {
				//字典表
				resultList = dictService.queryDictItemsByCode(dictCode);
			}
			//=======处理国际化======== 后端不要国际化
			Properties props = System.getProperties();
			String property = props.getProperty("spring.application.name");
			if(Func.isEmpty(property) || !Func.equals(property,"mjkj-bladex")){
				if(Func.isNotEmpty(resultList) && !Func.equals("coin_lang", dictCode)){
					for (DictModel model:resultList) {
						String title = model.getTitle();

						String language = mjkjBaseSqlService.getMngLanguage(title);
						model.setText(language);
						model.setLabel(language);
						model.setText(language);
					}
				}
			}



		} catch (Exception e) {
			return R.fail("操作失败");
		}

		return R.data(resultList);
	}



	/**
	 * 获取对象ID
	 *
	 * @return
	 */
	private String getCheckRuleId(CheckRule item) {
		try {
			return PropertyUtils.getProperty(item, "id").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
