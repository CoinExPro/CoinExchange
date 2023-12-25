package org.springblade.common.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springblade.core.log.event.ApiLogEvent;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.utils.LogAbstractUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class MjkjAspect {


	@Around("execution(* org.springblade..*Controller.*(..)))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		try{
			HttpServletRequest request = WebUtil.getRequest();
			String requestURI = request.getRequestURI();
			List<String> list=new ArrayList<>();
			list.add("/oauth");//认证
			list.add("/wxopen/ht/bindWx");//登录
			for (String url:list) {
				if(requestURI.startsWith(url)){
					return point.proceed();
				}
			}

			//获取类名
			String className = point.getTarget().getClass().getName();
			//获取方法
			String methodName = point.getSignature().getName();
			// 发送异步日志事件
			long beginTime = System.currentTimeMillis();
			//执行方法
			Object result = point.proceed();
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			//记录日志
			this.publishEvent(methodName, className, "请求日志", time);
			return result;
		}catch (Exception e){

		}
		return point.proceed();
	}

	public static void publishEvent(String methodName, String methodClass, String title, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType("1");
		logApi.setTitle(title);
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);
		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap(16);
		event.put("log", logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
