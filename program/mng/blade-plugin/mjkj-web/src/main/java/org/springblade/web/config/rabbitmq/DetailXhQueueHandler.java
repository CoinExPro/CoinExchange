package org.springblade.web.config.rabbitmq;


import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.core.tool.utils.Func;

import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchange;
import org.springblade.web.model.EntrustModel;

import org.springblade.web.service.IMongoService;
import org.springblade.web.service.IWebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

///**
// * 消息队列----处理--现货
// */
//@Slf4j
//@RabbitListener(queues = "web.detail.xh", ackMode = "MANUAL", concurrency = "50")
@Component
public class DetailXhQueueHandler {

	@Autowired
	private CoinMatchFactoryExchange coinMatchFactoryExchange;

	@Autowired
	private IWebService webService;

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;


	@Autowired
	private IMongoService mongoService;





}
