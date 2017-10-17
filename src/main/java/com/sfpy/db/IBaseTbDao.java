package com.sfpy.db;

import java.sql.Connection;
import java.util.Map;

import com.sfpy.field.Field;

/**
 * ���ݿ����DAO�����ӿ�
 * @author sfpy
 *
 */
public interface IBaseTbDao extends IBaseViewDao {
	
	/**
	 * �������
	 * @param dataMap ����Map
	 * @throws Exception
	 */
	public void insert(Map<String, Object> dataMap) throws Exception;
	
	/**
	 * �������������
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param dataMap ����Map
	 * @throws Exception
	 */
	public void insertWithConn(Connection conn, Map<String, Object> dataMap) throws Exception;
	
	/**
	 * ����������������
	 * @param updateField �����ֶ�Field
	 * @param updateValue ����ֵ
	 * @param id ����ID
	 * @throws Exception
	 */
	public void update(Field updateField, Object updateValue, String condition) throws Exception;
	
	/**
	 * �����񣺸���������������
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param updateField �����ֶ�Field
	 * @param updateValue ����ֵ
	 * @param id ����ID
	 * @throws Exception
	 */
	public void updateWithConn(Connection conn, Field updateField, Object updateValue, String condition) throws Exception;
	
	/**
	 * ��������
	 * @param id ����ID
	 * @param dataMap ���µ�Map
	 * @throws Exception
	 */
	public void update(Map<String, Object> dataMap, String condition) throws Exception;
	
	/**
	 * �����񣺸�������
	 * @param conn ���ݿ����ӣ�˭������˭�ر�
	 * @param id ����ID
	 * @param dataMap ���µ�Map
	 * @throws Exception
	 */
	public void updateWithConn(Connection conn, Map<String, Object> dataMap, String condition) throws Exception;

}
