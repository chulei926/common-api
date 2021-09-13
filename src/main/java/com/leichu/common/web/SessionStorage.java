package com.leichu.common.web;

import com.leichu.common.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

@Slf4j
public class SessionStorage {

	private static volatile SessionStorage instance;

	private static JedisPool jedisPool;
	private static final String SESSION_KEY_FORMAT = "SessionShare:%s";
	private static final int SESSION_TIMEOUT = 30 * 60;
	private static final int maxIdle = 20;
	private static final int maxTotal = 200;
	private static final Boolean testOnBorrow = Boolean.FALSE;
	private static final Boolean testOnReturn = Boolean.FALSE;
	private static final int timeout = 3000;

	public static SessionStorage getInstance() {
		if (null == instance) {
			synchronized (SessionStorage.class) {
				if (null == instance) {
					instance = new SessionStorage();
				}
			}
		}
		return instance;
	}

	private SessionStorage() {
		try {
			Properties properties = new Properties();
			final InputStream inputStream = SessionStorage.class.getResourceAsStream("/sessionStorage.properties");
			properties.load(inputStream);
			JedisPoolConfig config = new JedisPoolConfig();
			//最大空闲连接数
			config.setMaxIdle(maxIdle);
			//最大连接数
			config.setMaxTotal(maxTotal);
			config.setTestOnBorrow(testOnBorrow);
			config.setTestOnReturn(testOnReturn);
			String host = properties.getProperty("host", "127.0.0.1");
			String port = properties.getProperty("port", "6379");
			String password = properties.getProperty("password", null);
			jedisPool = new JedisPool(config, host, Integer.parseInt(port), timeout, password);
			registerHook();
			log.warn("Redis 连接池创建成功！{}:{}", properties.getProperty("host"), properties.getProperty("port"));
		} catch (Exception e) {
			log.error("Redis 连接池异常！", e);
		}
	}

	public void close() {
		if (null == jedisPool) {
			return;
		}
		try {
			jedisPool.close();
			jedisPool = null;
		} catch (Exception e) {
			throw new RuntimeException("Redis 客户端关闭失败！", e);
		}
		log.warn("Redis 客户端连接已关闭！");
	}

	private void registerHook() {
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(this::close));
	}

	private String getKey(String sessionId) {
		return String.format(SESSION_KEY_FORMAT, sessionId);
	}

	public void setSessionExpire(String sid) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.expire(getKey(sid), SESSION_TIMEOUT);
		}
	}

	public Object getAttribute(String sessionId, String name) {
		try (Jedis jedis = jedisPool.getResource()) {
			byte[] data = jedis.hget(getKey(sessionId).getBytes(StandardCharsets.UTF_8), name.getBytes(StandardCharsets.UTF_8));
			return SerializeUtils.unSerialize(data);
		}
	}

	public Enumeration getAttributeNames(String sessionId) {
		throw new UnsupportedOperationException("方法未实现。");
	}

	public void setAttribute(String sessionId, String name, Object value) {
		if (value == null) {
			return;
		}
		final String key = getKey(sessionId);
		try (Jedis jedis = jedisPool.getResource()) {
			byte[] data = SerializeUtils.serialize(value);
			jedis.hset(key.getBytes(StandardCharsets.UTF_8), name.getBytes(StandardCharsets.UTF_8), data);
			jedis.expire(key, SESSION_TIMEOUT);
		}
	}

	public void removeAttribute(String sessionId, String name) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hdel(getKey(sessionId), name);
		}
	}

	public void remove(String sessionId) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.del(getKey(sessionId));
		}
	}
}
