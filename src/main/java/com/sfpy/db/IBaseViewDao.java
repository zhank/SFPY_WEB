package com.sfpy.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sfpy.field.Field;

/**
 * ���ݿ������ͼDAO�����ӿڣ���ͼֻ�в�ѯ�ӿ�
 * @author sfpy
 *
 */
public interface IBaseViewDao {
	
	/**
	 * ����������ѯ
	 * @param id ����ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataById(Object id) throws Exception;
	
	/**
	 * ����������ѯ
	 * @param fields ��ѯ�����Field
	 * @param id ����ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataById(Field[] fields, Object id) throws Exception;
	
	/**
	 * �����񣺸���������ѯ
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param id ����ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataByIdWithConn(Connection conn, Object id) throws Exception; 
	
	/**
	 * �����񣺸���������ѯ
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param fields ��ѯ�����Field
	 * @param id ����ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataByIdWithConn(Connection conn, Field[] fields, Object id) throws Exception; 
	
	/**
	 * ����������ѯ����
	 * @param condition ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(String condition) throws Exception;
	
	/**
	 * ����������ѯ����
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition) throws Exception;
	
	/**
	 * ����������ѯ����
	 * @param condition ��ѯ����
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * ����������ѯ����
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * ����������ҳ��ѯ����
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @param start ��ѯ��¼�Ŀ�ʼλ��
	 * @param max ÿҳ��¼��
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, int start, int max, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * �����񣺸���������ѯ����
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param condition ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition) throws Exception;
	
	/**
	 * �����񣺸���������ѯ����
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition) throws Exception;

	/**
	 * �����񣺸���������ѯ����
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * �����񣺸���������ѯ����
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * �����񣺸���������ҳ��ѯ����
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param fields ��ѯ�����Field
	 * @param condition ��ѯ����
	 * @param start ��ѯ��¼�Ŀ�ʼλ��
	 * @param max ÿҳ��¼��
	 * @param orderBy �����ֶ�
	 * @param bAsc �Ƿ�����
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition, int start, int max, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * ����������ѯ��������
	 * @param condition ��ѯ����
	 * @param condValues ����ֵ
	 * @return
	 * @throws Exception
	 */
	public int getCount(String condition, List<Object> condValues) throws Exception;
	
	/**
	 * �����񣺸���������ѯ��������
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param condition ��ѯ����
	 * @param condValues ����ֵ
	 * @return
	 * @throws Exception
	 */
	public int getCountWithConn(Connection conn, String condition, List<Object> condValues) throws Exception;
}
