package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 现货交易
 */
@Data
public class CancelExchangeParam implements Serializable {

	private String type;//类型

	@ApiModelProperty(value = "取消id列表")
	private List<String> idList;

}
