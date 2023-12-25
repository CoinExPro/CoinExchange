package org.springblade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 现货交易
 */
@Data
public class CancelExchangeParamDTO implements Serializable {

	private String type;//类型

	@ApiModelProperty(value = "取消id列表")
	private List<String> idList;

}
