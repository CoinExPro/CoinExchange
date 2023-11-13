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
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.CommonEntity;
import org.springblade.cgform.model.TreeModel;
import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 *
 * @since 2021-05-20
 */
public interface ICgformFieldService extends BaseService<CgformField> {
	/**
	 * 查询表单的数据
	 * @param tableName 表名
	 * @param headId 表id
	 * @param params 查询条件
	 * @param needList
	 * @return
	 */
	Map<String, Object> queryAutolistPage(String tableName, Long headId, Map<String, Object> params, List<String> needList);


	List<CgformField> queryFormFieldsByTableName(String field1);
}
