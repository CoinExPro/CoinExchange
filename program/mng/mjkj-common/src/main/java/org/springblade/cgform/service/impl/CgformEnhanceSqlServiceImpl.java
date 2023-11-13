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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springblade.cgform.entity.CgformEnhanceJava;
import org.springblade.cgform.entity.CgformEnhanceSql;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.mapper.CgformEnhanceSqlMapper;
import org.springblade.cgform.mapper.SqlMapper;

import org.springblade.cgform.service.ICgformEnhanceJavaService;
import org.springblade.cgform.service.ICgformEnhanceSqlService;
import org.springblade.cgform.service.ICgformFieldService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.pool.AccumulatorRecursiveAction;
import org.springblade.config.util.SqlSymbolUtil;
import org.springblade.config.util.converter.ConverterUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;

/**
 * sql增强 服务实现类
 *
 *
 * @since 2021-05-22
 */
@Service
@Slf4j
public class CgformEnhanceSqlServiceImpl extends BaseServiceImpl<CgformEnhanceSqlMapper, CgformEnhanceSql> implements ICgformEnhanceSqlService {


	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;


	@Autowired
	private ICgformEnhanceJavaService javaService;

	@Autowired
	private SqlMapper sqlMapper;


	@Autowired
	private ICgformFieldService cgformFieldService;
	//sql增强 列表 ok
	@Override
	public void executeEnhanceSqlList(CgformHead head, String buttonCode,  Map<String, Object> params){
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getId());
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}

		LambdaQueryWrapper<CgformField> fieldWrapper = new LambdaQueryWrapper<>();
		fieldWrapper.eq(CgformField::getCgformHeadId, head.getId());
		fieldWrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> fieldList = cgformFieldService.list(fieldWrapper);

		// 根据数据库类型制造额外SQL
		String cgbSql = enhanceSql.getCgbSql();
		String sql = SqlSymbolUtil.getEnhanceByDataType(fieldList, params, null);

		if(cgbSql.contains("#{online_user_id}")){
			cgbSql = cgbSql.replace("#{online_user_id}", "'"+AuthUtil.getUserId()+"'");//id 默认值
		}


		String executeSql =cgbSql.replaceAll("\\$\\{mjkj_where}",sql);

		//分页参数
		Page page = MjkjUtils.getPage(params);

		params.put("executeSql",executeSql);
		Page pages = baseMapper.executeSql(page,executeSql,params);
		MjkjUtils.setPageResult(params, pages);
	}

	@Override
	public void saveBatchCodeOnlineTable(CgformHead head, List<CgformField> fieldList, List<Map<String, Object>> dataList,String batchCode) {
		SqlSession sqlSession = null;

		try {
			ConverterUtil.converter(2, dataList, fieldList);
			sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlMapper sqlMapper = (SqlMapper) sqlSession.getMapper(SqlMapper.class);
			short len = 1000;
			if (len >= dataList.size()) {//小于1000条
				for (int i = 0; i < dataList.size(); ++i) {
					String jsonStr = JSON.toJSONString(dataList.get(i));
					this.executeBatchSql(jsonStr, head, fieldList, sqlMapper,batchCode);
				}
			} else {//大于等于1000条
				for (int i = 0; i < dataList.size(); ++i) {
					String jsonStr = JSON.toJSONString(dataList.get(i));
					this.executeBatchSql(jsonStr, head, fieldList, sqlMapper,batchCode);
					if (i % len == 0) {
						sqlSession.commit();
						sqlSession.clearCache();
					}
				}
			}

			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}

	private void executeSql(String jsonStr, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper) throws BusinessException {
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		int cou = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_IMPORT, MjkjConstant.ENHANCE_START, head, jsonObject);//获取sql增强
		String tableName = head.getTableName();
		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			if (1 == cou) {
				Map<String, Object> map = SqlSymbolUtil.getInsertSql(tableName, fieldList, jsonObject);
				sqlMapper.executeInsertSQL(map);
			} else if (2 == cou) {
				Map<String, Object> map = SqlSymbolUtil.getUpdateSql(tableName, fieldList, jsonObject);
				sqlMapper.executeUpdatetSQL(map);
			}
		}
	}

	//批量添加
	private void executeBatchSql(String jsonStr, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper,String batchCode) throws BusinessException {
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		int cou = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_IMPORT, MjkjConstant.ENHANCE_START, head, jsonObject);//获取sql增强
		String tableName = head.getTableName();
		jsonObject.put("batch_code",batchCode);
		if (1 == cou) {
			Map<String, Object> map = SqlSymbolUtil.getInsertSql(tableName, fieldList, jsonObject);
			sqlMapper.executeInsertSQL(map);
		} else if (2 == cou) {
			Map<String, Object> map = SqlSymbolUtil.getUpdateSql(tableName, fieldList, jsonObject);
			sqlMapper.executeUpdatetSQL(map);
		}

	}


}
