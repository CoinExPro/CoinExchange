package org.springblade.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试消息体
 *
 *    yangkai.shen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletMessageStruct implements Serializable {
	private static final long serialVersionUID = 392365881428311040L;

	private String message;
}
