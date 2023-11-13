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
package org.springblade.web.mapper;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 魔晶科技
 */
public interface WebMapper {
	List<Map<String, Object>> getPayment(@Param("id") String id);

	Map<String, Object> getMemberSum(@Param("id") String id, @Param("level")Integer level);

	//获取用户
	Map<String, Object> getMemberProfit(@Param("id")String id, @Param("type")Integer type);

	//用户产生佣金
	Map<String, Object> getMemberCsyj(@Param("id")String id);
}
