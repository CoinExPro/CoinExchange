package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *    jh
 * @version 1.0
 * @date 2022/6/27 11:54
 */
@Data
public class OrderChatRecordParam implements Serializable {

	@NotBlank
	@ApiModelProperty("订单号")
	private String orderId;
	@ApiModelProperty("内容")
	private String content;
	@ApiModelProperty("内容(1文本2图片)")
	private String contentType;
	@ApiModelProperty("用户类型（1买方2卖方）")
	private String userType;



}
