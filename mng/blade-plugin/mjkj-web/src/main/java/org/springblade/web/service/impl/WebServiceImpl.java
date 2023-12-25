package org.springblade.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.WalletConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.market.MarketKlineUtils;
import org.springblade.config.util.DateUtils;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.web.mapper.WebMapper;
import org.springblade.web.model.EntrustModel;
import org.springblade.web.model.param.*;
import org.springblade.web.service.IWalletService;
import org.springblade.web.service.IWebService;
import org.springblade.web.utils.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class WebServiceImpl implements IWebService {

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@Autowired
	private BladeRedis bladeRedis;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private WebMapper webMapper;

	@Lazy
	@Autowired
	private IWalletService walletService;

	@Autowired
	private BladeRedis redis;

	private static final String COIN_MAP_COIN_SYMBOL = "coin:map:coin:id-coin";
	private static final String COIN_MAP_FIAT_CURRENCY = "coin:map:fiat:id-currency";
	private static final String PAYMENT_KEY = "coin:map:payment:id-payment";

	//获取语言
	@Override
	public String getLanguage(String code) {
		try {
			//TODO 后期做缓存
			String header =null;
			try{
				header = WebUtil.getHeader("mj-lang");
			}catch (Exception e){

			}
			String language = "en";
			if (Func.isNotEmpty(header)) {
				language = header;
			}
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("code", code);
			Map<String, Object> dataMap = mjkjBaseSqlService.getDataOneByFieldParams("coin_language", wrapper);
			if (Func.isEmpty(dataMap)) {
				return code;
			}
			String text = MjkjUtils.getMap2Str(dataMap, "text");
			Map<String, String> map = JsonUtil.parse(text, Map.class);
			if (Func.isEmpty(map)) {
				return "";
			}
			String currentLang = map.get(language);
			if (Func.isEmpty(currentLang)) {
				String en = map.get("en");
				return Func.isNotEmpty(en) ? en : map.get("zh_cn");
			}
			return map.get(language);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//获取语言
	//自定义语言
	public String getLanguage(String lang,String code){
		//后期做缓存
		String header =null;
		try{
			header = WebUtil.getHeader("mj-lang");
			if (Func.isNotEmpty(header)) {
				lang = header;
			}
		}catch (Exception e){

		}
		if(Func.isEmpty(lang)){
			lang="en";
		}
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("code", code);
		Map<String, Object> dataMap = mjkjBaseSqlService.getDataOneByFieldParams("coin_language", wrapper);
		if (Func.isEmpty(dataMap)) {
			return code;
		}
		String text = MjkjUtils.getMap2Str(dataMap, "text");
		Map<String, String> map = JsonUtil.parse(text, Map.class);
		if (Func.isEmpty(map)) {
			return "";
		}
		String currentLang = map.get(lang);
		if (Func.isEmpty(currentLang)) {
			String en = map.get("en");
			return Func.isNotEmpty(en) ? en : map.get("zh_cn");
		}
		return currentLang;

	}

	@Override
	public void handleEntrust(List<EntrustModel> entrustList, BigDecimal nowPrice, String symbolName) {

	}

//	//处理合约
//	public void handleEntrust(List<EntrustModel> entrustList, BigDecimal nowPrice, String symbolName) {
//		//符合条件的委托号
//		Set<String> entrustCodeList = new HashSet<>();
//
//
//		for (EntrustModel entrustModel : entrustList) {
//			String entrustType = entrustModel.getEntrustType();
//			String entrustCode = entrustModel.getEntrustCode();
//
//			if (Func.isNotEmpty(entrustType)) {
//				if(Func.equals(entrustType,"xh_sjwt")||Func.equals(entrustType,"xh_ggzc_sjwt")||Func.equals(entrustType,"xh_ggqc_sjwt")){//市价委托则直接成交
//					entrustCodeList.add(entrustCode);
//					continue;
//				}
//
//
//				if (Func.equals(entrustType, "ubw_quick") || Func.equals(entrustType, "bbw_quick")) {//闪电平仓
//					entrustCodeList.add(entrustCode);
//					continue;
//				}
//				if (Func.equals(entrustType, "bbw_reverse") || Func.equals(entrustType, "bbw_reverse")) {//反向开仓仓
//					entrustCodeList.add(entrustCode);
//					continue;
//				}
//			}
//			List<EntrustModel.CalculationModel> calculationList = entrustModel.getCalculationList();
//			if (Func.isEmpty(calculationList)) {
//				continue;
//			}
//			outer:
//			//锚点
//			for (EntrustModel.CalculationModel calculationModel : calculationList) {
//				String rule = calculationModel.getRule();
//				BigDecimal triggerPrice = calculationModel.getTriggerPrice();
//				switch (rule) {
//					case ">=":
//						if (nowPrice.compareTo(triggerPrice) != -1) {
//							entrustCodeList.add(entrustCode);
//							break outer;
//						}
//					case ">":
//						if (nowPrice.compareTo(triggerPrice) == 1) {
//							entrustCodeList.add(entrustCode);
//							break outer;
//						}
//					case "=":
//						if (nowPrice.compareTo(triggerPrice) == 0) {
//							entrustCodeList.add(entrustCode);
//							break outer;
//						}
//					case "<=":
//						if (nowPrice.compareTo(triggerPrice) != 1) {
//							entrustCodeList.add(entrustCode);
//							break outer;
//						}
//					case "<":
//						if (nowPrice.compareTo(triggerPrice) == -1) {
//							entrustCodeList.add(entrustCode);
//							break outer;
//						}
//				}
//			}
//		}
//		//不存在符合条件
//		if (Func.isEmpty(entrustCodeList)) {
//			return;
//		}
//		//进行二次确认------------
//		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("is_deleted", 0);
//		queryWrapper.eq("symbol_name", symbolName);
//		queryWrapper.in("entrust_code", entrustCodeList);
//		List<Map<String, Object>> selectList = mjkjBaseSqlService.getDataListByFieldParams("coin_entrust", queryWrapper);
//		if (Func.isEmpty(selectList)) {
//			return;
//		}
//
//		for (Map<String, Object> selectDataMap : selectList) {
//			String javaClass = MjkjUtils.getMap2Str(selectDataMap, "java_class");
//			String entrustCode = MjkjUtils.getMap2Str(selectDataMap, "entrust_code");
//			String entrustStatus = MjkjUtils.getMap2Str(selectDataMap, "entrust_status");//委托状态
//			if (!Func.equals(entrustStatus, "0")) {//不是委托中
//				//移除引擎
//				Iterator<EntrustModel> iterator = entrustList.iterator();
//				while (iterator.hasNext()) {
//					EntrustModel next = iterator.next();
//					if (Func.equals(entrustCode, next.getEntrustCode())) {//两个是一样，则移除
//						iterator.remove();
//						break;//跳出当前循环
//					}
//				}
//				continue;
//			}
//			//处理委托
//			try{
//				Object obj = SpringContextUtils.getBean(javaClass);
//				if (obj != null && obj instanceof IEntrustBasicService) {
//					IEntrustBasicService basicService = (IEntrustBasicService) obj;
//					basicService.handleEntrust(entrustCode, nowPrice);
//				}
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//
//		}
//	}

	//获取会员id
	@Override
	public String getMemberId() {
		Map<String, Object> dataMap = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException(this.getLanguage("mng_user_not_exist"));
		}
		return MjkjUtils.getMap2Str(dataMap, "id");
	}

	//获取会员id
	@Override
	public Map<String, Object> getMemberData() {
		Long authId = AuthUtil.getUserId();
		Map<String, Object> dataMap = mjkjBaseSqlService.getDataOneByField("coin_member",
			"blade_user_id", authId,
			Arrays.asList("tb.*", "(select real_name from blade_user where id = " + authId + ") as real_name"));
		String google_key = MjkjUtils.getMap2Str(dataMap, "google_key");

		if (Func.isEmpty(dataMap)) {
			throw new ServiceException(this.getLanguage("mng_user_not_exist"));
		}
		if (!dataMap.containsKey("phone")) {
			dataMap.put("phone", "");
		}
		if (!dataMap.containsKey("email")) {
			dataMap.put("email", "");
		}
		dataMap.put("google_key", Func.isNotEmpty(google_key) ? 1 : 0);
		dataMap.put("pay_pwd", Func.isNotEmpty(dataMap.get("pay_pwd")) ? 1 : 0);
		return dataMap;
	}

	@Override
	public Boolean accountVerificationBinding(AccountBindingParam param) throws BusinessException {
		Map<String, Object> memberData = getMemberData();
		String redisKey = "";
		if (Func.isEmpty(param.getCheckType())) {
			throw new BusinessException(this.getLanguage("mng_fail"));
		}

		String type="BD";
		if (Func.equals(param.getAccountType(), "1")) {

			String phone = param.getAccount();
			String areaCode = param.getPhoneRegion();
			if(Func.isEmpty(areaCode)){
				areaCode=this.getAreaCode(phone);
			}
			redisKey = MjkjUtils.getPhoneRedisKey(type, phone, areaCode);
		} else if (Func.equals(param.getAccountType(), "2")) {
			redisKey = MjkjUtils.getEmailRedisKey(type, param.getAccount());
		}
		//不存在，或者不对，则替换
		if (!bladeRedis.exists(redisKey) || !Func.equals((String) bladeRedis.get(redisKey),param.getVerificationCode())) {//不存在
			String verificationKey = redisKey.replace("BD", "MYZ");

			if (Func.equals(param.getCheckType(), "1") && bladeRedis.exists(verificationKey) &&
				((String) bladeRedis.get(verificationKey)).equals(param.getVerificationCode())) {
				return true;
			}

			if(!bladeRedis.exists(redisKey)){
				throw new BusinessException(getLanguage("mng_code_expired"));//验证码已过期
			}

		}
		String redisCode = (String) bladeRedis.get(redisKey);
		if (!Func.equals(param.getVerificationCode(), redisCode) && Func.isNotEmpty(param.getVerificationCode())) {
			throw new BusinessException(getLanguage("mng_code_incorrect"));
		}
		if (Func.equals(param.getCheckType(), "1")) {//校验
			return true;
		} else if (Func.equals(param.getCheckType(), "2")) {//修改账户
			Map<String, Object> bladeUserMap = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			map.put("withdrawal_freeze_time", DateUtils.now());
			if (Func.equals(param.getAccountType(), "1")) {
				bladeUserMap.put("phone", param.getAccount());
				map.put("phone", param.getAccount());
				map.put("area_code", param.getPhoneRegion());
			} else if (Func.equals(param.getAccountType(), "2")) {
				bladeUserMap.put("email", param.getAccount());
				map.put("email", param.getAccount());
			}
			if (Func.isNotEmpty(map)) {
				Map<String, Object> bladeUser = mjkjBaseSqlService.getDataOneByFieldParams("blade_user", Wrappers.query()
					.allEq(bladeUserMap));
				if (Func.isNotEmpty(bladeUser)) {
					throw new BusinessException("mng_phone_exist");
				}
				mjkjBaseSqlService.baseUpdateData("blade_user", bladeUserMap, MjkjUtils.getMap2Str(memberData, "blade_user_id"));
				mjkjBaseSqlService.baseUpdateData("coin_member", map, MjkjUtils.getMap2Str(memberData, "id"));
				return true;
			}
		} else if (Func.equals(param.getCheckType(), "3")) {//解绑
			//如果是已经成为商家了，则不允许解绑
			//1=审核中  2=已通过
			String isMerchant = MjkjUtils.getMap2Str(memberData, "is_merchant");
			if(Func.isNotEmpty(isMerchant) && (Func.equals(isMerchant,"1") || Func.equals(isMerchant,"2"))){
				throw new BusinessException(getLanguage("当前处于服务商家状态，不允许解绑"));
			}


			HashMap<String, Object> bladeUserMap = new HashMap<>();
			bladeUserMap.put("phone", "");
			mjkjBaseSqlService.baseUpdateData("blade_user", bladeUserMap, MjkjUtils.getMap2Str(memberData, "blade_user_id"));


			HashMap<String, Object> memberMap = new HashMap<>();
			memberMap.put("phone", "");
			memberMap.put("area_code", "");
			memberMap.put("withdrawal_freeze_time", DateUtils.now());
			mjkjBaseSqlService.baseUpdateData("coin_member", memberMap, MjkjUtils.getMap2Str(memberData, "id"));
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getLoginLog() {
		QueryWrapper<Object> tQueryWrapper = new QueryWrapper<Object>();
		tQueryWrapper.eq("member_id", getMemberId());
		tQueryWrapper.eq("is_deleted", 0);
		tQueryWrapper.orderByDesc("login_time");
		tQueryWrapper.last(" LIMIT 10 ");
		List<Map<String, Object>> listByField = mjkjBaseSqlService.getDataListByFieldParams("coin_member_login", tQueryWrapper);
		return listByField;
	}

	@Override
	public List<Map<String, Object>> getLoginDevice() {
		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("member_id", getMemberId());
		wrapper.eq("is_deleted", 0);
		wrapper.orderByDesc("last_login_time");
		List<Map<String, Object>> dataList = mjkjBaseSqlService.getDataListByFieldParams("coin_member_device", wrapper);
		for (Map<String, Object> map : dataList) {
			String Key = "blade_device_" + AuthUtil.getUserId() + MjkjUtils.getMap2Str(map, "device_id");
			if (bladeRedis.exists(Key)) {
				map.put("login_status", 1);
			}
		}

		return dataList;
	}

	@Override
	public Boolean deleteDevice(String id) {

		Integer device = mjkjBaseSqlService.baseZdyDeleteSql("coin_member_device", "device_id", id);
		if (device > 0) {
			String Key = "blade_device_" + AuthUtil.getUserId() + id;
			if (bladeRedis.exists(Key)) {
				String redisCode = (String) bladeRedis.get(Key);
				JwtUtil.removeAccessToken(AuthUtil.getTenantId(), String.valueOf(AuthUtil.getUserId()), redisCode);
				bladeRedis.del(Key);
			}
			return true;
		}
		return false;
	}

	@Override
	public Boolean updateDevice(AccountBindingParam param) throws BusinessException {
		String redisKey = "";
		String type="MYZ";
		if (Func.equals(param.getAccountType(), "1")) {
			String areaCode = param.getPhoneRegion();
			String phone = param.getAccount();
			if(Func.isEmpty(areaCode)){
				areaCode=this.getAreaCode(phone);
			}
			redisKey = MjkjUtils.getPhoneRedisKey(type,phone,areaCode);
		} else if (Func.equals(param.getAccountType(), "2")) {
			redisKey = MjkjUtils.getEmailRedisKey(type,param.getAccount());
		}

		if (!bladeRedis.exists(redisKey)) {
			throw new BusinessException(getLanguage("mng_code_expired"));
		}
		String redisCode = (String) bladeRedis.get(redisKey);
		if (!Func.equals(param.getVerificationCode(), redisCode) && Func.isNotEmpty(param.getVerificationCode())) {
			throw new BusinessException(getLanguage("mng_code_incorrect"));
		}
		QueryWrapper<Object> erapper = new QueryWrapper<>();
		erapper.eq("member_id", getMemberId());
		erapper.eq("device_id", param.getDeviceId());
		erapper.eq("is_deleted", 0);
		Map<String, Object> deviceMap = mjkjBaseSqlService.getDataOneByFieldParams("coin_member_device", erapper);
		if (Func.isNotEmpty(deviceMap)) {
			Map<String, Object> devMap = new HashMap<>();
			devMap.put("login_aulh", param.getLoginAulh());
			mjkjBaseSqlService.baseUpdateData("coin_member_device", devMap, MjkjUtils.getMap2Str(deviceMap, "id"));
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean verified(VerifiedParam param) throws BusinessException {
		Map<String, Object> memberData = getMemberData();
		if (Func.isEmpty(memberData)) {
			return false;
		}

		if (Func.isNotEmpty(mjkjBaseSqlService.getDataListByField("coin_member_identity", "card_status = 1 and card_code", param.getCardCode()))) {
			throw new ServiceException(getLanguage("mng_identity_exist"));
		}
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("member_id", MjkjUtils.getMap2Str(memberData, "id"));
		Map<String, Object> identity = mjkjBaseSqlService.getDataOneByFieldParams("coin_member_identity", wrapper);

		if (MjkjUtils.getMap2Integer(identity, "card_status") != -1) {
			throw new BusinessException(getLanguage("mng_resubmit_error"));
		}

		Map<String, Object> map = BeanUtil.beanToMap(param, true, true);
		map.put("member_id", MjkjUtils.getMap2Str(memberData, "id"));
		map.put("card_status", "0");
		map.put("reason", "");
		if (Func.isEmpty(identity)) {
			mjkjBaseSqlService.baseInsertData("coin_member_identity", map);
		} else {
			mjkjBaseSqlService.baseUpdateData("coin_member_identity", map, MjkjUtils.getMap2Str(identity, "id"));
		}
		Map<String, Object> memberMap = new HashMap<>();
		memberMap.put("real_name_status", "1");
		mjkjBaseSqlService.baseUpdateData("coin_member", memberMap, MjkjUtils.getMap2Str(memberData, "id"));

		return true;
	}

	@Override
	public Map<String, Object> getGoogleVerification(String type) {
		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());
		if (Func.isEmpty(memberData)) {
			throw new ServiceException(getLanguage("mng_user_not_exist"));
		}
		String secretKey = "";
		String googleKey = "member:google_key:" + MjkjUtils.getMap2Str(memberData, "id");
		// 验证
		if (Func.isNotEmpty(MjkjUtils.getMap2Str(memberData, "google_key")) && Func.equals(type, "1")) {
			secretKey = MjkjUtils.getMap2Str(memberData, "google_key");
		} else if (Func.equals(type, "0")) {
			if (bladeRedis.exists(googleKey)) {
				secretKey = (String) bladeRedis.get(googleKey);
			} else {
				secretKey = GoogleAuthenticator.getSecretKey();
			}
			bladeRedis.setEx(googleKey, secretKey, 1800L);
		}


		String qrCodeText = GoogleAuthenticator.getQrCodeText(secretKey, "coinhouse (" + MjkjUtils.getMap2Str(memberData, "name") + "-" + secretKey.substring(0, 3) + ")", null);
		Map<String, Object> map = new HashMap<>();
		map.put("qrCodeText", qrCodeText);
		map.put("secretKey", secretKey);
		return map;
	}

	/**
	 *
	 * @param type 1=谷歌验证码
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Boolean googleVerificationBindingOrReset(String type, String code) throws BusinessException {

		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());

		String googleKey = "member:google_key:" + MjkjUtils.getMap2Str(memberData, "id");
		String secretKey = Func.equals(type, "1") ? (String) bladeRedis.get(googleKey) : MjkjUtils.getMap2Str(memberData, "google_key");
		if (null == secretKey || secretKey.equals("")) {//019089
			throw new BusinessException(getLanguage("mng_get_key_again"));//请重新获取密钥
		}
		boolean flag = GoogleAuthenticator.checkCode(secretKey, Long.parseLong(code), System.currentTimeMillis());
		if (!flag) {
			throw new BusinessException(getLanguage("mng_code_incorrect"));//验证码不正确
		}
		if (Func.isEmpty(MjkjUtils.getMap2Str(memberData, "google_key")) ||
			!Func.equals(MjkjUtils.getMap2Str(memberData, "google_key"), secretKey)) {
			Map<String, Object> map = new HashMap<>();
			map.put("google_key", secretKey);
			map.put("withdrawal_freeze_time", DateUtils.now());
			mjkjBaseSqlService.baseUpdateData("coin_member", map, MjkjUtils.getMap2Str(memberData, "id"));
		}

		return true;
	}

	@Override
	public void tradePwd(TradePwdParam param) throws BusinessException {
		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());
		Map<String, Object> pwd = mjkjBaseSqlService.getTableById("blade_user", AuthUtil.getUserId().toString(), Collections.singletonList("password"));
		String redisKey = "";
		String type="BD";
		if (Func.isNotEmpty(param.getPhoneCode())) {
			String phone = MjkjUtils.getMap2Str(memberData, "phone");
			String areaCode = MjkjUtils.getMap2Str(memberData, "area_code");

			if(Func.isEmpty(areaCode)){
				areaCode=this.getAreaCode(phone);
			}

			redisKey = MjkjUtils.getPhoneRedisKey(type, phone, areaCode);
			if (!bladeRedis.exists(redisKey)) {
				throw new BusinessException(this.getLanguage("mng_code_expired"));
			}
			if (!Func.equals(bladeRedis.get(redisKey), param.getPhoneCode())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else if (Func.isNotEmpty(param.getEmailCode())) {
			String email = MjkjUtils.getMap2Str(memberData, "email");
			redisKey = MjkjUtils.getEmailRedisKey(type, email);
			if (!bladeRedis.exists(redisKey)) {
				throw new BusinessException(this.getLanguage("mng_code_expired"));
			}
			if (!Func.equals(bladeRedis.get(redisKey), param.getEmailCode())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else if (Func.isNotEmpty(param.getTotpCode())) {
			redisKey = MjkjUtils.getMap2Str(memberData, "google_key");
			if (!GoogleAuthenticator.checkCode(redisKey, Long.parseLong(param.getTotpCode()), System.currentTimeMillis())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else {
			throw new BusinessException(this.getLanguage("mng_code_incorrect"));
		}

		if (Func.isNotEmpty(param.getValType())) {
			if (MjkjUtils.getMap2Str(memberData, "pay_pwd").equals("")) {
				throw new BusinessException(this.getLanguage("mng_operation_fail"));
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("transaction_validate", param.getValType());
			map.put("withdrawal_freeze_time", DateUtils.now());
			mjkjBaseSqlService.baseUpdateData("coin_member", map, MjkjUtils.getMap2Str(memberData, "id"));
			return;
		}

		if (!param.getTradePwd().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$")) {
			throw new BusinessException(this.getLanguage("mng_pwd_rules"));
		}
		if (param.getLoginPwd().equals(param.getTradePwd())) {
			throw new BusinessException(this.getLanguage("mng_trade_pwd_match_login"));
		}
		if (!MjkjUtils.getMap2Str(pwd, "password").equals(DigestUtil.encrypt(DigestUtil.md5Hex(param.getLoginPwd())))) {
			throw new BusinessException(this.getLanguage("mng_login_pwd_incorrect"));
		}

		if (!MjkjUtils.getMap2Str(memberData, "pay_pwd").equals("")) {
			redisKey = redisKey.equals(MjkjUtils.getMap2Str(memberData, "google_key"))
				? "" : MjkjUtils.getMap2Str(memberData, "google_key");
			if (Func.isNotEmpty(param.getTotpCode()) && !redisKey.equals("")) {
				if (!GoogleAuthenticator.checkCode(redisKey, Long.parseLong(param.getTotpCode()), System.currentTimeMillis())) {
					throw new BusinessException(this.getLanguage("mng_code_incorrect"));
				}
				if (MjkjUtils.getMap2Str(memberData, "pay_pwd").equals(DigestUtil.encrypt(param.getTradePwd()))) {
					throw new BusinessException(this.getLanguage("mng_pwd_match_original"));
				}
			} else {
				throw new BusinessException(this.getLanguage("mng_operation_fail"));
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("pay_pwd", DigestUtil.encrypt(param.getTradePwd()));
		map.put("withdrawal_freeze_time", DateUtils.now());
		mjkjBaseSqlService.baseUpdateData("coin_member", map, MjkjUtils.getMap2Str(memberData, "id"));
	}

	@Override
	public void antiCode(AntiCodeParam param) throws BusinessException {
		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());
		String redisKey = "";
		String type="BD";
		if (Func.isNotEmpty(param.getPhoneCode())) {
			String phone = MjkjUtils.getMap2Str(memberData, "phone");
			String areaCode = MjkjUtils.getMap2Str(memberData, "area_code");
			if(Func.isEmpty(areaCode)){
				areaCode=this.getAreaCode(phone);
			}
			redisKey = MjkjUtils.getPhoneRedisKey(type,phone,areaCode);
			if (!bladeRedis.exists(redisKey)) {
				throw new BusinessException(this.getLanguage("mng_code_expired"));
			}
			if (!Func.equals(bladeRedis.get(redisKey), param.getPhoneCode())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else if (Func.isNotEmpty(param.getEmailCode())) {
			String email = MjkjUtils.getMap2Str(memberData, "email");
			redisKey = MjkjUtils.getEmailRedisKey(type, email);
			if (!bladeRedis.exists(redisKey)) {
				throw new BusinessException(this.getLanguage("mng_code_expired"));
			}
			if (!Func.equals(bladeRedis.get(redisKey), param.getEmailCode())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else if (Func.isNotEmpty(param.getGoogleCode())) {
			redisKey = MjkjUtils.getMap2Str(memberData, "google_key");
			if (!GoogleAuthenticator.checkCode(redisKey, Long.parseLong(param.getGoogleCode()), System.currentTimeMillis())) {
				throw new BusinessException(this.getLanguage("mng_code_incorrect"));
			}
		} else {
			throw new BusinessException(this.getLanguage("mng_code_incorrect"));
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("withdrawal_freeze_time", DateUtils.now());
		map.put("anti_code", param.getAntiCode());
		mjkjBaseSqlService.baseUpdateData("coin_member", map, MjkjUtils.getMap2Str(memberData, "id"));
	}

	@Override
	public BigDecimal coinConversion(String coinSymbol, BigDecimal amount) {

		BigDecimal totalKjslCoin = BigDecimal.ZERO;

		String symbolName = coinSymbol + "/USDT";
		Query query = new Query(Criteria.where("symbolName").is(symbolName)).limit(1);
		String mangodbkey = MarketKlineUtils.getDetailMongdbKey("XH", symbolName);
		Map selectModel = mongoTemplate.findOne(query, Map.class, mangodbkey);

		if (Func.isNotEmpty(selectModel) && MjkjUtils.getMap2BigD(selectModel, "close").compareTo(BigDecimal.ZERO) == 1) {
			totalKjslCoin = amount.multiply(MjkjUtils.getMap2BigD(selectModel, "close"))
				.setScale(8, BigDecimal.ROUND_DOWN);
		}
		return totalKjslCoin;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void payment(PaymentParam paymentParam) throws BusinessException {
		//二次校验
		this.validation(paymentParam.getCheckType(), paymentParam.getCode());
		//支付详情
		QueryWrapper queryWrapper = MjkjUtils.queryWrapper("pay_method_id", paymentParam.getPayMethodId(),
			"owner_id", paymentParam.getOwnerId(),
//			"country_id", paymentParam.getCountryId(),
			"is_deleted", 0);
		Map payAccount = mjkjBaseSqlService.getDataOneByFieldParams("coin_pay_account", queryWrapper);
		if (Func.isNotEmpty(payAccount) && paymentParam.isDeleted()) {
			mjkjBaseSqlService.baseDeleteSql("coin_pay_account", MjkjUtils.getMap2Long(payAccount, "id"));
			return;
		}
		//会员身份认证
		Map<String, Object> identity = mjkjBaseSqlService.getDataOneByField("coin_member_identity", "member_id", paymentParam.getOwnerId(), Collections.singletonList("card_status, surname, name"));
		if (Func.isEmpty(identity) || MjkjUtils.getMap2Integer(identity, "card_status") != 1) {
			throw new BusinessException(this.getLanguage("mng_not_real_name"));
		}
//		if (!Func.equals(MjkjUtils.getMap2Str(identity, "surname"), paymentParam.getSurname())
//			&& !Func.equals(MjkjUtils.getMap2Str(identity, "name"), paymentParam.getName())) {
//			throw new BusinessException("银行卡姓名与实名信息不一致");
//		}
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("owner_id", paymentParam.getOwnerId());
		insertMap.put("pay_method_id", paymentParam.getPayMethodId());
		insertMap.put("country_id", paymentParam.getCountryId());
		insertMap.put("name", paymentParam.getName());
		insertMap.put("account", paymentParam.getAccount());
		insertMap.put("bank", paymentParam.getBank());
		insertMap.put("branch", paymentParam.getBranch());
		insertMap.put("qr_code", paymentParam.getQrCode());
		insertMap.put("surname", paymentParam.getSurname());
		if (Func.isNotEmpty(payAccount)) {
			mjkjBaseSqlService.baseUpdateData("coin_pay_account", insertMap, MjkjUtils.getMap2Str(payAccount, "id"));
			return;
		}
		mjkjBaseSqlService.baseInsertData("coin_pay_account", insertMap);
	}

	@Override
	public List<Map<String, Object>> getPayment() throws BusinessException {
		List<Map<String, Object>> payment = webMapper.getPayment(getMemberId());
		for (int i = 0; i < payment.size(); i++) {
			String name = this.getLanguage(MjkjUtils.getMap2Str(payment.get(i), "memthod_name"));
			payment.get(i).replace("memthod_name", name);
		}
		return payment;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditBusiness(Integer codeType, String code, String name, String email, String coinSymbol, String coinCou, Integer status) {
		//this.validation(codeType, code);
		Map<String, Object> memberData = getMemberData();
		Assert.isTrue(MjkjUtils.getMap2Integer(memberData, "real_name_status") == 2, getLanguage("mng_not_real_name"));
		Assert.isTrue(Func.isNotEmpty(MjkjUtils.getMap2Str(memberData, "phone")) &&
			Func.isNotEmpty(MjkjUtils.getMap2Str(memberData, "email")), getLanguage("mng_audit_phone_empty"));
		Map<String, Object> coinMap;
		Map<String, Object> walletMap;
		BigDecimal before, after;
		name = Func.isEmpty(name) ? MjkjUtils.getMap2Str(memberData, "name") : name;
		if (status == 1) {
			Map<String, Object> sysMap = mjkjBaseSqlService.getDataOneByField("sys_cssz", "code", "sjgl_bzjsz");
			sysMap = JsonUtil.toMap(MjkjUtils.getMap2Str(sysMap, "param_json"));
			String principal = MjkjUtils.getMap2Str(sysMap, "money");
			coinSymbol = MjkjUtils.getMap2Str(sysMap, "coin");
			coinMap = mjkjBaseSqlService.getDataOneByFieldParams("coin_coin",
				Wrappers.query()
					.eq("symbol", coinSymbol)
					.eq("is_deleted", 0));
			Assert.isTrue(Func.isNotEmpty(principal) || Func.isNotEmpty(coinMap), getLanguage("mng_principal_notsupport"));
			walletMap = walletService
				.getWalletMap(WalletConstant.WALLET_TYPE_WALLET,
					MjkjUtils.getMap2Str(memberData, "id"),
					MjkjUtils.getMap2Str(coinMap, "id"));
			before = MjkjUtils.getMap2BigD(walletMap, "balance");
			BigDecimal cou = Func.isEmpty(coinCou) ? new BigDecimal(principal) : new BigDecimal(coinCou);
			after = before.subtract(cou);
			Assert.isTrue(cou.compareTo(before) <= 0, String.format(getLanguage("mng_balance_insufficient"), ""));

			Map<String, Object> principalMap = mjkjBaseSqlService
				.getDataOneByField("coin_pay_service", "member_id", MjkjUtils.getMap2Str(memberData, "id"));
			if (Func.isEmpty(principalMap)) {
				Assert.isTrue(cou.compareTo(new BigDecimal(principal)) >= 0, getLanguage("mng_principal_nlt") + principal);
				HashMap<String, Object> insertMap = new HashMap<>();
				insertMap.put("member_id", MjkjUtils.getMap2Str(memberData, "id"));
				insertMap.put("name", name);
				//insertMap.put("icon", MjkjUtils.getMap2Str(memberData, "avatar"));
				insertMap.put("principal_coin_id", MjkjUtils.getMap2Str(coinMap, "id"));
				insertMap.put("principal_symbol", MjkjUtils.getMap2Str(coinMap, "symbol"));
				insertMap.put("principal_amount", cou);
				insertMap.put("status", 0);
				mjkjBaseSqlService.baseInsertData("coin_pay_service", insertMap);
			} else {
				HashMap<String, Object> updateMap = new HashMap<>();
				updateMap.put("name", name);
				updateMap.put("principal_amount", cou.add(MjkjUtils.getMap2BigD(principalMap, "principal_amount")));
				mjkjBaseSqlService.baseUpdateData("coin_pay_service", updateMap, MjkjUtils.getMap2Str(principalMap, "id"));
			}
			HashMap<String, Object> memberUpdateMap = new HashMap<>();
			memberUpdateMap.put("is_merchant", 1);
			mjkjBaseSqlService.baseUpdateData("coin_member", memberUpdateMap, MjkjUtils.getMap2Str(memberData, "id"));
		} else {
			Map<String, Object> principalMap = mjkjBaseSqlService
				.getDataOneByFieldParams("coin_pay_service",
					Wrappers.query()
						.eq("member_id", MjkjUtils.getMap2Str(memberData, "id"))
						.eq("is_deleted", 0)
						.eq("status", 1));
			Assert.notEmpty(principalMap, getLanguage("mng_principal_miss"));
			walletMap = walletService
				.getWalletMap(WalletConstant.WALLET_TYPE_WALLET,
					MjkjUtils.getMap2Str(memberData, "id"),
					MjkjUtils.getMap2Str(principalMap, "principal_coin_id"));
			Assert.notEmpty(walletMap, getLanguage("mng_wallet_notexist"));
			before = MjkjUtils.getMap2BigD(walletMap, "balance");
			after = before.add(MjkjUtils.getMap2BigD(principalMap, "principal_amount"));
			HashMap<String, Object> updateMap = new HashMap<>();
			updateMap.put("principal_amount", 0);
			updateMap.put("status", 0);
			mjkjBaseSqlService.baseUpdateData("coin_pay_service", updateMap, MjkjUtils.getMap2Str(principalMap, "id"));
			HashMap<String, Object> memberUpdateMap = new HashMap<>();
			memberUpdateMap.put("is_merchant", 0);
			mjkjBaseSqlService.baseUpdateData("coin_member", memberUpdateMap, MjkjUtils.getMap2Str(memberData, "id"));
		}

		HashMap<String, Object> walletUpdateMap = new HashMap<>();
		walletUpdateMap.put("balance", after);
		mjkjBaseSqlService.baseUpdateData("coin_member_wallet", walletUpdateMap, MjkjUtils.getMap2Str(walletMap, "id"));

		Map<String, Object> historyMap = new HashMap<>();
		historyMap.put("member_id", MjkjUtils.getMap2Str(walletMap, "member_id"));
		historyMap.put("wallet_id", MjkjUtils.getMap2Str(walletMap, "id"));
		historyMap.put("coin_id", MjkjUtils.getMap2Str(walletMap, "coin_id"));
		historyMap.put("coin_symbol", MjkjUtils.getMap2Str(walletMap, "coin_symbol"));
		historyMap.put("type", status);
		historyMap.put("amount", after.subtract(before).abs());
		historyMap.put("member_before_balance", before);//之前
		historyMap.put("member_before_frozen_balance", MjkjUtils.getMap2Str(walletMap, "frozen_balance"));//之前
		historyMap.put("member_after_balance", after);//之后
		historyMap.put("member_after_frozen_balance", MjkjUtils.getMap2Str(walletMap, "frozen_balance"));//之后
		historyMap.put("service_type", 3);
		historyMap.put("remark", "商家保证金");
		mjkjBaseSqlService.baseInsertData("coin_member_wallet_history", historyMap);
	}


	//获取用户推广信息
	@Override
	public List<Map<String, Object>> getMemeberTgInfo() throws BusinessException {
		List<Map<String, Object>> list = new ArrayList<>();
		String id = getMemberId();
		//1级推广
		Map<String, Object> level1 = new HashMap<>();
		Map<String, Object> m1 = webMapper.getMemberSum(id, 1);
		if (Func.isNotEmpty(m1)) {
			level1.put("title", mjkjBaseSqlService.getMngLanguage("一级推广"));
			level1.put("id", "1");
			level1.putAll(m1);
		}
		Map<String, Object> map1 = webMapper.getMemberProfit(id, 1);
		BigDecimal profit1 = MjkjUtils.getMap2BigD(map1, "profit");
		if (profit1.compareTo(BigDecimal.ZERO) == 0) {
			level1.put("yjsr", "-");
		} else {
			level1.put("yjsr", profit1);
			level1.put("symbol", "USDT");
		}
		list.add(level1);


		//2及推广
		Map level2 = new HashMap();
		Map<String, Object> m2 = webMapper.getMemberSum(id, 2);
		if (Func.isNotEmpty(m2)) {
			level2.put("title", mjkjBaseSqlService.getMngLanguage("二级推广"));
			level2.put("id", "2");
			level2.putAll(m2);
		}

		Map<String, Object> map2 = webMapper.getMemberProfit(id, 2);
		BigDecimal profit2 = MjkjUtils.getMap2BigD(map2, "profit");

		if (profit2.compareTo(BigDecimal.ZERO) == 0) {
			level2.put("yjsr", "-");
		} else {
			level2.put("yjsr", profit2);
			level2.put("symbol", "USDT");
		}
		list.add(level2);

		return list;
	}

	@Override
	public List<Map<String, Object>> getMemeberTgList(String type) throws BusinessException {
		List<Map<String, Object>> list = new ArrayList<>();
		//用户id
		String id = getMemberId();
		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member", "id", id);
		//用户级别
		Integer level = MjkjUtils.getMap2Integer(memberData, "level");
		for (; level >= 1; level--) {
			List<Map<String, Object>> levelList = new ArrayList<>();
			//获取直系下属id
			QueryWrapper qw = new QueryWrapper();
			//一级推广
			if (Func.equals(type, "1")) {
				qw.eq("pid", id);
				qw.eq("level", level);
			} else if (Func.equals(type, "2")) {//二级推广
				qw.ne("pid", id);
				qw.ne("id", id);
				qw.eq("level", level);
				qw.like("ancestors", id);
			}
			List<Map<String, Object>> memberList = mjkjBaseSqlService.getDataListByFieldParams("coin_member", qw);
			for (Map<String, Object> member : memberList) {
				String mid = MjkjUtils.getMap2Str(member, "id");
				//获取下属的一级推广
				Map<String, Object> map = webMapper.getMemberSum(mid, 1);
				//姓名
				String name = MjkjUtils.getMap2Str(member, "name");
				//产生佣金
				Map<String, Object> t1 = webMapper.getMemberCsyj(mid);
				BigDecimal p1 = MjkjUtils.getMap2BigD(t1, "yj");

				//封装map
				map.put("name", name);
				if (p1.compareTo(BigDecimal.ZERO) == 0) {
					map.put("csyj", "0");
				} else {
					map.put("csyj", p1);
					map.put("symbol", "USDT	");
				}
				levelList.add(map);
			}
			if (Func.isNotEmpty(levelList)) {
				Map<String, Object> levelMap = mjkjBaseSqlService.getDataOneByField("coin_member_level", "level", level, Collections.singletonList("level_title"));
				Map m = new HashMap();
				//会员等级
				m.put("level", MjkjUtils.getMap2Str(levelMap, "level_title"));
				m.put("list", levelList);
				list.add(m);
			}
		}
		return list;
	}

	public void validation(Integer codeType, String code) {
		Assert.isTrue(Func.isNotEmpty(codeType), "validation type error");
		Map<String, Object> memberMap = mjkjBaseSqlService.getDataOneByField("coin_member", "blade_user_id", AuthUtil.getUserId());
		String type="MYZ";
		if (codeType == 1) {//手机号码
			String phone = MjkjUtils.getMap2Str(memberMap, "phone");
			String areaCode = MjkjUtils.getMap2Str(memberMap, "area_code");
			if(Func.isEmpty(areaCode)){
				areaCode=this.getAreaCode(phone);
			}
			String redisKey = MjkjUtils.getPhoneRedisKey(type, phone, areaCode);
			Assert.isTrue(Func.isNotEmpty(code) && Objects.equals(redis.<String>get(redisKey), code), getLanguage("mng_code_incorrect"));
		} else if (codeType == 2) {///邮箱
			String email = MjkjUtils.getMap2Str(memberMap, "email");
			String redisKey = MjkjUtils.getEmailRedisKey(type, email);
			Assert.isTrue(Func.isNotEmpty(code) && Objects.equals(redis.<String>get(redisKey), code), getLanguage("mng_code_incorrect"));
		} else if (codeType == 3) {//谷歌验证码
			String secretKey = MjkjUtils.getMap2Str(memberMap, "google_key");
			Assert.isTrue(GoogleAuthenticator.checkCode(secretKey, Long.parseLong(code), System.currentTimeMillis()), getLanguage("mng_code_incorrect"));
		} else if (codeType == 4) {//支付密码
			String payPwd = MjkjUtils.getMap2Str(memberMap, "pay_pwd");
			Assert.isTrue(Func.isEmpty(payPwd) || payPwd.equals(code), getLanguage("mng_trade_pwd_incorrect"));
		}
	}


	public Map<String, Map<String, Object>> getCoinMap() {
		if (redis.exists(COIN_MAP_COIN_SYMBOL))
			return redis.get(COIN_MAP_COIN_SYMBOL);
		List<Map<String, Object>> coinList = mjkjBaseSqlService.getDataByTable("coin_coin");
		Map<String, Map<String, Object>> coinMap = coinList.parallelStream().collect(Collectors.toMap(k -> MjkjUtils.getMap2Str(k, "id"), v -> v));
		redis.setEx(COIN_MAP_COIN_SYMBOL, coinMap, 360000L);
		return coinMap;
	}

	public Map<String, Map<String, Object>> getFiatMap() {
		if (redis.exists(COIN_MAP_FIAT_CURRENCY))
			return redis.get(COIN_MAP_FIAT_CURRENCY);
		List<Map<String, Object>> countryList = mjkjBaseSqlService.getDataByTable("coin_country");
		Map<String, Map<String, Object>> fiatMap = countryList.parallelStream().collect(Collectors.toMap(k -> MjkjUtils.getMap2Str(k, "id"), v -> v));
		redis.setEx(COIN_MAP_FIAT_CURRENCY, fiatMap, 360000l);
		return fiatMap;
	}

	public Map<String, Map<String, Object>> getPaymentMap() {
		if (redis.exists(PAYMENT_KEY))
			return redis.get(PAYMENT_KEY);
		List<Map<String, Object>> list = mjkjBaseSqlService.getDataByTable("coin_payment_member", Arrays.asList("id", "name", "icon", "pay_methods_type"));
		for (int i = 0; i < list.size(); i++) {
			String name = this.getLanguage(MjkjUtils.getMap2Str(list.get(i), "name"));
			list.get(i).replace("name", name);
		}
		Map<String, Map<String, Object>> paymentMap = list.parallelStream().collect(Collectors.toMap(k -> MjkjUtils.getMap2Str(k, "id"), v -> v));
		redis.setEx(PAYMENT_KEY, paymentMap, 7200L);
		return paymentMap;
	}

	//获取区号
	@Override
	public String getAreaCode(String phone){
		Map<String, Object> memberMap = mjkjBaseSqlService.getDataOneByField("coin_member", "phone", phone);
		String areaCode = MjkjUtils.getMap2Str(memberMap, "area_code");
		return areaCode;
	}

	@Override
	public void tradePwdVerification(TradePwdVerificationParam param) {
		Map<String, Object> memberData = mjkjBaseSqlService.getDataOneByField("coin_member",
			"blade_user_id", AuthUtil.getUserId());
			Assert.isTrue(MjkjUtils.getMap2Str(memberData, "pay_pwd").equals(DigestUtil.encrypt(param.getTradePwd())),
				this.getLanguage("mng_trade_pwd_incorrect"));
		int validate = Func.toInt(memberData.get( "transaction_validate"), 0);
		if (validate == 0)
			return;

		String ip = WebUtil.getIP();
		HttpServletRequest request = WebUtil.getRequest();
		String deviceId = request.getHeader("deviceId");
		String token = request.getHeader("blade-auth");
		String memberId = MjkjUtils.getMap2Str(memberData, "id");
		String key = "trade:verified:" + memberId + deviceId + ip;
		if (validate == 1) { //登录有效
			redis.setEx(key, token, Duration.ofDays(7L));
		} else if (validate == 2) { //3小时验证
			redis.setEx(key, token, Duration.ofHours(3L));
		} else if (validate == 3) { //每次验证
			redis.setEx(key, token, 500L);
		}
	}

	@Override
	public Boolean tradePwdVerification() {
		Map<String, Object> memberData = getMemberData();
		if (MjkjUtils.getMap2Integer(memberData, "trade_status") == 1) {
			if (new Date().compareTo(MjkjUtils.getMap2DateTime(memberData, "withdrawal_freeze_time")) < 0) {
				throw new IllegalArgumentException(getLanguage("当前账号暂时禁止交易，解锁时间：") +
					MjkjUtils.getMap2DateTime(memberData, "withdrawal_freeze_time"));
			}
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("trade_status", 0);
			mjkjBaseSqlService.baseUpdateData("coin_member", updateMap, MjkjUtils.getMap2Str(memberData, "id"));
		}
		int validate = Func.toInt(memberData.get( "transaction_validate"), 0);
		if (Func.isEmpty(MjkjUtils.getMap2Str(memberData, "pay_pwd")) || validate == 0){
			return false;
		}


		String ip = WebUtil.getIP();
		HttpServletRequest request = WebUtil.getRequest();
		String deviceId = request.getHeader("deviceId");
		String token = request.getHeader("blade-auth");
		String memberId = MjkjUtils.getMap2Str(memberData, "id");//trade:verified:null127.0.0.1
		String key = "trade:verified:" + memberId + deviceId + ip;
		if (token.equals(redis.get(key))){
			if (validate == 3){
				redis.del(key);
			}
			return false;
		}
		return true;
	}

	//根据交易对获取现货模式（1=走货模式 2=撮合模式）
	@Override
	public String getXhExchangeModel(String symbolName){
		String redisKey="coin_exchange:exchange_type:"+symbolName;
		if(bladeRedis.exists(redisKey)){
			String str = bladeRedis.get(redisKey);
			return str;
		}

		QueryWrapper<Object> exchangeWrapper=new QueryWrapper<>();
		exchangeWrapper.eq("is_deleted",0);
		exchangeWrapper.eq("symbol_name",symbolName);
		exchangeWrapper.select("exchange_type");
		Map<String, Object> exchangeMap = mjkjBaseSqlService.getDataOneByFieldParams("coin_coin_exchange", exchangeWrapper);
		if(Func.isEmpty(exchangeMap)){
			throw new ServiceException("现货交易类型有误");
		}
		String exchangeType = MjkjUtils.getMap2Str(exchangeMap, "exchange_type");
		if(Func.isEmpty(exchangeType)){
			throw new ServiceException("现货交易类型有误");
		}
		bladeRedis.setEx(redisKey,exchangeType,300L);//5分钟

		return exchangeType;
	}
}


