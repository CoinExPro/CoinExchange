package org.springblade.common;

import org.springframework.context.ApplicationEvent;

public class ActionLogEvent extends ApplicationEvent {
	public ActionLogEvent(Object source) {
		super(source);
	}
}
