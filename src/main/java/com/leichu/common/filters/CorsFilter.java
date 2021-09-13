package com.leichu.common.filters;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

/**
 * 跨域过滤器.
 *
 * @author leichu 2021-09-12.
 */
public class CorsFilter implements Filter {

	private String allowOrigin;
	private String allowMethods;
	private String allowCredentials;
	private String allowHeaders;
	private String exposeHeaders;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		if (StringUtils.isNotEmpty(allowOrigin)) {
			response.setHeader("Access-Control-Allow-Origin", allowOrigin);
		} else {
			HttpServletRequest request = (HttpServletRequest) req;
			String referer = request.getHeader("referer");
			if (StringUtils.isNoneBlank(referer)) {
				try {
					URI uri = new URI(referer);
					response.setHeader("Access-Control-Allow-Origin", String.format("http://%s:%s", uri.getHost(), uri.getPort()));
				} catch (Throwable e) {
					response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
				}
			}
		}
		if (StringUtils.isNotEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (StringUtils.isNotEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
		}
		if (StringUtils.isNotEmpty(allowHeaders)) {
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if (StringUtils.isNotEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}
