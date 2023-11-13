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

import java.math.BigDecimal;

/**
 * 钱包相关
 */
public interface WalletMapper {
	void addWallet(String id, BigDecimal balance);

	//增加冻结资产
	void addFrozenWallet(String id, BigDecimal frozenBalance);

	//减少冻结资产
	void subFrozenWallet(String id, BigDecimal frozenBalance);

	//移除冻结资产
	void removeFrozenWallet(String id, BigDecimal frozenBalance);

	void addWalletStop(String id, BigDecimal balance);

	//增加冻结资产
	void addFrozenWalletStop(String id, BigDecimal frozenBalance);

	//减少冻结资产
	void subFrozenWalletStop(String id, BigDecimal frozenBalance);

	//移除冻结资产
	void removeFrozenWalletStop(String id, BigDecimal frozenBalance);
}
