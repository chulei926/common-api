package com.leichu.common.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 通用返回值。
 *
 * @author leichu 2019-03-12.
 */
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = 1944653059233394455L;

	/**
	 * 操作码（0：成功，-1：失败）.
	 */
	private int code;
	/**
	 * 结果.
	 */
	protected T result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	private static <T> JsonResult<T> getInstance() {
		return new JsonResult<T>();
	}

	public static <T> JsonResult<T> getSuccessResult() {
		JsonResult<T> result = getInstance();
		result.setCode(ResultCode.success.code);
		return result;
	}

	public static <T> JsonResult<T> getSuccessResult(T result) {
		JsonResult<T> jsonResult = getInstance();
		jsonResult.setCode(ResultCode.success.code);
		jsonResult.setResult(result);
		return jsonResult;
	}

	public static <T> JsonResult<T> getFailureResult() {
		JsonResult<T> result = getInstance();
		result.setCode(ResultCode.fail.code);
		return result;
	}

	public static <T> JsonResult<T> getFailureResult(T result) {
		JsonResult<T> jsonResult = getInstance();
		jsonResult.setCode(ResultCode.fail.code);
		jsonResult.setResult(result);
		return jsonResult;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public enum ResultCode {
		success(0), fail(-1);
		private int code;

		ResultCode(int code) {
			this.code = code;
		}
	}

}
