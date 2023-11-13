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
package org.springblade.gateway.dynamic;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway的路由定义模型
 *
 *
 */
@Data
public class GatewayRoute {

	/**
	 * 路由的id
	 */
	private String id;

	/**
	 * 路由断言集合配置
	 */
	private List<GatewayPredicate> predicates = new ArrayList<>();

	/**
	 * 路由过滤器集合配置
	 */
	private List<GatewayFilter> filters = new ArrayList<>();

	/**
	 * 路由规则转发的目标uri
	 */
	private String uri;

	/**
	 * 路由执行的顺序
	 */
	private int order = 0;
}
