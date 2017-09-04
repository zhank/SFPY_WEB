package com.sfpy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class ConnectionPool implements IConnectionPool {

	// ���ӳ���������
	private DBbean dbBean;
	private boolean isActive = false; // ���ӳػ״̬
	private int countActive = 0; // ��¼�������ܵ�������

	// ��������
	private List<Connection> freeConnection = new Vector<Connection>();
	// �����
	private List<Connection> activeConnection = new Vector<Connection>();
	// ���̺߳����Ӱ󶨣���֤������ͳһִ��
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public ConnectionPool(DBbean dbBean) {
		super();
		this.dbBean = dbBean;
		init();
		checkPool();
	}

	// ��ʼ��
	public void init() {
		try {
			Class.forName(dbBean.getDriverName());
			for (int i = 0; i < dbBean.getInitConnection(); i++) {
				Connection conn = newConnection();
				// ��ʼ����С������
				if (conn != null) {
					freeConnection.add(conn);
					countActive++;
				}
			}
			isActive = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// �������
	public Connection getConnection() {
		Connection conn = null;
		try {
			// �ж��Ƿ񳬹��������������
			if (countActive < dbBean.getMaxActiveConnections()) {
				if (freeConnection.size() > 0) {
					conn = freeConnection.get(0);
					if (conn != null) {
						threadLocal.set(conn);
					}
					freeConnection.remove(0);
				} else {
					conn = newConnection();
				}
			} else {
				// �����������,ֱ�����»������
				wait(this.dbBean.getConnTimeOut());
				conn = getConnection();
			}
			if (isValid(conn)) {
				activeConnection.add(conn);
				countActive++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// ���������
	public synchronized Connection newConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		if (dbBean != null) {
			Class.forName(dbBean.getDriverName());
			conn = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(), dbBean.getPassword());
		}
		return conn;
	}

	// �ж������Ƿ����
	public boolean isValid(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 *  ��õ�ǰ����
	 */
	public Connection getCurrentConnection() {
		// Ĭ���߳�����ȡ
		Connection conn = threadLocal.get();
		if (!isValid(conn)) {
			conn = getConnection();
		}
		return null;
	}

	public synchronized void releaseConn(Connection conn) throws SQLException {
		if (isValid(conn) && !(freeConnection.size() > dbBean.getMaxConnections())) {
			freeConnection.add(conn);
			activeConnection.remove(conn);
			countActive--;
			threadLocal.remove();
			// �������������ȴ����̣߳�ȥ������
			notifyAll();
		}
	}

	// �������ӳ�
	public synchronized void destroy() {
		for (Connection conn : freeConnection) {
			try {
				if (isValid(conn)) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (Connection conn : activeConnection) {
			try {
				if (isValid(conn)) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		isActive = false;
		countActive = 0;
	}

	public boolean isActive() {
		return isActive;
	}

	// ��ʱ������ӳ����
	public void checkPool() {
		if (dbBean.isCheckPool()) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 1.���߳����������״̬
					// 2.���ӳ���С ���������
					// 3.����״̬���м�飬��Ϊ���ﻹ��Ҫд�����̹߳�����࣬��ʱ�Ͳ������
					System.out.println("���߳���������" + freeConnection.size());
					System.out.println("�����������" + activeConnection.size());
					System.out.println("�ܵ���������" + countActive);
				}
			}, dbBean.getLazyCheck(), dbBean.getPeriodCheck());
		}
	}

}
