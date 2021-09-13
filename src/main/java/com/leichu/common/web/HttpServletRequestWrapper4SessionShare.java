package com.leichu.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class HttpServletRequestWrapper4SessionShare extends HttpServletRequestWrapper {

	private final String sessionId;

	public HttpServletRequestWrapper4SessionShare(String sessionId, HttpServletRequest request) {
		super(request);
		this.sessionId = sessionId;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return new HttpSession4SessionShare(this.sessionId, super.getSession(create));
	}

	@Override
	public HttpSession getSession() {
		return new HttpSession4SessionShare(this.sessionId, super.getSession());
	}
}
