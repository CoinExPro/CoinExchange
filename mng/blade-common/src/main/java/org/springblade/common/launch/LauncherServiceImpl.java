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
package org.springblade.common.launch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.constant.NacosConstant;
import org.springblade.core.launch.service.LauncherService;
import org.springblade.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Map;
import java.util.Properties;

/**
 * 启动参数拓展
 *
 *    smallchil
 */
@Slf4j
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		Map<String, String> maps = System.getenv();
		String nacosUrl="";
		String elkUrl="";
		log.info("==========开始获取=========");
		for (String key:maps.keySet()) {
			String val = maps.get(key);
			if(StringUtils.isNotEmpty(val)){
				if(key.equals("nacos-url")){
					log.error("key===="+key+"  val====="+val);
					nacosUrl=val;
				}else if(key.equals("elk-url")){
					log.error("key===="+key+"  val====="+val);
					elkUrl=val;
				}
			}
		}

		if(StringUtils.isEmpty(nacosUrl)){
			nacosUrl=LauncherConstant.nacosAddr(profile);
		}
		if(StringUtils.isEmpty(elkUrl)){
			elkUrl=LauncherConstant.elkAddr(profile);
		}

		// 通用注册
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", nacosUrl);
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", nacosUrl);
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", nacosUrl);
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");

		//魔晶定制
		String GROUP_STR="DEFAULT_GROUP";
		PropsUtil.setProperty(props,"spring.cloud.nacos.discovery.group", GROUP_STR);
		PropsUtil.setProperty(props,"spring.cloud.nacos.discovery.service", appName);

		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[0].data-id", NacosConstant.sharedDataId());
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[0].group", GROUP_STR);
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[0].refresh", "true");
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[1].data-id", NacosConstant.sharedDataId(profile));
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[1].group", GROUP_STR);
		PropsUtil.setProperty(props,"spring.cloud.nacos.config.shared-configs[1].refresh", "true");


		//todo 线上环境
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", "CoinExPro_Open");
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", "CoinExPro_Open");

	}

}
