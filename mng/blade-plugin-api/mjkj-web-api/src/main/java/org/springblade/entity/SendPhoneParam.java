package org.springblade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: @Wai
 * Date: 2022-10-28
 */
@Data
public class SendPhoneParam implements Serializable {
	private String phone;
	private String content;
}
