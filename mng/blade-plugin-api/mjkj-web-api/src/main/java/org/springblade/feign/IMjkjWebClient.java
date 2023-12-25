package org.springblade.feign;

import org.springblade.core.tool.api.R;
import org.springblade.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 前段相关
 */
@FeignClient(
	value = "mjkj-web"
)
public interface IMjkjWebClient {

	String API_PREFIX = "/client";

	String SEND_YX = API_PREFIX + "/send-yx";//解约
	String SEND_EMAIL = API_PREFIX + "/send-email";//发送邮箱验证码
	String SEND_PHONE = API_PREFIX + "/send-phone";//发送手机验证码

	String HANDLE_XXL_JOB_ORDER_TIME_OUT = API_PREFIX + "/handleXxlJob-orderTimeOut";//更新过期订单
	String ORDER_APPEAL_TIME_OUT = API_PREFIX + "/order_appeal_timeout";//订单申述过期

	String RESET_MATH = API_PREFIX + "/reset_math";//重置引擎
	String MARKET_PROFIT = API_PREFIX + "/market-profit";//市值发放收益
	String INVITE_REWARD = API_PREFIX + "/invite-reward";//直推奖励
	String CONTRACT_PROFIT = API_PREFIX + "/contract-profit";//合约发放收益
	String RESET_MEMBER_LEVEL = API_PREFIX + "/reset-member-level";//重置会员等级
	String ORDER_STATUS = API_PREFIX + "/order-status"; //修改订单状态

	String WALLET_INFO = API_PREFIX + "/wallet_info";//钱包信息

	String MEMBER_ASSERT = API_PREFIX + "/member_assert";//用户资产
	String CREATE_WALLET = API_PREFIX + "/create_wallet";//创建钱包信息
	String WALLET_ADDWALLET = API_PREFIX + "/wallet_addWallet";//增加可用余额
	String WALLET_SUBWALLET = API_PREFIX + "/wallet_subWallet";//减少可用资产
	String WALLET_ADDFROZENWALLET = API_PREFIX + "/wallet_addFrozenWallet";//增加冻结资产
	String WALLET_SUBFROZENWALLET = API_PREFIX + "/wallet_subFrozenWallet";//减少冻结资产
	String WALLET_REMOVEFROZENWALLET = API_PREFIX + "/wallet_removeFrozenWallet";//移除冻结资产
	String WALLET_BUY_SELL = API_PREFIX + "/wallet_buy_sell";//买卖


	String WEALTH_CBSX_PROFIT = API_PREFIX + "/wealth-cbsx-profit";//理财管理-存币生息-发放收益
	String WEALTH_CBSX_GOODS_STATUS = API_PREFIX + "/wealth-cbsx-goods-status";//理财管理-存币生息-商品状态

	String SS_BCXX = API_PREFIX + "/ss_bcxx";//申诉-补充信息

	String HANDLE_NIGHT_FEE = API_PREFIX + "/handle-night-fee"; //处理过夜费

	String HANDLE_CONTRACT_RG = API_PREFIX + "/handle-contract-rg"; //人工强爆强平

	String ENTRUST = API_PREFIX + "/entrust";
	String ENTRUST_CANCEL = API_PREFIX + "/entrust/cancel";
	String ENTRUST_QUERY = API_PREFIX + "/entrust/query";

	String MEMBER_API_KEY = API_PREFIX + "/member/apikey";

	//获取钱包信息
	@PostMapping(WALLET_INFO)
	R<Map<String, Object>> getWalletInfo(@RequestBody WalletGetParam param);

	@PostMapping(MEMBER_ASSERT)
	R<Map<String, Object>> getMemberAssert(@RequestParam("id") String memberId);

	@PostMapping(CREATE_WALLET)
	R<Map<String, Object>> createWallet(@RequestParam("id") String id);

	//增加可用资产
	@PostMapping(WALLET_ADDWALLET)
	R addWallet(@RequestBody AddWalletParam param);

	//减少可用资产
	@PostMapping(WALLET_SUBWALLET)
	R subWallet(@RequestBody SubWalletParam param);

