package org.springblade.common.aspect;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.ActionLogEvent;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ActionAspect {

	@Pointcut("execution(* org.springblade.cgform.mapper.SqlMapper.execute*(..))")
	public void pointcut() {

	}
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		for (Object arg : point.getArgs()) {
			if (arg instanceof Map) {
				SpringUtil.publishEvent(new ActionLogEvent(arg));
			}
		}
		return point.proceed();
	}


}
