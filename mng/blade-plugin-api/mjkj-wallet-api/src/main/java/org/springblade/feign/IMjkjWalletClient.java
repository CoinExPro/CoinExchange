package org.springblade.feign;

import org.springblade.core.tool.api.R;
import org.springblade.model.CreateWalletAddressModelParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 钱包相关
 */
@FeignClient(
	value = "mjkj-wallet"
)
public interface IMjkjWalletClient {

	String API_PREFIX = "/client-wallet";

	String GET_BALANCE = API_PREFIX + "/get-balance";
	String GET_ADDRESS = API_PREFIX + "/get-address";//创建钱包地址
	String TRANSFER = API_PREFIX + "/transfer";//转账
	String SYNCBLOCK = API_PREFIX + "/syncblock";//同步区块
	String JOB_HANDLE_CHAIN = API_PREFIX + "/job-handleChain";//处理连上信息
	String JOB_COLLECT = API_PREFIX + "/job-collect";//归集处理
	String JOB_SCAN_BLOCK = API_PREFIX + "/job-scan-block";//扫块
	String JOB_BALANCE_REMIND = API_PREFIX + "/job-balance-remind";
	String CHECK_ADDRESS = API_PREFIX + "/check-address";//校验地址
	String JOB_QRZT= API_PREFIX + "/job-qrzt";//确认状态
	String GET_CHAIN_BALANCE = API_PREFIX + "/get-chain-balance";//获取链上余额
	/**
	 * 获取钱包
	 *
	 * @return
	 */
	@PostMapping(GET_ADDRESS)
	R<String> getAddress(@RequestParam("chainType") String chainType);


	/**
	 * 转账
	 *
	 * @param chainType       ERC20 TRC20 BTC
	 * @param taskId          任务id
	 * @param toAddress       到账地址
	 * @param amount          转账金额
	 * @param contractAddress 合约地址
	 * @return
	 * @throws Exception
	 */
	@PostMapping(TRANSFER)
	void transfer(@RequestParam("chainType") String chainType, @RequestParam("taskId") String taskId, @RequestParam("toAddress") String toAddress, @RequestParam("amount") BigDecimal amount, @RequestParam("contractAddress") String contractAddress);

	/**
	 * 定时器 - 处理链上信息(提币)
	 *
	 * @return
	 */
	@PostMapping(JOB_HANDLE_CHAIN)
	void handleChain() throws Exception;

	/**
	 * 定时器 - 归集
	 *
	 * @return
	 */
	@PostMapping(JOB_COLLECT)
	void jobCollect() throws Exception;

	/**
	 * 定时器 - 同步区块所有链路区块
	 *
	 * @return
	 */
	@PostMapping(JOB_SCAN_BLOCK)
	void jobScanBlock() throws Exception;

	/**
	 * 定时器-系统余额提醒
	 *
	 * @throws Exception
	 */
	@PostMapping(GET_BALANCE)
	R<List<Map<String, Object>>> getBalance() throws Exception;

	@PostMapping(JOB_BALANCE_REMIND)
	void balanceRemind() throws Exception;

	@PostMapping(CHECK_ADDRESS)
	R<Boolean> checkAddress(@RequestParam("chainType") String chainType, @RequestParam("address") String address);

	/**
	 * 获取链上余额
	 *
	 * @return
	 */
	@PostMapping(GET_CHAIN_BALANCE)
	R<BigDecimal> getChainBalance(@RequestParam("chainType") String chainType, @RequestParam("address") String address, @RequestParam("coinName") String coinName);

}