	//增加冻结资产
	@PostMapping(WALLET_ADDFROZENWALLET)
	R addFrozenWallet(@RequestBody AddFrozenWalletParam param);

	//减少冻结资产
	@PostMapping(WALLET_SUBFROZENWALLET)
	R subFrozenWallet(@RequestBody SubFrozenWalletParam param);

	//移除冻结资产
	@PostMapping(WALLET_REMOVEFROZENWALLET)
	R removeFrozenWallet(@RequestBody RemoveWalletParam param);

	//买卖
	@PostMapping(WALLET_BUY_SELL)
	R walletBuySell(@RequestBody BuySellWalletParam param);



	/*
	 * 异地登录邮箱
	 */
	@PostMapping(SEND_YX)
	Boolean sendYx(@RequestBody SendYxParam param);

	/*
	 * 发送邮箱
	 */
	@PostMapping(SEND_EMAIL)
	Boolean sendEmail(@RequestBody SendEmailParam param);

	/*
	 * 发送手机号
	 */
	@PostMapping(SEND_PHONE)
	Boolean sendPhone(@RequestBody SendPhoneParam param);

	/**
	 * 每分钟轮询过期未付款订单
	 */
	@PostMapping(HANDLE_XXL_JOB_ORDER_TIME_OUT)
	void orderTimeOutJobHandler();

	/**
	 * 申述过期转客服
	 */
	@PostMapping(ORDER_APPEAL_TIME_OUT)
	void orderAppealTimeOutJobHandler();

	/**
	 * 重置引擎
	 */
	@PostMapping(RESET_MATH)
	void resetMath();

	/**
	 * 市值发放收益
	 */
	@PostMapping(MARKET_PROFIT)
	void marketProfit();

	/**
	 * 直推奖励
	 */
	@PostMapping(INVITE_REWARD)
	void inviteReward();

	/**
	 * 合约发放收益
	 */
	@PostMapping(CONTRACT_PROFIT)
	void contractProfit(@RequestParam("param") String param);

	/**
	 * 重置用户等级
	 */
	@PostMapping(RESET_MEMBER_LEVEL)
	void resetMemberLevel(@RequestParam("param") String operateMemberI);


	/**
	 * 修改订单状态
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	@PostMapping(ORDER_STATUS)
	R changeOrderStatus(@RequestParam("id") String id, @RequestParam("status") String status);

	/**
	 * 理财-存币生息，发放收益
	 */
	@PostMapping(WEALTH_CBSX_PROFIT)
	void wealthCbsxProfit();

	/**
	 * 交易存币生息是否已结束
	 *
	 * @return
	 */
	@PostMapping(WEALTH_CBSX_GOODS_STATUS)
	void changeCbsxGoodsStatus();

	/**
	 * 申诉-补充信息
	 *
	 * @param appeal
	 */
	@PostMapping(SS_BCXX)
	void appeal(@RequestBody Appeal appeal);

	/**
	 * 处理过夜费
	 */
	@PostMapping(HANDLE_NIGHT_FEE)
	void handleNightFee();

	/**
	 * 人工处理强爆强平
	 *
	 * @param logContractId 合约id
	 * @param type          类型 1=强爆 2=强平
	 * @return
	 */
	@PostMapping(HANDLE_CONTRACT_RG)
	R handleContractRg(@RequestParam("logContractId") String logContractId, @RequestParam("type") String type);

	/**
	 * 委托
	 */
	@PostMapping(ENTRUST)
	R entrust(@RequestBody EntrustParamApi param);

	@PostMapping(ENTRUST_CANCEL)
	R cancelEntrust(@RequestBody CancelExchangeParamDTO param);

	@GetMapping(ENTRUST_QUERY)
	R queryEntrust(@RequestParam("entrustType") String entrustType,
				   @RequestParam("entrustFlag")String entrustFlag,
				   @RequestParam("symbol")String symbol);

	@PostMapping(MEMBER_API_KEY)
	R<Map<String,Object>> getApiKey(@RequestParam("apiKey") String apiKey);
}
