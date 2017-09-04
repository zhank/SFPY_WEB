package com.sfpy.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {

	// �������
	public Connection getConnection();

	// ��õ�ǰ����
	public Connection getCurrentConnection();

	// ��������
	public void releaseConn(Connection conn) throws SQLException;

	// �������
	public void destroy();

	// ���ӳػ״̬
	public boolean isActive();

	// ��ʱ������ӳ�
	public void checkPool();
}
