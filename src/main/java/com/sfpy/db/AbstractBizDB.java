/**
 * @(#)AbstractBizDB.java
 * 
 * @author renw,xujun
 * @version 1.0 2006-8-1
 * 
 * Copyright (C) 2000,2006 , KOAL, Inc.
 */
package com.sfpy.db;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sfpy.field.Field;

/**
 * 
 * Purpose:ʵ�ֳ����IDbCall����װһϵ��IDbCall�Ĺ��÷���������Ҫ�����ݿ���д�����࣬���Լ̳д������ʵ��
 * ����ÿ��ʵ�������õ���ģʽ���г�ʼ���ͽ��й���
 * 
 * @see
 * @since 1.1.0
 */
public abstract class AbstractBizDB implements IDbCall, Serializable {

	private static final long serialVersionUID = 1L;

	protected static Logger log = LoggerFactory.getLogger(IDbCall.class);

	// ��Ϊ����������Ҫָ����ͬ�������ӳأ����Ӧ����������ָ������ֹ�ظ�ʹ��ͬ�����ӳس��ִ���
	// protected String DB_POOL = "BIZ_POOL";

	/**
	 * �ڲ�ʵ�ʵ��õ�IDbOpʵ���࣬ȱʡ�����Ż���ͨ��ʵ��
	 */
	protected IDbOp m_dbOp = new DbOpBase();

	/**
	 * ��������ʵ���Ż���IDbOpʵ����
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
	 * ��óص�����
	 * 
	 * @return
	 */
	abstract public String getPoolName();

	/**
	 * ���óص�����
	 * 
	 * @return
	 */
	abstract public void setPoolName(String poolName);

	/**
	 * ��ʼ�����ݿ�
	 * 
	 * @throws Exception
	 */
	public abstract void initDB() throws Exception;

