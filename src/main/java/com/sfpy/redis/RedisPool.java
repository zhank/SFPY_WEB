package com.sfpy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Explain:Redis���ӳ�
 */
public final class RedisPool {

	private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);

	private static JedisPool jedisPool = null;

	/**
	 * ��ʼ��Redis���ӳ�
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			/*
			 * ע�⣺ �ڸ߰汾��jedis
			 * jar�������籾�汾2.9.0��JedisPoolConfigû��setMaxActive��setMaxWait������
			 * ������Ϊ�߰汾�йٷ������˴˷��������������������滻�� maxActive ==> maxTotal maxWait==>
			 * maxWaitMillis
			 */
			JedisDBCfg cfg = JedisDBCfg.getInstance();
			config.setMaxTotal(cfg.MAX_TOTAL);
			config.setMaxIdle(cfg.MAX_IDLE);
			config.setMaxWaitMillis(cfg.MAX_WAIT_MILLIS);
			config.setTestOnBorrow(cfg.TEST_ON_BORROW);
			jedisPool = new JedisPool(config, cfg.IP, cfg.PORT, cfg.TIMEOUT, cfg.AUTH);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * ��ȡJedisʵ��
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis jedis = jedisPool.getResource();
				return jedis;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static void closeConn(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
			// jedisPool.returnResource(jedis);
			// jedis.close()ȡ��jedisPool.returnResource(jedis)������3.0�汾��ʼ
			// jedis.close();
		}
	}
}