/*
 * Created on 2017-07-28
 *
 * @author zhangk
 * 
 * Copyright (C) 2006 SFPY SOFTWARE.
 */
package com.sfpy.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * �ײ����ݿ����CRUD�����Ľӿ�
 * @author zhangk
 * @version 1.0
 * @since 1.0
 */
public interface IDbOp {

	/**
	 * ȱʡ�ķ��ؼ�¼��������δָ��ʱʹ��
	 */
	public static final int DEFAULT_MAX = 1000;
	
	/**
	 * �����¼������ǰ�ж��Ƿ���ڣ���������׳��쳣��
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param dataMap �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param condition �ж�����
	 * @param condValues ��������
	 * @throws Exception
	 */
	public int insertIfNotExists(Connection conn, String table, Map<String,Object> dataMap, String condition, List<Object> condValues) throws Exception;
	
	/**
	 * �����¼
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param dataMap �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param isAutoGenKey �Ƿ񷵻������Ӽ�ֵ
	 * @return �������ֶε�ֵ
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String,Object> dataMap, boolean isAutoGenKey) throws Exception;
	
	/**
	 * �����¼
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param dataMap �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param colList ������ɺ���Ҫ���ص��ֶ�����
	 * @param isAutoGenKey �Ƿ񷵻������Ӽ�ֵ
	 * @return �������ֶε�ֵ
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String,Object> dataMap, String[] colList, boolean isAutoGenKey) throws Exception;

	
	/**
	 * �����¼
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param dataMap �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String,Object> dataMap) throws Exception;
	
	/**
	 * ���±��¼�Ķ���ֶ�
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param dataMap ���������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param condition �������
	 * @param condValues ���������б�
	 * @throws Exceptionc
	 */
	public void update(Connection conn, String table, Map<String,Object> dataMap, String condition, List<Object> condValues) throws Exception;
	
	/**
	 * ���±��¼��һ���ֶ�
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param name �������ֶ���
	 * @param value ����������
	 * @param condition �޲������������
	 * @throws Exception
	 */
	public void update(Connection conn, String table, String name, Object value, String condition) throws Exception;
	
	/**
	 * ɾ����¼
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param condition �������
	 * @param condValues ���������б�
	 * @throws Exception
	 */
	public void delete(Connection conn, String table, String preparedCond, List<Object> condValues) throws Exception;
	
	/**
	 * ��ȡһ����¼����map����
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param mustUnique �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return ����map
	 * @throws Exception
	 */
	public Map<String,Object> getOneRowAsMap(Connection conn, String table, List<String> cols, String condition, List<Object> condValues,
			boolean mustUnique) throws Exception;
	
	/**
	 * ��ȡһ����¼�������鷽ʽ���ء�
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param mustUnique �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(Connection conn, String table, List<String> cols, String condition, List<Object> condValues,
			boolean mustUnique) throws Exception;
	
	/**
	 * ͨ�ò�ѯ����List[map1, map2...]��ʽ����
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param start ��¼�Ŀ�ʼλ��
	 * @param max ��������¼����
	 * @param orderBy �����ֶ���
	 * @param groupBy �����ֶ���
	 * @param bAsc �Ƿ�����
	 * @return ��List[map1, map2...]��ʽ���أ�colsָ�����ֶ����ݣ����Ϊmax��
	 * @throws Exception
	 */
	public List<Map<String,Object>> searchAsMapList(Connection conn, String table, List<String> cols, String condition, List<Object> condValues, int start,
			int max, String orderBy, String groupBy, boolean bAsc) throws Exception;
	
	//���һ�������ݿ�LabelΪkey�ķ���,�����޸Ļ�Ӱ��������������д��һ������,ϣ������Ŀ�մ��ںϲ�����  by maj 2016-8-8
	public List<Map<String,Object>> searchAsMapListInLabel(Connection conn, String table, List<String> cols, String condition, List<Object> condValues, int start,
			int max, String orderBy, String groupBy, boolean bAsc) throws Exception;
	/**
	 * ͨ�ò�ѯ����List[array1, array2...]��ʽ����
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param start ��¼�Ŀ�ʼλ��
	 * @param max ��������¼����
	 * @param orderBy �����ֶ���
	 * @param groupBy �����ֶ���
	 * @param bAsc �Ƿ�����
	 * @return ��List[array1, array2...]��ʽ���أ�colsָ�����ֶ�����
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(Connection conn, String table, List<String> cols, String condition, List<Object> condValues,
			int start, int max, String orderBy, String groupBy, boolean bAsc) throws Exception;

	
	/**
	 * �õ���¼����
	 * @param conn ���ݿ�����
	 * @param table ����
	 * @param condition �������
	 * @param condValues ��������б�
	 * @param distinct ���ݿ�distinct
	 * @return ���������ļ�¼����
	 * @throws Exception
	 */
	public int getCount(Connection conn, String table, String condition, List<Object> condValues, String distinct)
			throws Exception;
	
	/**
	 * �õ�ָ�����е����ֵ
	 * @param type ��������
	 * @param conn
	 * @param table
	 * @param col
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public <T> T getMax(Class<T> type , Connection conn, String table, String col, String condition, List<Object> condValues)
			throws Exception;
	
	public <T> T getMin(Class<Integer> type, Connection conn, String table, String colum, String contidion, List<Object> condValues) throws Exception;
	
	/**
	 * ִ��sql��䣨������
	 *
	 * @param conn
	 * @param sql
	 * @throws Exception
	 */
	public void execSQL(Connection conn, String[] sqls) throws Exception;
	

}
