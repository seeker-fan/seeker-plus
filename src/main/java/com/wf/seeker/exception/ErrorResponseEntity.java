package com.wf.seeker.exception;

import lombok.Data;

/**
 * 搭配接口限流使用，返回统一相依码
 * 
 * @author Fan.W
 * @since 1.8
 */
@Data
public class ErrorResponseEntity {

	/**
	 * @param code
	 * @param message
	 */
	public ErrorResponseEntity(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;
}
