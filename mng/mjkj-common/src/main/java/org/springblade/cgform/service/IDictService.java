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

import org.springblade.cgform.entity.SysDict;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.model.DuplicateCheckVo;
import org.springblade.cgform.model.TreeSelectModel;
import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务类
 *
 *
 * @since 2021-05-27
 */
public interface IDictService extends BaseService<SysDict> {

	List<String> queryTableDictByKeys(String table, String text, String code, String keys);
	@Deprecated
	List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);
	/**
	 * 13获取表数据字典
	 * @param table
	 * @param text
	 * @param code
	 * @return
	 */
	List<DictModel> queryTableDictItemsByCode(String table, String text, String code);
	/**
	 * 10获取数据字典
	 * @param code
	 * @return
	 */
	public List<DictModel> queryDictItemsByCode(String code);
	public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

}
