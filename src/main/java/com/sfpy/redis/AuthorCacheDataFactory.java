package com.sfpy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 权限统计缓存
 * 
 * @author zhangkk
 * @date 2017年9月14日
 */
public class AuthorCacheDataFactory {

	private static final Logger logger = LoggerFactory.getLogger(AuthorCacheDataFactory.class);

	/**
	 * 权限统计主界面 应用Id+身份类型 角色权限统计：roleId+身份类型
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
	 * 应用统计节点主键："appId_userType";
	 * 
	 * @param orgId
	 * @return
	 */
	public String getKey(String key) {
		return key;
	}

	/**
	 * 更新应用统计数据
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
	 * 更新缓存中统计信息
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
		System.out.println("更新统计节点=" + (t2 - t1));
	}

	/**
	 * 删除缓存数据
	 * 
	 * @param key
	 */
	public void deleteCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		jedis.del(key);
		RedisPool.closeConn(jedis);
	}

	/**
	 * 通过key获取统计数据
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
	 * 加载统计数据
	 * 
	 * @param isReloadAllData
	 *            强制更新所有缓存数据
	 */

}
