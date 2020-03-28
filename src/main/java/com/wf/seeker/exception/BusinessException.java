package com.wf.seeker.exception;

/**
 * 业务异常
 */
public class BusinessException extends SystemException {

	private String code;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
