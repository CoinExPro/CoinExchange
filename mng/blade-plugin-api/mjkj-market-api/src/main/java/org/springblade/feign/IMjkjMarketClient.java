package org.springblade.feign;

import org.springblade.core.tool.api.R;
import org.springblade.model.DeptHModel;
import org.springblade.model.DetailMessageModel;
import org.springblade.model.KlineMessageModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 市场行情相关
 */
@FeignClient(
	value = "mjkj-market"
)
public interface IMjkjMarketClient {

	String API_PREFIX = "/marketClient";

	String HANDLE_XXL_JOB = API_PREFIX + "/handleXxlJob";
	String HANDLE_XXL_JOB_PING = API_PREFIX + "/handleXxlJob-ping";
	String HANDLE_XXL_JOB_CONNECTSOCKET = API_PREFIX + "/handleXxlJob-connectSocket";
	String HANDLE_XXL_JOB_SUBSOCKET = API_PREFIX + "/handleXxlJob-subSocket";
	String HANDLE_XXL_JOB_SUBKLINESOCKET = API_PREFIX + "/handleXxlJob-subKlineSocket";
	String HANDLE_XXL_JOB_REQKLINESOCKET = API_PREFIX + "/handleXxlJob-reqKlineSocket";
	String HANDLE_XXL_JOB_REQKLINESOCKET_NUM = API_PREFIX + "/handleXxlJob-reqKlineSocketNum";

	String HANDLE_XXL_JOB_OKX_QUOTEDPRICE = API_PREFIX + "/handleXxlJob-quotedPrice";//ok 易抓取汇率

	String HANDLE_XXL_JOB_BORROW_INTEREST= API_PREFIX + "/borrow-interest";//就算借币利息

	String MESSAGE_INITDATA= API_PREFIX + "/message-initdata";//消息推送初始数据

	String MESSAGE_CONTRACT_DETAIL= API_PREFIX + "/message-contract-detail";//合约详情

	String MESSAGE_WEB_DETAIL= API_PREFIX + "/message-web-detail";//详情

	String MESSAGE_WEB_KLINE= API_PREFIX + "/message-web-kline";//K线

	String MESSAGE_WEB_DEPTH= API_PREFIX + "/message-web-depth";//深度

	String MESSAGE_COINHOUSE_XH_ENTRUST = API_PREFIX + "/message-coinhouse-xh-entrust";//自定义委托

	String MESSAGE_COINHOUSE_XH_EXCHANGE = API_PREFIX + "/message-coinhouse-xh-exchange";//自定义成交

	String MESSAGE_COINHOUSE_XH_KLINE_GENERATE = API_PREFIX + "/message-coinhouse-xh-kline-generate";//自定义生成K线

	String GET_NOW_XH_PRICE = API_PREFIX + "/get-now-xh-price";//获取现货当前价格

	/**
	 * ping pong
	 */
	@PostMapping(HANDLE_XXL_JOB_PING)
	void ping();


	/**
	 * 订阅心跳，1分钟
	 */
	@PostMapping(HANDLE_XXL_JOB_CONNECTSOCKET)
	void checkSocketConnect();

	/**
     * 订阅交易所消息
     */
	@PostMapping(HANDLE_XXL_JOB_SUBSOCKET)
	void socketSubJobHandler(@RequestParam String paramType);

	/**
	 * 订阅K线心跳，1分钟
	 */
	@PostMapping(HANDLE_XXL_JOB_SUBKLINESOCKET)
	void socketSubKlineJobHandler(@RequestParam String paramType);

	/**
	 * 获取K线心跳，10分钟
	 */
	@PostMapping(HANDLE_XXL_JOB_REQKLINESOCKET)
	void socketReqKlineJobHandler(@RequestParam String paramType);

	/**
	 * 获取K线心跳，10分钟
	 */
	@PostMapping(HANDLE_XXL_JOB_REQKLINESOCKET_NUM)
	void socketReqKlineNumJobHandler();

	/**
	 * 定时获取汇率
	 */
	@PostMapping(HANDLE_XXL_JOB_OKX_QUOTEDPRICE)
	void getQuotedPrice();

	/**
	 * 定时获取汇率
	 */
	@PostMapping(HANDLE_XXL_JOB_BORROW_INTEREST)
	void borrowInterestJobHandler();



	/**
	 * 消息初始数据
	 */
	@GetMapping(MESSAGE_INITDATA)
	R messageInitdata(@RequestParam("type") String type);

	/**
	 * 发送详情消息
	 */
	@PostMapping(MESSAGE_CONTRACT_DETAIL)
	void messageContractDetail(@RequestBody DetailMessageModel model);

	/**
	 * 发送详情消息
	 */
	@PostMapping(MESSAGE_WEB_DETAIL)
	void messageWebDetail(@RequestParam String contractType,@RequestParam String symbolName,@RequestParam BigDecimal nowPrice);

	/**
	 * 发送K线消息
	 */
	@PostMapping(MESSAGE_WEB_KLINE)
	void messageWebKline(@RequestBody KlineMessageModel model);

	/**
	 * 发送深度消息
	 */
	@PostMapping(MESSAGE_WEB_DEPTH)
	void messageWebDeptH(@RequestBody DeptHModel model);


	//自定义深度-委托，取消委托，成交的时候触发
	@GetMapping(MESSAGE_COINHOUSE_XH_ENTRUST)
	void messagCoinhouseEntrust(@RequestParam("symbolName") String symbolName);

	//自定义成交
	@GetMapping(MESSAGE_COINHOUSE_XH_EXCHANGE)
	void messagCoinhouseExchange(@RequestParam("logExchangeDetailId") String logExchangeDetailId);



	//自定义生成K线
	@GetMapping(MESSAGE_COINHOUSE_XH_KLINE_GENERATE)
	void messageCoinhouseXhKlineGenerate();

	/**
	 * 获取当前价格
	 * @param symbolName
	 * @return
	 */
	@GetMapping(GET_NOW_XH_PRICE)
	BigDecimal getXhNowPrice(@RequestParam("symbolName") String symbolName);

}
