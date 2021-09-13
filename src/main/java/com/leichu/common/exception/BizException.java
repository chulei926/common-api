package com.leichu.common.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 业务异常。
 *
 * @author leichu 2019-03-12.
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -8179492781417319727L;
	
	private String code;

    /**
     * 构造函数
     */
    public BizException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param message 信息
     */
    public BizException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 信息
     * @param cause   cause
     */
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param cause cause
     */
    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message, Throwable cause) {
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
