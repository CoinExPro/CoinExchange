package org.springblade.config.pool;

import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.model.AccumulatorRecursiveActionParam;
import org.springblade.cgform.service.ICgformEnhanceSqlService;
import org.springblade.config.exception.BusinessException;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;


@Slf4j
public class AccumulatorRecursiveAction extends RecursiveAction {
	private final int start;

	private final int end;

	private AccumulatorRecursiveActionParam param;

	private final int LIMIT = 200;
	private ICgformEnhanceSqlService sqlService;
	private ServletRequestAttributes sra;


	public AccumulatorRecursiveAction(int start, int end, AccumulatorRecursiveActionParam param, ServletRequestAttributes sra) {
		this.start = start;
		this.end = end;
		this.param = param;
		this.sqlService = param.getSqlService();
		this.sra = sra;
	}


	@Override
	protected void compute() {

	}
}
