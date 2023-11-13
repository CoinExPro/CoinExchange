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
package org.springblade.config.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.config.MybatisPlusConfiguration;
import org.springblade.core.tenant.*;
import org.springblade.core.tenant.aspect.BladeTenantAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 多租户配置类
 *
 *
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(MybatisPlusConfiguration.class)
@EnableConfigurationProperties(BladeTenantProperties.class)
public class MjkjTenantConfiguration {

	/**
	 * 自定义多租户处理器
	 *
	 * @param tenantProperties 多租户配置类
	 * @return TenantHandler
	 */
	@Bean
	@Primary
	public TenantLineHandler bladeTenantHandler(BladeTenantProperties tenantProperties) {
		return new BladeTenantHandler(tenantProperties);
	}

	/**
	 * 自定义租户拦截器
	 *
	 * @param tenantHandler    多租户处理器
	 * @param tenantProperties 多租户配置类
	 * @return MjkjTenantInterceptor
	 */
	@Bean
	@Primary
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler, BladeTenantProperties tenantProperties) {
		MjkjTenantInterceptor tenantInterceptor = new MjkjTenantInterceptor();
		tenantInterceptor.setTenantLineHandler(tenantHandler);
		tenantInterceptor.setTenantProperties(tenantProperties);
		return tenantInterceptor;
	}

	/**
	 * 自定义租户id生成器
	 *
	 * @return TenantId
	 */
	@Bean
	@ConditionalOnMissingBean(TenantId.class)
	public TenantId tenantId() {
		return new BladeTenantId();
	}

	/**
	 * 自定义租户切面
	 */
	@Bean
	public BladeTenantAspect bladeTenantAspect() {
		return new BladeTenantAspect();
	}

}
