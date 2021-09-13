package com.leichu.common.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统异常。
 *
 * @author leichu 2019-03-12.
 */
public class SysException extends RuntimeException {

	private static final long serialVersionUID = 7963973385602074788L;
	private String code;

    /**
     * 构造函数
     */
    public SysException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param message 信息
     */
    public SysException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 信息
     * @param cause   cause
     */
    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param cause cause
     */
    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SysException(String code, String message, Throwable cause) {
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
