package org.springblade.feign;

import org.springblade.model.MatchExchangeAddParam;
import org.springblade.model.MatchExchangeDelParam;
import org.springblade.model.MatchExchangeRefreshParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 撮合相关
 */
@FeignClient(
	value = "mjkj-match"
)
public interface IMjkjMatchClient {

	String API_PREFIX = "/client-match";

	String MATCH_ADD_EXCHANGE = API_PREFIX + "/add-exchange";//新增交易

	String MATCH_DEL_EXCHANGE = API_PREFIX + "/del-exchange";//删除交易

	String MATCH_REFRESH_EXCHANGE = API_PREFIX + "/refresh-exchange";//刷新交易

	String JOB_RESET_REDIS = API_PREFIX + "/reset-redis";//定时器重置redis

	String JOB_REFRESH_EXCHANGE = API_PREFIX + "/job_refresh_exchange";//定时器触发撮合


	//新增交易
	@PostMapping(MATCH_ADD_EXCHANGE)
	void addExchange(@RequestBody MatchExchangeAddParam param);

	//删除交易
	@PostMapping(MATCH_DEL_EXCHANGE)
	void delExchange(@RequestBody MatchExchangeDelParam param);

	//刷新交易
	@PostMapping(MATCH_REFRESH_EXCHANGE)
	void refreshExchange(@RequestBody MatchExchangeRefreshParam param);

	//刷新交易
	@PostMapping(JOB_RESET_REDIS)
	void resetRedis();

	//定时器触发撮合
	@PostMapping(JOB_REFRESH_EXCHANGE)
	void jobRefreshExchange();


}
