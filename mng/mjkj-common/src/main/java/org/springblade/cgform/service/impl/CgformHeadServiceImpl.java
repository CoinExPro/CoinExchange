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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.entity.CgformEnhanceJava;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.entity.CgformIndex;
import org.springblade.cgform.enums.CgformEnum;
import org.springblade.cgform.mapper.CgformHeadMapper;
import org.springblade.cgform.model.CgformModel;
import org.springblade.cgform.model.OnlGenerateModel;
import org.springblade.cgform.model.TreeDataModel;
import org.springblade.cgform.model.database.DbReadTableUtil;
import org.springblade.cgform.model.generate.impl.CodeGenerateOne;
import org.springblade.cgform.model.generate.impl.CodeGenerateOneToMany;
import org.springblade.cgform.model.generate.pojo.ColumnExpVo;
import org.springblade.cgform.model.generate.pojo.ColumnVo;
import org.springblade.cgform.model.generate.pojo.TableVo;
import org.springblade.cgform.model.generate.pojo.onetomany.MainTableVo;
import org.springblade.cgform.model.generate.pojo.onetomany.SubTableVo;
import org.springblade.cgform.service.*;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjjyConfig;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.db.DataBaseConfig;
import org.springblade.config.db.TableModel;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.exception.DBException;
import org.springblade.config.service.DbTableHandleI;
import org.springblade.config.util.*;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 *
 * @since 2021-05-20
 */
@Slf4j
@Service
public class CgformHeadServiceImpl extends BaseServiceImpl<CgformHeadMapper, CgformHead> implements ICgformHeadService {


	public List<Map<String, Object>> queryListData(String sql) {
		return this.baseMapper.queryList(sql);
	}















}
