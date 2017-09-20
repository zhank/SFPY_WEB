package com.sfpy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * Ȩ��ͳ�ƻ���
 * 
 * @author zhangkk
 * @date 2017��9��14��
 */
public class AuthorCacheDataFactory {

	private static final Logger logger = LoggerFactory.getLogger(AuthorCacheDataFactory.class);

	/**
	 * Ȩ��ͳ�������� Ӧ��Id+������� ��ɫȨ��ͳ�ƣ�roleId+�������
	 */

	private AuthorCacheDataFactory() {
	}

	private static class SingletonHolder {
		private static final AuthorCacheDataFactory INSTANCE = new AuthorCacheDataFactory();
	}

	public static final AuthorCacheDataFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Ӧ��ͳ�ƽڵ�������"appId_userType";
	 * 
	 * @param orgId
	 * @return
	 */
	public String getKey(String key) {
		return key;
	}

	/**
	 * ����Ӧ��ͳ������
	 * 
	 * @param appId
	 * @param userType
	 */
	public void updateCacheOrgById(String key, Object value) {
		Jedis jedis = RedisPool.getJedis();
		updateCacheData(jedis, key, value);
		RedisPool.closeConn(jedis);
	}

	/**
	 * ���»�����ͳ����Ϣ
	 * 
	 * @param jedis
	 * @param key
	 * @param value
	 */
	public void updateCacheData(Jedis jedis, String key, Object value) {
		long t1 = System.currentTimeMillis();
		if (value == null) {
			value = "0";
		}
		jedis.set(key, value.toString());
		long t2 = System.currentTimeMillis();
		System.out.println("����ͳ�ƽڵ�=" + (t2 - t1));
	}

	/**
	 * ɾ����������
	 * 
	 * @param key
	 */
	public void deleteCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		jedis.del(key);
		RedisPool.closeConn(jedis);
	}

	/**
	 * ͨ��key��ȡͳ������
	 * 
	 * @param key
	 * @return
	 */
	public Object getCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		Object count = jedis.get(key);
		RedisPool.closeConn(jedis);
		return count;
	}

	/**
	 * ����ͳ������
	 * 
	 * @param isReloadAllData
	 *            ǿ�Ƹ������л�������
	 */

}
