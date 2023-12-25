package org.springblade.plugin.message.feign;


import org.springblade.core.tool.api.R;
import org.springblade.plugin.message.model.SocketMsgModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Message Feign接口类
 *
 *    weikun
 */
@FeignClient(
	value = "blade-message"
)
public interface IMessageClient {

	String API_PREFIX = "/client";
	String SEND_SOCKET_MSG = API_PREFIX + "/send-socket-msg";

	/**
	 * 发送消息
	 *
	 * @param model 消息实体
	 * @return
	 */
	@PostMapping(SEND_SOCKET_MSG)
	R<Boolean> sendSocketMsg(@RequestBody SocketMsgModel model);


}
