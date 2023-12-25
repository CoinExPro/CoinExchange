package org.springblade.plugin.message.model;

import lombok.Data;

import java.util.List;

@Data
public class SocketMsgModel {
	//键
	private String topic;
	//内容
	private String content;
}
