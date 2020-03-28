package com.wf.seeker.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
@Data
public class SysQuartzJob implements Serializable {
	private static final long serialVersionUID = 1L;

	/** id */
	private java.lang.String id;
	/** 创建人 */
	private java.lang.String createBy;
	/** 创建时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** 删除状态 */
	private java.lang.Integer delFlag;
	/** 修改人 */
	private java.lang.String updateBy;
	/** 修改时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/** 任务类名 */
	private java.lang.String jobClassName;
	/** cron表达式 */
	private java.lang.String cronExpression;
	/** 参数 */
	private java.lang.String parameter;
	/** 描述 */
	private java.lang.String description;
	/** 状态 0正常 -1停止 */
	private java.lang.Integer status;

}
