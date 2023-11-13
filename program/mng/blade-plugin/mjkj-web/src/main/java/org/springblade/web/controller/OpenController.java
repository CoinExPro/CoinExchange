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
package org.springblade.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjjyConfig;
import org.springblade.config.market.MarketConstant;
import org.springblade.config.market.MarketKlineUtils;
import org.springblade.config.util.EmailTemplateUtils;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.*;
import org.springblade.feign.IMjkjMarketClient;
import org.springblade.web.model.KLine;
import org.springblade.web.model.MarketGoodsModel;
import org.springblade.web.model.MongoDetail;
import org.springblade.web.model.param.*;
import org.springblade.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("coin/open")
@Api(value = "公共开放接口", tags = "公共开放接口")
public class OpenController {


	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@Autowired
	private IMarketService marketService;

	@Autowired
	private IWebService webService;


	@ApiOperationSupport(order = 1)
	@GetMapping({"/market/initData"})
	@ApiOperation(value = "市场详情初始数据", notes = "市场详情初始数据")
	public R getMarketInitData(String type) {
		Object o = marketService.messageInitdata(type);
		return R.data(o);
	}
	@ApiOperationSupport(order = 1)
	@GetMapping({"/get/download/url"})
	@ApiOperation(value = "获取最小下载地址", notes = "存币生息-获取存币计划详情")
	public R getDownloadUrl(String type) {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("client_type", type);
		wrapper.eq("is_deleted", 0);
		wrapper.orderByDesc("version_code");
		List<Map<String, Object>> dataMapList = mjkjBaseSqlService.getDataListByFieldParams("coin_app_version", wrapper);
		if (Func.isEmpty(dataMapList)) {
			return R.data("");
		}
		Map<String, Object> map = dataMapList.get(0);
		return R.data(MjkjUtils.getMap2Str(map, "url"));

	}
	@ApiOperationSupport(order = 2)
	@GetMapping({"/lang/{yy}"})
	@ApiOperation(value = "修改语言", notes = "修改语言")
	public R updateLang(@PathVariable("yy") String yy) {
		if(Func.isEmpty(yy)){
			return R.data(webService.getLanguage("成功"));
		}
		Long userId = AuthUtil.getUserId();
		if(Func.isEmpty(userId)){
			return R.data(webService.getLanguage("成功"));
		}

		return R.data(webService.getLanguage("成功"));
	}


}

