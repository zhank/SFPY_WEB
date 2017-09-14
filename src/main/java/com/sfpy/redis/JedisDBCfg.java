package com.sfpy.redis;

/**
 * jedis���ݿ���ص�����
 */
public class JedisDBCfg {

	// Redis������IP
	public String IP = "127.0.0.1";
	// Redis�Ķ˿ں�
	public int PORT = 6379;
	// ��������
	public String AUTH = "123456";

	// ��������ʵ���������Ŀ��Ĭ��Ϊ8��
	// �����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)
	public int MAX_TOTAL = 1024;
	// ����һ��pool����ж��ٸ�״̬Ϊidle(����)��jedisʵ����Ĭ��ֵ��8
	public int MAX_IDLE = 200;
	// �ȴ��������ӵ����ʱ�䣬��λ�Ǻ��룬Ĭ��ֵΪ-1����ʾ������ʱ��
	// ��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException
	public int MAX_WAIT_MILLIS = 10000;
	public int TIMEOUT = 10000;
	// ��borrow(��)һ��jedisʵ��ʱ���Ƿ���ǰ����validate(��֤)������
	// ���Ϊtrue����õ���jedisʵ�����ǿ��õ�
	public boolean TEST_ON_BORROW = true;

	private static JedisDBCfg m_instance;

	public static JedisDBCfg getInstance() {
		if (m_instance == null) {
			m_instance = new JedisDBCfg();
		}
		return m_instance;
	}

	protected JedisDBCfg() {
	}

	public String getCaption() {
		return "jedis���ݿ�����";
	}
}
