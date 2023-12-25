package org.springblade.web.model.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 划转
 */
@Data
public class TransParam implements Serializable {

	private String from;//发出方
	private String to;//接收方
	private String coinSymbol;//币种
	private BigDecimal amount;//金额

	private String exchangeCoinId;//交易对id
}
