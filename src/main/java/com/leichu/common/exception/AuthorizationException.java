package com.leichu.common.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 授权异常.
 *
 * @author leichu 2021-09-12.
 */
public class AuthorizationException extends RuntimeException {
	private String code;

	/**
	 * 构造函数
	 */
	public AuthorizationException() {
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param message 信息
	 */
	public AuthorizationException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 *
	 * @param message 信息
	 * @param cause   cause
	 */
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 *
	 * @param cause cause
	 */
	public AuthorizationException(Throwable cause) {
		super(cause);
	}

	public AuthorizationException(String code, String message) {
		super(message);
		this.code = code;
	}

	public AuthorizationException(String code, String message, Throwable cause) {
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
