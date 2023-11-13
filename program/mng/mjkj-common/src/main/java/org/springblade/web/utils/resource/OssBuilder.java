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
package org.springblade.web.utils.resource;

import org.springblade.core.oss.OssTemplate;
import org.springblade.core.oss.enums.OssEnum;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.BladeOssRule;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.entity.Oss;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oss云存储统一构建类
 *
 *
 */
@Component
public class OssBuilder {

	public static final String OSS_CODE = "oss:code:";
	public static final String OSS_PARAM_KEY = "code";

	private final OssProperties ossProperties;

	public OssBuilder(OssProperties ossProperties ){
		this.ossProperties = ossProperties;
	}

	/**
	 * OssTemplate配置缓存池
	 */
	private final Map<String, OssTemplate> templatePool = new ConcurrentHashMap<>();

	/**
	 * oss配置缓存池
	 */
	private final Map<String, Oss> ossPool = new ConcurrentHashMap<>();

	/**
	 * 获取template
	 *
	 * @return OssTemplate
	 */
	public OssTemplate template() {
		return template(StringPool.EMPTY);
	}

	/**
	 * 获取template
	 *
	 * @param code 资源编号
	 * @return OssTemplate
	 */
	public OssTemplate template(String code) {
		String tenantId = AuthUtil.getTenantId();
		Oss oss = getOss(tenantId, code);
		Oss ossCached = ossPool.get(tenantId);
		OssTemplate template = templatePool.get(tenantId);
		// 若为空或者不一致，则重新加载
		if (Func.hasEmpty(template, ossCached) || !oss.getEndpoint().equals(ossCached.getEndpoint()) || !oss.getAccessKey().equals(ossCached.getAccessKey())) {
			synchronized (OssBuilder.class) {
				template = templatePool.get(tenantId);
				if (Func.hasEmpty(template, ossCached) || !oss.getEndpoint().equals(ossCached.getEndpoint()) || !oss.getAccessKey().equals(ossCached.getAccessKey())) {
					OssRule ossRule;
					// 若采用默认设置则开启多租户模式, 若是用户自定义oss则不开启
					if (oss.getEndpoint().equals(ossProperties.getEndpoint()) && oss.getAccessKey().equals(ossProperties.getAccessKey()) && ossProperties.getTenantMode()) {
						ossRule = new BladeOssRule(Boolean.TRUE);
					} else {
						ossRule = new BladeOssRule(Boolean.FALSE);
					}
					template = AliOssBuilder.template(oss, ossRule);
					templatePool.put(tenantId, template);
					ossPool.put(tenantId, oss);
				}
			}
		}
		return template;
	}

	/**
	 * 获取对象存储实体
	 *
	 * @param tenantId 租户ID
	 * @return Oss
	 */
	public Oss getOss(String tenantId, String code) {
		Oss defaultOss = new Oss();
		defaultOss.setId(0L);
		defaultOss.setCategory(OssEnum.of(ossProperties.getName()).getCategory());
		defaultOss.setEndpoint(ossProperties.getEndpoint());
		defaultOss.setBucketName(ossProperties.getBucketName());
		defaultOss.setAccessKey(ossProperties.getAccessKey());
		defaultOss.setSecretKey(ossProperties.getSecretKey());
		return defaultOss;
	}


}
