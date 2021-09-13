package com.leichu.common.web;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class SessionShareFilter implements Filter {

	private String sessionKey = "session_id";
	private String cookieDomain = "";
	private String cookiePath = "";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (null != filterConfig.getInitParameter("sessionKey")) {
			this.sessionKey = filterConfig.getInitParameter("sessionKey");
		}
		if (null != filterConfig.getInitParameter("cookieDomain")) {
			this.cookieDomain = filterConfig.getInitParameter("cookieDomain");
		}
		if (null != filterConfig.getInitParameter("cookiePath")) {
			this.cookiePath = filterConfig.getInitParameter("cookiePath");
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		final Cookie[] cookies = request.getCookies();

		String sid = null;
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (!this.sessionKey.equals(cookie.getName())) {
					continue;
				}
				sid = cookie.getValue();
				break;
			}
		}
		if (null == sid || sid.length() <= 0) {
			sid = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(this.sessionKey, sid);
			cookie.setMaxAge(-1);
			cookie.setPath(this.cookiePath);
			if (null != this.cookieDomain && this.cookieDomain.length() > 0) {
				cookie.setDomain(this.cookieDomain);
			}
			response.addCookie(cookie);
		}
		chain.doFilter(new HttpServletRequestWrapper4SessionShare(sid, request), response);

	}

	@Override
	public void destroy() {

	}
}
