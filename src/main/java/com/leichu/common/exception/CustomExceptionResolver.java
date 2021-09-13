package com.leichu.common.exception;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.leichu.common.dto.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理器.
 *
 * @author kitty 2019-05-08.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage(), ex);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		Map<String, String> body = this.getBody(ex);
		String agent = request.getHeader("user-agent");
		if (StringUtils.isNotEmpty(agent) && (agent.contains("Android") || agent.contains("iPhone") || agent.contains("iPad"))) {
			try {
				//手机端返回200，抛出错误信息
				response.setStatus(HttpStatus.OK.value());
				JsonResult jsonResult = JsonResult.getFailureResult(body.get("message"));
				response.setContentType("text/json;charset=utf-8");
				response.getWriter().write(jsonResult.toString());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			return new ModelAndView();
		}
		try {
			JsonResult jsonResult = JsonResult.getFailureResult(body.get("message"));
			if (null != body.get("code")) {
				jsonResult.setCode(Integer.parseInt(body.get("code")));
			}
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(jsonResult.toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView();
	}

	/**
	 * <p>
	 * <code>getBody</code>根据exception获取返回信息
	 * </p>
	 */
	private Map<String, String> getBody(Exception ex) {
		Map<String, String> body = new HashMap<String, String>();
		if (ex instanceof BizException) {
			body.put("code", ((BizException) ex).getCode());
			body.put("message", ex.getMessage());
		} else if (ex instanceof RpcException && ((RpcException) ex).isBiz()) {
			body.put("message", ex.getMessage());
		} else if (ex instanceof AuthenticationException) {
			body.put("code", "-401");
			body.put("message", ex.getMessage());
		} else if (ex instanceof AuthorizationException) {
			body.put("code", "-403");
			body.put("message", ex.getMessage());
		} else {
			body.put("message", "系统出现异常，请检查网络是否正常或尝试进行刷新/重启页面操作！");
		}
		logger.error(body.get("message"));
		return body;
	}
}
