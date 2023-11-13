/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.cgform.model.flow;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程模型
 *
 *
 */
@Data
public class FlowModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MODEL_TYPE_BPMN = 0;
	public static final int MODEL_TYPE_FORM = 2;
	public static final int MODEL_TYPE_APP = 3;
	public static final int MODEL_TYPE_DECISION_TABLE = 4;
	public static final int MODEL_TYPE_CMMN = 5;

	private String id;
	private String name;
	private String modelKey;
	private String description;
	private Date created;
	private Date lastUpdated;
	private String createdBy;
	private String lastUpdatedBy;
	private Integer version;
	private String modelEditorJson;
	private String modelComment;
	private Integer modelType;
	private String tenantId;
	private byte[] thumbnail;

}
