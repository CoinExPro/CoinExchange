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

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springblade.cgform.entity.*;
import org.springblade.cgform.enums.CgformConstant;
import org.springblade.cgform.enums.CgformEnum;
import org.springblade.cgform.model.*;
import org.springblade.cgform.model.query.QueryGenerator;
import org.springblade.cgform.service.*;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjjyConfig;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.db.DbConfig;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.util.*;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.web.utils.resource.OssBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.TreeModel;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @since 2021-05-20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("cgform-api")
@Api(value = "表单开发接口", tags = "表单开发接口")
public class CgformApiController extends BaseController {

	private final ICgformHeadService cgformHeadService;

	private final ICgformFieldService cgformFieldService;


	private final ICgformEnhanceJavaService javaService;

	private final ICgformEnhanceSqlService sqlService;


	private final BladeRedis redis;

	private static final String CGFORM_ID_REDIS_KEY = "cgform:id:";


	@ApiOperationSupport(order = 12)
	@GetMapping({"/getData/{headId}"})
	@ApiOperation(value = "功能测试 - 获取数据列表-ok", notes = "获取数据列表")
	public R<Map<String, Object>> getData(@PathVariable("headId") Long headId, HttpServletRequest req) {
		// 根据headId查询表单
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		String langRedisKey=CGFORM_ID_REDIS_KEY+headId;//语言
		if(Func.isNotEmpty(property) && property.startsWith("mjkj-web")){
			if (headId == 1530094068845895681L && redis.exists(langRedisKey)) {
				//return R.data(redis.get(langRedisKey));
			}
		}

		CgformHead onlCgformHead = cgformHeadService.getById(headId);
		if (Func.isEmpty(onlCgformHead)) {
			return R.fail("实体不存在");
		}
		//判断该接口是否要的登录
		String nologinSelect = onlCgformHead.getNologinSelect();
		if(Func.equals(nologinSelect,"0")){//需要登录
			BladeUser user = AuthUtil.getUser();
			if(Func.isEmpty(user)){
				return R.fail("登录已过期，请重新登录");
			}
			//登录成功，判断改表是否对外开放
			String noViewRoleStrList = onlCgformHead.getNoViewDataRole();
			if(Func.isNotEmpty(noViewRoleStrList)){//有设置某一个角色不允许访问
				List<Long> roleList = Func.toLongList(noViewRoleStrList);

				BladeUser onlineUser = AuthUtil.getUser();
				String roleIdStrList = onlineUser.getRoleId();
				List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

				List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
				if(Func.isNotEmpty(collect)){
					return R.fail("暂无查看数据权限");
				}
			}


		}


		try {
			String tableName = onlCgformHead.getTableName();
			Map<String, Object> params = SqlSymbolUtil.getParameterMap(req);//获取查询参数
			Map<String, Object> resultMap =new HashMap<>();
			if(Func.equals(onlCgformHead.getFormCategory(),"view")){//显示表
				resultMap.put("total",0);
				resultMap.put("records",new ArrayList<>());
			}else{
				resultMap = cgformFieldService.queryAutolistPage(tableName, headId, params, null);
			}
			Long total = MjkjUtils.getMap2Long(resultMap, "total");
			List<Map<String, Object>> dataList = (List) resultMap.get("records");

			if (Func.isEmpty(dataList)) {
				dataList = new ArrayList<>();
			}
			//走增强
			javaService.executeEnhanceList(onlCgformHead, MjkjConstant.ENHANCE_QUERY, dataList, params);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead, MjkjConstant.ENHANCE_QUERY, params);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead, MjkjConstant.ENHANCE_QUERYANEXPORT, params);
			//分页
			if (Func.isNotEmpty(params.get("dataTotal")) && Func.isNotEmpty(params.get("dataRecords"))) {
				resultMap.put("total", params.get("dataTotal"));
				resultMap.put("records", params.get("dataRecords"));
				resultMap.put("dataOther", params.get("dataOther"));
				resultMap.put("idList", params.get("idList"));
				return R.data(resultMap);
			}
			if (Func.isNotEmpty(params.get("dataOther"))) {
				resultMap.put("dataOther", params.get("dataOther"));
			}
			resultMap.put("dataOther", params.get("dataOther"));
			//自定义分页
			Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
			if (Func.isNotEmpty(dataList) && pageSzie != -521) {//分组
				int size = dataList.size();
				Object pageNoObj = params.get("pageNo");
				Object pageSizeObj = params.get("pageSize");

				if (size > total.intValue() && Func.isNotEmpty(pageNoObj) && Func.isNotEmpty(pageNoObj)) {//查询出来的数据大于总数
					Integer page = Func.toInt(pageNoObj);
					Integer pageSize = Func.toInt(pageSizeObj);
					dataList = dataList.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
					resultMap.put("total", size);
					resultMap.put("records", dataList);
				}

			} else if (Func.isNotEmpty(dataList) && pageSzie == -521) {
				resultMap.put("records", dataList);

				//仅对该表加缓存
				if (headId ==1530094068845895681L) {
					redis.setEx(langRedisKey, resultMap, Duration.ofMinutes(30));
				}
			} else {
				//所有id
				Object idList = params.get("idList");
				if (Func.isNotEmpty(idList)) {
					resultMap.put("idList", idList);
				}
			}

			return R.data(resultMap);
		} catch (Exception e) {
			e.getStackTrace();
			return R.fail("数据库查询失败" + e.getMessage());
		}
	}











}
