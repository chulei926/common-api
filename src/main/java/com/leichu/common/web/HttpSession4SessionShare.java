package com.leichu.common.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

public class HttpSession4SessionShare implements HttpSession {

	private final String sessionId;
	private final HttpSession rawSession;

	public HttpSession4SessionShare(String sessionId, HttpSession session) {
		this.sessionId = sessionId;
		this.rawSession = session;
		SessionStorage.getInstance().setSessionExpire(this.sessionId);
	}

	@Override
	public long getCreationTime() {
		return this.rawSession.getCreationTime();
	}

	@Override
	public String getId() {
		return this.sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return this.rawSession.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		return this.rawSession.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.rawSession.setMaxInactiveInterval(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		return this.rawSession.getMaxInactiveInterval();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return this.rawSession.getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		return SessionStorage.getInstance().getAttribute(this.sessionId, name);
	}

	@Override
	public Object getValue(String name) {
		return this.getAttribute(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		return SessionStorage.getInstance().getAttributeNames(this.sessionId);
	}

	@Override
	public String[] getValueNames() {
		return new String[0];
	}

	@Override
	public void setAttribute(String name, Object value) {
		SessionStorage.getInstance().setAttribute(this.sessionId, name, value);
	}

	@Override
	public void putValue(String name, Object value) {
		this.setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		SessionStorage.getInstance().removeAttribute(this.sessionId, name);
	}

	@Override
	public void removeValue(String name) {
		this.removeAttribute(name);
	}

	@Override
	public void invalidate() {
		SessionStorage.getInstance().remove(this.sessionId);
	}

	@Override
	public boolean isNew() {
		return this.rawSession.isNew();
	}
}
