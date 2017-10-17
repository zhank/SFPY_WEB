package com.sfpy.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public final class BizDB extends AbstractBizDB{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BizDB.class);

	private static class Inner {
		private static BizDB instance = new BizDB();
	}

	private static boolean inited = false;
	
	/**
	 * 构造函数设定为保护，强制外部类访问此类通过getInstance()方法完成初始化后调用
	 * 
	 */
	protected BizDB() {
	}

	/**
	 * 获得不需要初始化的实例，仅用于在配置中加载Listener
	 * 
	 * @return
	 */
	public static BizDB getInstanceWithoutInit() {
		return Inner.instance;
	}
	
	/**
	 * 根据驱动实现优化的IDbOp实现类
	 * 
	 * @param jdbcDriver
	 * @param version
	 */
	public void enableOptimize(String jdbcDriver, int version) {
		IDbOp newDbOp = DbOpMgr.getDbOp(jdbcDriver, version);
		if (newDbOp != null) {
			m_dbOp = newDbOp;
		}
	}

	/**
	 * 使用单例模式进行初始化 在未部署的情况下，需要在服务启动前调用getInstance()，<br>
	 * @return
	 * @throws Exception
	 */
	public static synchronized BizDB getInstance() {
		if (!inited) {
			try {
				getInstanceWithoutInit().initDB();
				// 已经初始化完成
				inited = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return getInstanceWithoutInit();
	}


	@Override
	public String getPoolName() {
		return null;
	}

	@Override
	public void setPoolName(String poolName) {
		
	}

	@Override
	public void initDB() throws Exception {
		//ConnectionPoolManager.getInstance().init();
	}
	
	/**
	 * 获取优化以后的查询类
	 * 
	 * @return
	 */
	public IDbOp getOptimizeDbOp() {
		return m_dbOp;
	}
}