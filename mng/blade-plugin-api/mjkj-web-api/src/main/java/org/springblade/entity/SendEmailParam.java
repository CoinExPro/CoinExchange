package org.springblade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: @Wai
 * Date: 2022-10-28
 */
@Data
public class SendEmailParam implements Serializable {
	private String email;
	private String title;
	private List<String> content;
}
