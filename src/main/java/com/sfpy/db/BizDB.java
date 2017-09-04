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
	 * ���캯���趨Ϊ������ǿ���ⲿ����ʴ���ͨ��getInstance()������ɳ�ʼ�������
	 * 
	 */
	protected BizDB() {
	}

	/**
	 * ��ò���Ҫ��ʼ����ʵ�����������������м���Listener
	 * 
	 * @return
	 */
	public static BizDB getInstanceWithoutInit() {
		return Inner.instance;
	}

	/**
	 * ʹ�õ���ģʽ���г�ʼ�� ��δ���������£���Ҫ�ڷ�������ǰ����getInstance()��<br>
	 * @return
	 * @throws Exception
	 */
	public static synchronized BizDB getInstance() {
		if (!inited) {
			try {
				getInstanceWithoutInit().initDB();
				// �Ѿ���ʼ�����
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
}