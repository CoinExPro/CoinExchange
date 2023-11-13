package org.springblade.web.model.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class PaymentParam implements Serializable {

	private Integer checkType;

	private String code;

	/**
	 * 所有者id
	 */
	@ApiModelProperty(value = "所有者id", hidden = true)
	private String owner_id;

	/**
	 * 支付方式
	 */
	@NotBlank
	@ApiModelProperty(value = "支付方式")
	private String pay_method_id;

	/**
	 * 所有者id
	 */
	@ApiModelProperty(value = "国家id", hidden = true)
	private String country_id;

	/**
	 * 姓氏
	 */
	@NotBlank
	@ApiModelProperty(value = "姓氏")
	private String surname;

	/**
	 * 姓名
	 */
	@NotBlank
	@ApiModelProperty(value = "姓名")
	private String name;


	/**
	 * 账户
	 */
	@NotBlank
	@ApiModelProperty(value = "账户")
	private String account;

	/**
	 * 开户行
	 */
	@ApiModelProperty(value = "开户行")
	private String bank;

	/**
	 * 开户支行
	 */
	@ApiModelProperty(value = "开户支行")
	private String branch;

	/**
	 * 收款二维码
	 */
	@ApiModelProperty(value = "收款二维码")
	private String qrCode;

	/**
	 * 是否删除
	 */
	@ApiModelProperty(value = "为true时仅删除信息")
	private boolean is_deleted;

	private static final long serialVersionUID = 1L;

	public String getOwnerId() {
		return owner_id;
	}

	public void setOwnerId(String ownerId) {
		this.owner_id = ownerId;
	}

	public String getPayMethodId() {
		return pay_method_id;
	}

	public void setPay_method_id(String pay_method_id) {
		this.pay_method_id = pay_method_id;
	}

	public String getSurname() {
		return surname;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getPay_method_id() {
		return pay_method_id;
	}

	public String getCountryId() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQr_code(String qrCode) {
		this.qrCode = qrCode;
	}

	public boolean isDeleted() {
		return is_deleted;
	}

	public void setDeleted(boolean deleted) {
		is_deleted = deleted;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountry_id() {
		return country_id;
	}
}
