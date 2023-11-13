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
package org.springblade.gateway.provider;

import org.springblade.core.launch.constant.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 *
 */
public class AuthProvider {

	public static final String AUTH_KEY = TokenConstant.HEADER;

	public static final String API_PATH_REG = "(.*)api/v\\d(.*)";

	public static final String API_KEY = "API-Key";

	public static final String API_SECRET_KEY = "Signature";

	public static final String BODY_REQUEST_BODY_PARAMETERS = "request-body-parameters";

	private static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();

	static {
		DEFAULT_SKIP_URL.add("/example");
		DEFAULT_SKIP_URL.add("/oauth/token/**");
		DEFAULT_SKIP_URL.add("/oauth/captcha/**");
		DEFAULT_SKIP_URL.add("/oauth/clear-cache/**");
		DEFAULT_SKIP_URL.add("/oauth/user-info");
		DEFAULT_SKIP_URL.add("/oauth/render/**");
		DEFAULT_SKIP_URL.add("/oauth/callback/**");
		DEFAULT_SKIP_URL.add("/oauth/revoke/**");
		DEFAULT_SKIP_URL.add("/oauth/refresh/**");
		DEFAULT_SKIP_URL.add("/token/**");
		DEFAULT_SKIP_URL.add("/actuator/**");
		DEFAULT_SKIP_URL.add("/v2/api-docs/**");
		DEFAULT_SKIP_URL.add("/auth/**");
		DEFAULT_SKIP_URL.add("/log/**");
		DEFAULT_SKIP_URL.add("/menu/routes");
		DEFAULT_SKIP_URL.add("/menu/auth-routes");
		DEFAULT_SKIP_URL.add("/menu/top-menu");
		DEFAULT_SKIP_URL.add("/tenant/info");
		DEFAULT_SKIP_URL.add("/process/resource-view");
		DEFAULT_SKIP_URL.add("/process/diagram-view");
		DEFAULT_SKIP_URL.add("/manager/check-upload");
		DEFAULT_SKIP_URL.add("/error/**");
		DEFAULT_SKIP_URL.add("/assets/**");
	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return DEFAULT_SKIP_URL;
	}

}