	/**
	 * ��ȡFieldList���ֶ����б�
	 * 
	 * @param fields
	 * @return
	 */
	public static List<String> getListFromFields(Field[] fields) {
		if (fields == null) {
			return null;
		}
		List<String> fieldList = new ArrayList<String>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			fieldList.add(fields[i].name);
		}
		return fieldList;
	}

	/**
	 * �������ݲ���--����
	 * 
	 * @param table
	 * @param dataMap
	 * @throws Exception
	 */
	public int insert(String table, Map<String, Object> dataMap) throws Exception {
		Connection conn = getConn(false);
		try {
			return m_dbOp.insert(conn, table, dataMap);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--����
	 * 
	 * @param table
	 * @param dataMap
	 * @param isAutoGenKey
	 *            �Ƿ񷵻���������, true����
	 * @throws Exception
	 */
	public int insert(String table, Map<String, Object> dataMap, boolean isAutoGenKey) throws Exception {
		Connection conn = getConn(false);
		try {
			return m_dbOp.insert(conn, table, dataMap, isAutoGenKey);
		} finally {
			closeConn(conn);
		}
	}

	public int insert(String table, Map<String, Object> dataMap, String[] colList, boolean isAutoGenKey)
			throws Exception {
		Connection conn = getConn(false);
		try {
			return m_dbOp.insert(conn, table, dataMap, colList, isAutoGenKey);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--���루����ǰ�жϣ�
	 * 
	 * @param table
	 * @param dataMap
	 * @param cond
	 * @param condValues
	 * @throws Exception
	 */
	public int insertIfNotExists(String table, Map<String, Object> dataMap, String cond, List<Object> condValues)
			throws Exception {
		Connection conn = getConn(false);
		try {
			return m_dbOp.insertIfNotExists(conn, table, dataMap, cond, condValues);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ���±��¼�Ķ���ֶ�
	 * 
	 * @param table
	 *            ����
	 * @param dataMap
	 *            ���������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @throws Exception
	 */
	public void update(String table, Map<String, Object> dataMap, String condition, List<Object> condValues)
			throws Exception {
		Connection conn = getConn(false);

		try {
			m_dbOp.update(conn, table, dataMap, condition, condValues);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--����
	 * 
	 * @param table
	 * @param name
	 * @param value
	 * @param condition
	 * @throws Exception
	 */
	public void update(String table, String name, Object value, String condition) throws Exception {
		Connection conn = getConn(false);

		try {
			m_dbOp.update(conn, table, name, value, condition);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--����
	 * 
	 * @param table
	 * @param dataMap
	 * @param condition
	 * @throws Exception
	 */
	public void update(String table, Map<String, Object> dataMap, String condition) throws Exception {
		Connection conn = getConn(false);

		try {
			m_dbOp.update(conn, table, dataMap, condition, null);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--ɾ��
	 * 
	 * @param table
	 * @param condition
	 * @throws Exception
	 */
	public void delete(String table, String condition) throws Exception {
		Connection conn = getConn(false);
		try {
			m_dbOp.delete(conn, table, condition, null);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ɾ����¼
	 * 
	 * @param table
	 *            ����
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @throws Exception
	 */
	public void delete(String table, String preparedCond, List<Object> condValues) throws Exception {
		Connection conn = getConn(false);
		try {
			m_dbOp.delete(conn, table, preparedCond, condValues);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table) throws Exception {
		return searchAsMapList(table, null, null, null, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @return Map����
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields) throws Exception {
		return searchAsMapList(table, fields, null, null, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @return Map����
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields, String condition) throws Exception {
		return searchAsMapList(table, fields, condition, null, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields, String condition,
			List<Object> condValues) throws Exception {
		return searchAsMapList(table, fields, condition, condValues, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	/**
	 * �������ݲ���--��ѯ(���������ֶ�)
	 * 
	 * @param table
	 * @param condition
	 * @return Map����
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, String condition) throws Exception {
		return searchAsMapList(table, null, condition, null, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	/**
	 * �������ݲ���--��ѯ(���������ֶ�)
	 * 
	 * @param table
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, String condition, List<Object> condValues)
			throws Exception {
		return searchAsMapList(table, null, condition, condValues, 0, IDbOp.DEFAULT_MAX, null, false);
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @param start
	 * @param max
	 * @param orderBy
	 * @param bAsc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields, String condition,
			List<Object> condValues, int start, int max, String orderBy, boolean bAsc) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.searchAsMapList(conn, table, getListFromFields(fields), condition, condValues, start, max,
					orderBy, null, bAsc);
		} finally {
			closeConn(conn);
		}
	}

	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc)
					throws Exception {
		return searchAsMapList(table, AbstractBizDB.getListFromFields(fields), condition, condValues, start, max,
				orderBy, groupBy, bAsc);
	}

	/**
	 * ͨ�ò�ѯ����List[map1, map2...]��ʽ����
	 * 
	 * @param table
	 *            ����
	 * @param cols
	 *            ��Ҫ���ص��ֶ����б�
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @param start
	 *            ��¼�Ŀ�ʼλ��
	 * @param max
	 *            ��������¼����
	 * @param orderBy
	 *            �����ֶ���
	 * @param groupBy
	 *            �����ֶ���
	 * @param bAsc
	 *            �Ƿ�����
	 * @return ��List[map1, map2...]��ʽ���أ�colsָ�����ֶ����ݣ����Ϊmax��
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, List<String> cols, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc)
					throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsMapList(conn, table, cols, condition, condValues, start, max, orderBy, groupBy, bAsc);
		} finally {
			closeConn(conn);
		}
	}

	// ���һ�������ݿ�LabelΪkey�ķ���,�����޸Ļ�Ӱ��������������д��һ������,ϣ������Ŀ�մ��ںϲ����� by maj 2016-8-8
	public List<Map<String, Object>> searchAsMapListInLabel(String table, List<String> cols, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc)
					throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsMapListInLabel(conn, table, cols, condition, condValues, start, max, orderBy, groupBy,
					bAsc);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, null, null, null, 0, IDbOp.DEFAULT_MAX, null, null, false);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), null, null, 0, IDbOp.DEFAULT_MAX,
					null, null, false);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, List<String> cols) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, cols, null, null, 0, IDbOp.DEFAULT_MAX, null, null, false);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), condition, null, 0,
					IDbOp.DEFAULT_MAX, null, null, false);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param orderBy
	 * @param bAsc
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, String orderBy,
			boolean bAsc) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), condition, null, 0,
					IDbOp.DEFAULT_MAX, orderBy, null, bAsc);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, List<Object> condValues)
			throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), condition, condValues, 0,
					IDbOp.DEFAULT_MAX, null, null, false);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @param start
	 * @param max
	 * @param orderBy
	 * @param bAsc
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, List<Object> condValues,
			int start, int max, String orderBy, boolean bAsc) throws Exception {
		Connection conn = getConn(true);
		List<Object[]> result = null;
		try {

			result = m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), condition, condValues, start, max,
					orderBy, null, bAsc);
		} finally {
			closeConn(conn);
		}
		return result;
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @param start
	 * @param max
	 * @param orderBy
	 * @param groupBy
	 * @param bAsc
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, List<Object> condValues,
			int start, int max, String orderBy, String groupBy, boolean bAsc) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, getListFromFields(fields), condition, condValues, start, max,
					orderBy, groupBy, bAsc);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯ
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @param start
	 * @param max
	 * @param orderBy
	 * @param groupBy
	 * @param bAsc
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, List<String> fields, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc)
					throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.searchAsArrayList(conn, table, fields, condition, condValues, start, max, orderBy, groupBy,
					bAsc);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯһ������
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, Field[] fields, String condition) throws Exception {
		return getOneRowAsArray(table, fields, condition, null);
	}

	/**
	 * �������ݲ���--��ѯһ������
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, Field[] fields, String condition, List<Object> condValues)
			throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.getOneRowAsArray(conn, table, getListFromFields(fields), condition, condValues, true);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯһ������
	 * 
	 * @param table
	 * @param cols
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, List<String> cols, String condition, List<Object> condValues)
			throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.getOneRowAsArray(conn, table, cols, condition, condValues, true);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ��ȡһ����¼�������鷽ʽ���ء�
	 * 
	 * @param table
	 *            ����
	 * @param cols
	 *            ��Ҫ���ص��ֶ����б�
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @param mustUnique
	 *            �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, List<String> cols, String condition, List<Object> condValue,
			boolean mustUnique) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.getOneRowAsArray(conn, table, cols, condition, condValue, true);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯһ������
	 * 
	 * @param table
	 * @param cols
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOneRowAsMap(String table, List<String> cols, String condition) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.getOneRowAsMap(conn, table, cols, condition, null, true);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �������ݲ���--��ѯһ������
	 * 
	 * @param table
	 * @param cols
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOneRowAsMap(String table, Field[] fields, String condition) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.getOneRowAsMap(conn, table, getListFromFields(fields), condition, null, true);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ��ȡһ����¼����map����
	 * 
	 * @param table
	 *            ����
	 * @param cols
	 *            ��Ҫ���ص��ֶ����б�
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @param mustUnique
	 *            �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return ����map
	 * @throws Exception
	 */
	public Map<String, Object> getOneRowAsMap(String table, List<String> cols, String condition,
			List<Object> condValues, boolean mustUnique) throws Exception {
		Connection conn = getConn(true);

		try {
			return m_dbOp.getOneRowAsMap(conn, table, cols, condition, condValues, mustUnique);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * �����¼��
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public int getCount(String table) throws Exception {
		return getCount(table, null, null, null);
	}

	/**
	 * �����¼��
	 * 
	 * @param table
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getCount(String table, String condition) throws Exception {
		return getCount(table, condition, null, null);
	}

	/**
	 * �����¼��
	 * 
	 * @param table
	 * @param condition
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int getCount(String table, String condition, Object value) throws Exception {

		return getCount(table, condition, Arrays.asList(new Object[] { value }), null);
	}

	/**
	 * �����¼��
	 * 
	 * @param table
	 * @param condition
	 * @param condValues
	 * @return
	 * @throws Exception
	 */
	public int getCount(String table, String condition, List<Object> condValues) throws Exception {
		return getCount(table, condition, condValues, null);
	}

	/**
	 * �����¼��
	 * 
	 * @param table
	 * @param condition
	 * @param condValues
	 * @param distinct
	 * @return
	 * @throws Exception
	 */
	public int getCount(String table, String condition, List<Object> condValues, String distinct) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.getCount(conn, table, condition, condValues, distinct);
		} finally {
			closeConn(conn);
		}
	}

	public Integer getMaxInt(String table, String col) throws Exception {
		return getMaxInt(table, col, null);
	}

	public Integer getMaxInt(String table, String col, String condition) throws Exception {
		return getMaxInt(table, col, condition, null);
	}

	public Integer getMaxInt(String table, String col, String condition, List<Object> condValues) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.getMax(Integer.class, conn, table, col, condition, condValues);
		} finally {
			closeConn(conn);
		}
	}

	public Integer getMinInt(String table, String col) throws Exception {
		return getMaxInt(table, col, null);
	}

	public Integer getMinInt(String table, String col, String condition) throws Exception {
		return getMaxInt(table, col, condition, null);
	}

	public Integer getMinInt(String table, String col, String condition, List<Object> condValues) throws Exception {
		Connection conn = getConn(true);
		try {
			return m_dbOp.getMin(Integer.class, conn, table, col, condition, condValues);
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ����ID�鵽ĳ����һ�ֶ�(�磺���ݲ���ID�鵽��������)
	 * 
	 * @author huangc (2006-07-24)
	 * @param strTable
	 *            - Ҫ��ѯ�ı�����(�磺"TB_DEPART")
	 * @param fieldID
	 *            - Ҫ��ѯ��ID�ֶ�(�磺TbDepart.DEPART_ID)
	 * @param fieldObject
	 *            - Ҫ�����ֶ�(�磺TbDepart.DEPART_NAME)
	 * @param param
	 *            - Ҫ��ѯ��IDֵ(�磺map.get(TbDepart.DEPART_NAME.name))
	 * @return ��IDֵƥ�������
	 */
	public Object getObjectFromID(String strTable, Field fieldID, Field fieldObject, Object param) throws Exception {
		return getObjectFromID(strTable, fieldID, fieldObject, param, null);
	}

	/**
	 * ����ID�鵽ĳ����һ�ֶ�(�磺���ݲ���ID�鵽��������)
	 * 
	 * @author huangc (2006-07-24)
	 * @param strTable
	 *            - Ҫ��ѯ�ı�����(�磺"TB_DEPART")
	 * @param fieldID
	 *            - Ҫ��ѯ��ID�ֶ�(�磺TbDepart.DEPART_ID)
	 * @param fieldObject
	 *            - Ҫ�����ֶ�(�磺TbDepart.DEPART_NAME)
	 * @param param
	 *            - Ҫ��ѯ��IDֵ(�磺map.get(TbDepart.DEPART_NAME.name))
	 * @param otherCond
	 *            - ��������(�磺TbDepart.DEPART_PARENT.name + " IS NOT NULL")
	 * @return ��IDֵƥ�������
	 */
	public Object getObjectFromID(String strTable, Field fieldID, Field fieldObject, Object param, String otherCond)
			throws Exception {
		Object[] oneRec;
		Field[] m_fields = { fieldObject, };
		String cond = fieldID.toSqlEQ(param);
		if (otherCond != null) {
			cond += " AND " + otherCond;
		}
		List<?> ob = searchAsArrayList(strTable, m_fields, cond);
		if (ob.size() == 0) {
			return null;
		} else {
			oneRec = (Object[]) ob.get(0);
			return oneRec[0];
		}
	}

	/**
	 * ��ȡ���ݿ����ӣ�����ʵ����δ��ʼ��ʱ�Ƚ��г�ʼ��)
	 * 
	 * @param bAutoCommit
	 *            �Ƿ��Զ��ύ
	 * @return
	 * @throws Exception
	 */
	public Connection getConn(boolean bAutoCommit) throws Exception {
		return ConnectionPoolManager.getInstance().getConnection(ConnectionPoolManager.poolName);
	}

	/**
	 * �ͷ����ݿ�����
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void closeConn(Connection conn) throws Exception {
		if (conn == null)
			return;
		try {
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (Exception e) {
			log.error("�ύcommit�쳣(" + getPoolName() + ")", e);
		} finally {
			try {
				// conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e) {
				log.error("�ر�conn��", e);
			}
		}
	}

	/**
	 * �ж�һ��ֵ�Ƿ��Ѵ������ݿ��� true:���ڣ�flase:������
	 * find_valueҪ��Ѷ��ֵ��fieldҪ��ѯ�ֶε���Ϣ��tableName������ifString��ѯ���ֶ��Ƿ�Ϊ�ַ�
	 */
	public boolean getValue(final String find_value, final Field field, final String tableName,
			final boolean ifString) {
		return getValue(find_value, field, tableName, ifString, "");
	}

	/**
	 * otherCon��������
	 */
	public boolean getValue(final String find_value, final Field field, final String tableName, final boolean ifString,
			final String otherCond) {

		Field[] fields = { field };
		StringBuffer condition = new StringBuffer(field.name);
		if (ifString) {
			condition.append("='");
			condition.append(find_value);
			condition.append("'");
		} else {
			condition.append("=");
			condition.append(find_value);
		}
		if ((otherCond != null) && (!otherCond.equals(""))) {
			condition.append(" and ");
			condition.append(otherCond);
		}

		try {
			List<?> ob = searchAsArrayList(tableName, fields, condition.toString());
			if (ob.size() >= 1) {
				return true;
			}
		} catch (Exception e) {
			// System.out.println("erro:" + e);
		}
		return false;
	}

	/**
	 * ����ִ��sql���
	 * 
	 * @param table
	 * @param condition
	 * @throws Exception
	 */
	public void execSQL(String[] sqls) throws Exception {
		Connection conn = getConn(false);
		try {
			m_dbOp.execSQL(conn, sqls);
		} finally {
			closeConn(conn);
		}
	}
}