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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.StringPool;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 租户拦截器
 *
 *
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MjkjTenantInterceptor extends TenantLineInnerInterceptor {

	/**
	 * 租户处理器
	 */
	private TenantLineHandler tenantLineHandler;
	/**
	 * 租户配置文件
	 */
	private BladeTenantProperties tenantProperties;
	/**
	 * 超管需要启用租户过滤的表
	 */
	private List<String> adminTenantTables = Arrays.asList("blade_top_menu", "blade_dict_biz");

	@Override
	public void setTenantLineHandler(TenantLineHandler tenantLineHandler) {
		super.setTenantLineHandler(tenantLineHandler);
		this.tenantLineHandler = tenantLineHandler;
	}

	@Override
	protected void processInsert(Insert insert, int index, String sql, Object obj) {
		// 未启用租户增强，则使用原版逻辑
		if (!tenantProperties.getEnhance()) {
			super.processInsert(insert, index, sql, obj);
			return;
		}
		if (tenantLineHandler.ignoreTable(insert.getTable().getName())) {
			// 过滤退出执行
			return;
		}
		List<Column> columns = insert.getColumns();
		if (CollectionUtils.isEmpty(columns)) {
			// 针对不给列名的insert 不处理
			return;
		}
		String tenantIdColumn = tenantLineHandler.getTenantIdColumn();
		if (columns.stream().map(Column::getColumnName).anyMatch(i -> i.equals(tenantIdColumn))) {
			// 针对已给出租户列的insert 不处理
			return;
		}
		columns.add(new Column(tenantIdColumn));

		// fixed gitee pulls/141 duplicate update
		List<Expression> duplicateUpdateColumns = insert.getDuplicateUpdateExpressionList();
		if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
			EqualsTo equalsTo = new EqualsTo();
			equalsTo.setLeftExpression(new StringValue(tenantIdColumn));
			equalsTo.setRightExpression(tenantLineHandler.getTenantId());
			duplicateUpdateColumns.add(equalsTo);
		}

		Select select = insert.getSelect();
		if (select != null) {
			this.processInsertSelect(select.getSelectBody());
		} else if (insert.getItemsList() != null) {
			// fixed github pull/295
			ItemsList itemsList = insert.getItemsList();
			if (itemsList instanceof MultiExpressionList) {
				((MultiExpressionList) itemsList).getExpressionLists().forEach(el -> el.getExpressions().add(tenantLineHandler.getTenantId()));
			} else {
				((ExpressionList) itemsList).getExpressions().add(tenantLineHandler.getTenantId());
			}
		} else {
			throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
		}
	}

	/**
	 * 处理 PlainSelect
	 */
	@Override
	protected void processPlainSelect(PlainSelect plainSelect) {
		//#3087 github
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		if (CollectionUtils.isNotEmpty(selectItems)) {
			selectItems.forEach(this::processSelectItem);
		}

		// 处理 where 中的子查询
		Expression where = plainSelect.getWhere();
		processWhereSubSelect(where);

		// 处理 fromItem
		FromItem fromItem = plainSelect.getFromItem();
		List<Table> list = processFromItem(fromItem);
		List<Table> mainTables = new ArrayList<>(list);

		// 处理 join
		List<Join> joins = plainSelect.getJoins();
		if (CollectionUtils.isNotEmpty(joins)) {
			mainTables = processJoins(mainTables, joins);
		}

		// 当有 mainTable 时，进行 where 条件追加
		if (CollectionUtils.isNotEmpty(mainTables) && !doTenantFilters(mainTables)) {
			plainSelect.setWhere(builderExpression(where, mainTables));
		}
	}

	/**
	 * update 语句处理
	 */
	@Override
	protected void processUpdate(Update update, int index, String sql, Object obj) {
		final Table table = update.getTable();
		if (tenantLineHandler.ignoreTable(table.getName())) {
			// 过滤退出执行
			return;
		}
		if (doTenantFilter(table.getName())) {
			// 过滤退出执行
			return;
		}
		update.setWhere(this.andExpression(table, update.getWhere()));
	}

	/**
	 * delete 语句处理
	 */
	@Override
	protected void processDelete(Delete delete, int index, String sql, Object obj) {
		final Table table = delete.getTable();
		if (tenantLineHandler.ignoreTable(table.getName())) {
			// 过滤退出执行
			return;
		}
		if (doTenantFilter(table.getName())) {
			// 过滤退出执行
			return;
		}
		delete.setWhere(this.andExpression(table, delete.getWhere()));
	}

	/**
	 * delete update 语句 where 处理
	 */
	@Override
	protected BinaryExpression andExpression(Table table, Expression where) {
		//获得条件表达式
		EqualsTo equalsTo = new EqualsTo();
		Expression leftExpression = this.getAliasColumn(table);
		Expression rightExpression = tenantLineHandler.getTenantId();
		// 若是超管则不进行过滤
		if (doTenantFilter(table.getName())) {
			leftExpression = rightExpression = new StringValue(StringPool.ONE);
		}
		equalsTo.setLeftExpression(leftExpression);
		equalsTo.setRightExpression(rightExpression);
		if (null != where) {
			if (where instanceof OrExpression) {
				return new AndExpression(equalsTo, new Parenthesis(where));
			} else {
				return new AndExpression(equalsTo, where);
			}
		}
		return equalsTo;
	}

	/**
	 * 增强插件使超级管理员可以看到所有租户数据
	 */
	@Override
	protected Expression builderExpression(Expression currentExpression, List<Table> tables) {
		// 没有表需要处理直接返回
		if (CollectionUtils.isEmpty(tables)) {
			return currentExpression;
		}
		// 租户
		Expression tenantId = tenantLineHandler.getTenantId();
		// 构造每张表的条件
		List<EqualsTo> equalsTos = tables.stream()
			// 租户忽略表
			.filter(x -> !tenantLineHandler.ignoreTable(x.getName()))
			// 超管忽略表
			.filter(x -> !doTenantFilter(x.getName()))
			.map(item -> new EqualsTo(getAliasColumn(item), tenantId))
			.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(equalsTos)) {
			return currentExpression;
		}

		// 注入的表达式
		Expression injectExpression = equalsTos.get(0);
		// 如果有多表，则用 and 连接
		if (equalsTos.size() > 1) {
			for (int i = 1; i < equalsTos.size(); i++) {
				injectExpression = new AndExpression(injectExpression, equalsTos.get(i));
			}
		}

		if (currentExpression == null) {
			return injectExpression;
		}
		if (currentExpression instanceof OrExpression) {
			return new AndExpression(new Parenthesis(currentExpression), injectExpression);
		} else {
			return new AndExpression(currentExpression, injectExpression);
		}

	}

	private List<Table> processFromItem(FromItem fromItem) {
		// 处理括号括起来的表达式
		while (fromItem instanceof ParenthesisFromItem) {
			fromItem = ((ParenthesisFromItem) fromItem).getFromItem();
		}

		List<Table> mainTables = new ArrayList<>();
		// 无 join 时的处理逻辑
		if (fromItem instanceof Table) {
			Table fromTable = (Table) fromItem;
			mainTables.add(fromTable);
		} else if (fromItem instanceof SubJoin) {
			// SubJoin 类型则还需要添加上 where 条件
			List<Table> tables = processSubJoin((SubJoin) fromItem);
			mainTables.addAll(tables);
		} else {
			// 处理下 fromItem
			processOtherFromItem(fromItem);
		}
		return mainTables;
	}

	/**
	 * 处理 sub join
	 *
	 * @param subJoin subJoin
	 * @return Table subJoin 中的主表
	 */
	private List<Table> processSubJoin(SubJoin subJoin) {
		List<Table> mainTables = new ArrayList<>();
		if (subJoin.getJoinList() != null) {
			List<Table> list = processFromItem(subJoin.getLeft());
			mainTables.addAll(list);
			mainTables = processJoins(mainTables, subJoin.getJoinList());
		}
		return mainTables;
	}

	/**
	 * 处理 joins
	 *
	 * @param mainTables 可以为 null
	 * @param joins      join 集合
	 * @return List<Table> 右连接查询的 Table 列表
	 */
	private List<Table> processJoins(List<Table> mainTables, List<Join> joins) {
		// join 表达式中最终的主表
		Table mainTable = null;
		// 当前 join 的左表
		Table leftTable = null;

		if (mainTables == null) {
			mainTables = new ArrayList<>();
		} else if (mainTables.size() == 1) {
			mainTable = mainTables.get(0);
			leftTable = mainTable;
		}

		//对于 on 表达式写在最后的 join，需要记录下前面多个 on 的表名
		Deque<List<Table>> onTableDeque = new LinkedList<>();
		for (Join join : joins) {
			// 处理 on 表达式
			FromItem joinItem = join.getRightItem();

			// 获取当前 join 的表，subJoint 可以看作是一张表
			List<Table> joinTables = null;
			if (joinItem instanceof Table) {
				joinTables = new ArrayList<>();
				joinTables.add((Table) joinItem);
			} else if (joinItem instanceof SubJoin) {
				joinTables = processSubJoin((SubJoin) joinItem);
			}

			if (joinTables != null) {

				// 如果是隐式内连接
				if (join.isSimple()) {
					mainTables.addAll(joinTables);
					continue;
				}

				// 当前表是否忽略
				Table joinTable = joinTables.get(0);

				List<Table> onTables = null;
				// 如果不要忽略，且是右连接，则记录下当前表
				if (join.isRight()) {
					mainTable = joinTable;
					if (leftTable != null) {
						onTables = Collections.singletonList(leftTable);
					}
				} else if (join.isLeft()) {
					onTables = Collections.singletonList(joinTable);
				} else if (join.isInner()) {
					if (mainTable == null) {
						onTables = Collections.singletonList(joinTable);
					} else {
						onTables = Arrays.asList(mainTable, joinTable);
					}
					mainTable = null;
				}

				mainTables = new ArrayList<>();
				if (mainTable != null) {
					mainTables.add(mainTable);
				}

				// 获取 join 尾缀的 on 表达式列表
				Collection<Expression> originOnExpressions = join.getOnExpressions();
				// 正常 join on 表达式只有一个，立刻处理
				if (originOnExpressions.size() == 1 && onTables != null) {
					List<Expression> onExpressions = new LinkedList<>();
					onExpressions.add(builderExpression(originOnExpressions.iterator().next(), onTables));
					join.setOnExpressions(onExpressions);
					leftTable = joinTable;
					continue;
				}
				// 表名压栈，忽略的表压入 null，以便后续不处理
				onTableDeque.push(onTables);
				// 尾缀多个 on 表达式的时候统一处理
				if (originOnExpressions.size() > 1) {
					Collection<Expression> onExpressions = new LinkedList<>();
					for (Expression originOnExpression : originOnExpressions) {
						List<Table> currentTableList = onTableDeque.poll();
						if (CollectionUtils.isEmpty(currentTableList)) {
							onExpressions.add(originOnExpression);
						} else {
							onExpressions.add(builderExpression(originOnExpression, currentTableList));
						}
					}
					join.setOnExpressions(onExpressions);
				}
				leftTable = joinTable;
			} else {
				processOtherFromItem(joinItem);
				leftTable = null;
			}
		}

		return mainTables;
	}

	/**
	 * 判断当前操作是否需要进行过滤
	 *
	 * @param tableName 表名
	 */
	public boolean doTenantFilter(String tableName) {
		return AuthUtil.isAdministrator() && !adminTenantTables.contains(tableName);
	}

	/**
	 * 判断当前操作是否需要进行过滤
	 *
	 * @param tables 表名
	 */
	public boolean doTenantFilters(List<Table> tables) {
		List<String> tableNames = tables.stream().map(Table::getName).collect(Collectors.toList());
		return AuthUtil.isAdministrator() && !CollectionUtil.containsAny(adminTenantTables, tableNames);
	}

}
