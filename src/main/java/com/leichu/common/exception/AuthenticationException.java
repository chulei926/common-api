package com.leichu.common.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 认证异常.
 *
 * @author leichu 2021-09-12.
 */
public class AuthenticationException extends RuntimeException {

	private String code;

	/**
	 * 构造函数
	 */
	public AuthenticationException() {
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param message 信息
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 *
	 * @param message 信息
	 * @param cause   cause
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 *
	 * @param cause cause
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	public AuthenticationException(String code, String message) {
		super(message);
		this.code = code;
	}

	public AuthenticationException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
