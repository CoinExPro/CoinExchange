package org.springblade.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@ConfigurationProperties(prefix = "coinhouse")
public class CoinhouseConfig {

	// 短信相关
	private static String smsDriver;
	private static String smsGateway;
	private static String smsUsername;
	private static String smsPassword;
	private static String smsSign;

	//邮箱相关
	private static String emailId;
	private static String emailPassword;
	private static String emailFjr;
	private static String emailHost;

	//角色相关
	private static String rootRole;
	private static String zeroRole;
	private static String firstRole;
	private static String secondRole;
	private static String thirdRole;
	private static String generalRole;

	//默认代理商
	private static String customerBladeUserId;

	private static String salt;//自定义盐

	//火币成交量随机值
	private static BigDecimal huobiVolumePercent;

	//默认头像
	private static String defaultHeadIcon;

	//像超级管理员
	private static String administratorPhoneCode;//区号
	private static String administratorPhone;//手机号码
	private static String administratorEmail;//验证码

	//当前使用的交易所数据
	private static String marketJysName;

	private static String apiKey;
	private static String apiSecret;
	private static String apiBaseurl;
	//余额提醒
	private static String remindPhoneCode;//区号
	private static String remindPhone;//手机号码
	private static String remindEmail;//验证码



	//Telegram Bot
	private static String tgBaseUrl = "https://tgproxy.coinub.com/bot";
	private static String tgBotToken = "6177072684:AAG_VjYdcZmlbAa34G5Fxn2c8xDu7lsG7Ng";
	private static String tgBotName = "coinhouse01bot";

	public static String getSmsDriver() {
		return smsDriver;
	}

	public static String getTgBaseUrl() {
		return tgBaseUrl;
	}

	public static void setTgBaseUrl(String tgBaseUrl) {
		CoinhouseConfig.tgBaseUrl = tgBaseUrl;
	}
	public static String getTgBotName() {
		return tgBotName;
	}


	public static String getTgBotToken() {
		return tgBotToken;
	}

	public static void setTgBotToken(String tgBotToken) {
		CoinhouseConfig.tgBotToken = tgBotToken;
	}
	public static void setTgBotName(String tgBotName) {
		CoinhouseConfig.tgBotName = tgBotName;
	}

	public void setSmsDriver(String smsDriver) {
		CoinhouseConfig.smsDriver = smsDriver;
	}

	public static String getSmsGateway() {
		return smsGateway;
	}

	public void setSmsGateway(String smsGateway) {
		CoinhouseConfig.smsGateway = smsGateway;
	}

	public static String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		CoinhouseConfig.smsUsername = smsUsername;
	}

	public static String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		CoinhouseConfig.smsPassword = smsPassword;
	}

	public static String getSmsSign() {
		return smsSign;
	}

	public void setSmsSign(String smsSign) {
		CoinhouseConfig.smsSign = smsSign;
	}

	public static String getEmailId() {
		return emailId;
	}

	public  void setEmailId(String emailId) {
		CoinhouseConfig.emailId = emailId;
	}

	public static String getEmailPassword() {
		return emailPassword;
	}

	public  void setEmailPassword(String emailPassword) {
		CoinhouseConfig.emailPassword = emailPassword;
	}

	public static String getEmailFjr() {
		return emailFjr;
	}

	public void setEmailFjr(String emailFjr) {
		CoinhouseConfig.emailFjr = emailFjr;
	}

	public static String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		CoinhouseConfig.emailHost = emailHost;
	}

	public static BigDecimal getHuobiVolumePercent() {
		return huobiVolumePercent;
	}

	public static String getRootRole() {
		return rootRole;
	}

	public  void setRootRole(String rootRole) {
		CoinhouseConfig.rootRole = rootRole;
	}

	public static String getZeroRole() {
		return zeroRole;
	}

	public  void setZeroRole(String zeroRole) {
		CoinhouseConfig.zeroRole = zeroRole;
	}

	public static String getFirstRole() {
		return firstRole;
	}

	public  void setFirstRole(String firstRole) {
		CoinhouseConfig.firstRole = firstRole;
	}

	public static String getSecondRole() {
		return secondRole;
	}

	public  void setSecondRole(String secondRole) {
		CoinhouseConfig.secondRole = secondRole;
	}

	public static String getThirdRole() {
		return thirdRole;
	}

	public  void setThirdRole(String thirdRole) {
		CoinhouseConfig.thirdRole = thirdRole;
	}

	public static String getGeneralRole() {
		return generalRole;
	}

	public  void setGeneralRole(String generalRole) {
		CoinhouseConfig.generalRole = generalRole;
	}

	public void setHuobiVolumePercent(BigDecimal huobiVolumePercent) {
		CoinhouseConfig.huobiVolumePercent = huobiVolumePercent;
	}

	public static String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		CoinhouseConfig.salt = salt;
	}

	public static String getCustomerBladeUserId() {
		return customerBladeUserId;
	}

	public void setCustomerBladeUserId(String customerBladeUserId) {
		CoinhouseConfig.customerBladeUserId = customerBladeUserId;
	}

	public static String getDefaultHeadIcon() {
		return defaultHeadIcon;
	}

	public void setDefaultHeadIcon(String defaultHeadIcon) {
		CoinhouseConfig.defaultHeadIcon = defaultHeadIcon;
	}

	public static String getAdministratorPhoneCode() {
		return administratorPhoneCode;
	}

	public  void setAdministratorPhoneCode(String administratorPhoneCode) {
		CoinhouseConfig.administratorPhoneCode = administratorPhoneCode;
	}

	public static String getAdministratorPhone() {
		return administratorPhone;
	}

	public  void setAdministratorPhone(String administratorPhone) {
		CoinhouseConfig.administratorPhone = administratorPhone;
	}

	public static String getAdministratorEmail() {
		return administratorEmail;
	}

	public  void setAdministratorEmail(String administratorEmail) {
		CoinhouseConfig.administratorEmail = administratorEmail;
	}

	public static String getMarketJysName() {
		return marketJysName;
	}

	public void setMarketJysName(String marketJysName) {
		CoinhouseConfig.marketJysName = marketJysName;
	}

	public static String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		CoinhouseConfig.apiKey = apiKey;
	}

	public static String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		CoinhouseConfig.apiSecret = apiSecret;
	}

	public static String getApiBaseurl() {
		return apiBaseurl;
	}

	public void setApiBaseurl(String apiBaseurl) {
		CoinhouseConfig.apiBaseurl = apiBaseurl;
	}

	public static String getRemindPhoneCode() {
		return remindPhoneCode;
	}

	public void setRemindPhoneCode(String remindPhoneCode) {
		CoinhouseConfig.remindPhoneCode = remindPhoneCode;
	}

	public static String getRemindPhone() {
		return remindPhone;
	}

	public void setRemindPhone(String remindPhone) {
		CoinhouseConfig.remindPhone = remindPhone;
	}

	public static String getRemindEmail() {
		return remindEmail;
	}

	public void setRemindEmail(String remindEmail) {
		CoinhouseConfig.remindEmail = remindEmail;
	}
}
