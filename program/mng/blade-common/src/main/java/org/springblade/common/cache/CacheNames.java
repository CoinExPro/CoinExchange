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
package org.springblade.common.cache;

/**
 * 缓存名
 *
 *
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * EMAIL验证码key
	 */
	String EMAIL_KEY = "blade:auth::blade:email:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "blade:user::blade:fail:";

}
