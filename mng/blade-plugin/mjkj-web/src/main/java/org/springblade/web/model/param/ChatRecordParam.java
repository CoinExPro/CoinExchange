package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 客服聊天
 */
@Data
public class ChatRecordParam implements Serializable {

	@NotBlank
	@ApiModelProperty("聊天id")
	private String chatId;
	@ApiModelProperty("内容")
	private String content;
	@ApiModelProperty("内容(1文本2图片)")
	private String contentType;
}
