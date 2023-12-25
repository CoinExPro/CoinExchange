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
package org.springblade.cgform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.mapper.CgformFieldMapper;
import org.springblade.cgform.mapper.CgformHeadMapper;
import org.springblade.cgform.mapper.SqlMapper;
import org.springblade.cgform.model.CommonEntity;
import org.springblade.cgform.model.TreeModel;
import org.springblade.cgform.service.ICgformFieldService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.SqlSymbolUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 服务实现类
 *
 *
 * @since 2021-05-20
 */
@Slf4j
@Service
public class CgformFieldServiceImpl extends BaseServiceImpl<CgformFieldMapper, CgformField> implements ICgformFieldService {

    @Autowired
    private CgformHeadMapper headMapper;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private BladeRedis bladeRedis;

    public void executeInsertSQL(Map<String, Object> map) {
        sqlMapper.executeInsertSQL(map);
    }



	@Override
	public Map<String, Object> queryAutolistPage(String tableName, Long headId, Map<String, Object> params, List<String> needList) {
		HashMap<String, Object> resultMap = new HashMap<>();
		String redisKey="mjkj_sql:tableName_"+tableName+":headId_"+headId+":md5_"+Func.md5Hex(JsonUtil.toJson(params));

		LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformField::getCgformHeadId, headId);
		wrapper.orderByAsc(CgformField::getOrderNum);
		//获取所以的字段
		List<CgformField> fieldList = this.list(wrapper);

		String sqlStr="";
	/*	if(bladeRedis.exists(redisKey)){
			sqlStr =bladeRedis.get(redisKey);
		}else{*///redis 不存在
		StringBuffer sbf = new StringBuffer();

		// 拼接查询这张表的数据的SQL语句:
		SqlSymbolUtil.getSelect(tableName, fieldList, sbf);
		// 根据数据库类型制造额外SQL
		String sql = SqlSymbolUtil.getByDataType(fieldList, params, needList);
		// 制造额外SQL(查询条件)
		String sql1 = SqlSymbolUtil.getByParams(params);
		// 拼接出完整的SQL查询语句
		sbf.append(" where is_deleted =0  " + sql + sql1);
		// 获取column字段属性
		Object column = params.get("column");
		// column != null, 制造获取排序规则:
		if (column != null) {
			String columnStr = column.toString();
			// 获取排序字段 "asc" / "desc"
			String orderStr = params.get("order").toString();
			// column 等于转换成驼峰命名后的确是表额外属性集合内的某个字段,根据这个字段制造查询排序语句:
			if (this.orderBy(columnStr, fieldList)) {
				String orderBy = ConvertUtils.camelToUnderline(columnStr);
				if(Func.equals(orderBy,"id")){
					orderBy="("+orderBy+" +0 )";
				}
				// 拼接排序语句
				sbf.append(" ORDER BY " + orderBy);
				if ("asc".equals(orderStr)) {
					sbf.append(" asc");
				} else {
					sbf.append(" desc");
				}
			}
		}
		if(params.containsKey("allIdFlag") && MjkjUtils.getMap2Integer(params,"allIdFlag")==1){//获取所有
			sbf=new StringBuffer();
			sbf.append("select id from "+tableName+" t where is_deleted =0  " + sql + sql1);
		}
		sqlStr=sbf.toString();
		//写入redis 5分钟
		bladeRedis.setEx(redisKey,sqlStr,300L);
		//}


		if(params.containsKey("allIdFlag") && MjkjUtils.getMap2Integer(params,"allIdFlag")==1){//获取所有
			List<Map<String, Object>> dataList = sqlMapper.queryListBySqlList(sqlStr);
			// 如果没有查到数据,封装
			resultMap.put("total", 0);
			resultMap.put("fieldList", new ArrayList<>());
			resultMap.put("records",new ArrayList<>());
			resultMap.put("idList",dataList);
			return resultMap;
		}

		// pageSize是否为null?是的话,取10
		Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
		// 如果pageSize = -521
		if (pageSzie == -521) {
			// 根据查询条件查询数据:
			List<Map<String, Object>> dataList = sqlMapper.queryListBySqlList(sqlStr);
			// 如果查询到了数据
			if (dataList != null && dataList.size() != 0) {
				resultMap.put("total", dataList.size());// 封装数据总条数
				resultMap.put("fieldList", fieldList);// 封装表额外字段
				//判断是否包含
				Integer cou = baseMapper.getBlobCou(headId);
				if(Func.isNotEmpty(cou) && cou>0){
					resultMap.put("records", SqlSymbolUtil.handleClob(dataList)); // 封装数据
				}else{
					resultMap.put("records", dataList); // 封装数据
				}
			} else {
				resultMap.put("total", 0);// 如果没有查到数据,封装
				resultMap.put("fieldList", fieldList); // 封装字段集合
				List<Map<String, Object>> list=new ArrayList<>();// 创建ArrayList
				resultMap.put("records",list );// 封装数据为空的ArrayList
			}
		} else {
			Integer pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
			Page<Map<String, Object>> page = new Page<>(pageNo, pageSzie);// 创建分页对象,封装参数
			IPage<Map<String, Object>> ipage = sqlMapper.selectPageBySqlList(page, sqlStr); // 分页查询
			resultMap.put("total", ipage.getTotal());// 封装总条数
			Integer cou = baseMapper.getBlobCou(headId);
			if(Func.isNotEmpty(cou) && cou>0){
				resultMap.put("records", SqlSymbolUtil.handleClob(ipage.getRecords())); // 封装数据
			}else{
				resultMap.put("records", ipage.getRecords()); // 封装数据
			}
		}

		return resultMap;
	}
	public boolean orderBy(String str, List<CgformField> list) {
		boolean flag = false;
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			CgformField field = (CgformField) iterator.next();
			if (ConvertUtils.camelToUnderline(str).equals(field.getDbFieldName())) {
				flag = true;
				break;
			}
		}


		return flag;
	}

	public List<CgformField> queryFormFieldsByTableName(String tableName) {
		CgformHead head = headMapper.selectOne((Wrapper) (new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, tableName));
		if (Func.isEmpty(head)) {
			return null;
		}
		LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
		wrapper.eq(CgformField::getCgformHeadId, head.getId());
		return this.list(wrapper);
	}



}
