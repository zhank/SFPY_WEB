/*
 * Created on 2006-2-7
 *
 * @author renwei
 * 
 * Copyright (C) 2000, 2006, KOAL SOFTWARE.
 */
package com.sfpy.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IDbOp��ͨ��ʵ�֣��������������ݿ���������ض����ݿ���ص��Ż�����Լ̳д��࣬������Ҫ�Ż��ķ�����
 *
 * <p>
 * �������������Apache��commons-dbutils����dbutils���Ǹ�����Щ���Ҷ�Date��Ĵ���Ҫ��Щ�Ķ�������δʹ�á�
 *
 * @author zhangk
 * @version 1.0
 * @see IDbOp
 * @since 1.0
 */
public class DbOpBase implements IDbOp, Serializable {

	private static final long serialVersionUID = 1L;
	final static Logger log = LoggerFactory.getLogger(DbOpBase.class);

	/**
	 * ������ֵ�����͵��ò�ͬ��setXXX����
	 *
	 * @param pstmt
	 * @param values
	 * @throws Exception
	 */
	protected static final void preparedStatementSetValues(PreparedStatement pstmt, List<Object> values)
			throws Exception {
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) instanceof java.util.Date) {
				java.util.Date date = (java.util.Date) values.get(i);
				pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
			} else {
				pstmt.setObject(i + 1, values.get(i));
			}
		}

	}

	/**
	 * ����ResultSet����е����ݣ���������ݿ������Զ�ת��Ϊjava����
	 *
	 * @param index
	 * @param rs
	 * @param rsmd
	 * @return
	 * @throws Exception
	 */
	protected static final Object getRsObj(int index, ResultSet rs, ResultSetMetaData rsmd) throws Exception {
		switch (rsmd.getColumnType(index)) {
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
		case Types.BLOB:
			byte[] data = rs.getBytes(index);
			if (data != null) {
				return new String(data);
			} else {
				return null;
			}
		case Types.CLOB:
			Clob clob = rs.getClob(index);
			if (clob != null) {
				return clob.getSubString(1, Integer.valueOf(clob.length() + ""));
			} else {
				return null;
			}
		case Types.NUMERIC:
			//oracle û�� boolean��ר��sqlType, ��number(1)����  by maj 2016.11.02
			switch(rsmd.getPrecision(index))
			{
	            case 1:
	            	//��Ϊoracle�������ʹ��  by maj 2016.11.02
	            	if (rsmd.getClass().getPackage().getName().equals("oracle.jdbc.driver"))
	            	{
	            		Boolean b = rs.getBoolean(index);
	            		if (rs.wasNull())
	            			b = null;
	            		return b;
	            	}
	            	else
	            	{
	            		Integer i = rs.getInt(index);
	            		if (rs.wasNull())
	            			i = null;
	            		return i;
	            	}
	            default:
	            	Integer i = rs.getInt(index);
	            	if (rs.wasNull())
            			i = null;
	            	return i;
			}

		case Types.BIGINT:
			return new Integer(rs.getInt(index));
		case Types.DECIMAL:
			BigDecimal decimal = rs.getBigDecimal(index);
			if (decimal == null) {
				return null;
			} else {
				return decimal.intValue();
			}
		case Types.DATE:
		case Types.TIMESTAMP:
			Timestamp timestamp = rs.getTimestamp(index);
			if (timestamp == null) {
				return null;
			} else {
				return new java.util.Date(timestamp.getTime());
			}
		case Types.ARRAY:
		case Types.BIT:
		case Types.BOOLEAN:
		case Types.CHAR:
		case Types.DATALINK:
		case Types.DISTINCT:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.INTEGER:
		case Types.JAVA_OBJECT:
		case Types.LONGNVARCHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.NCLOB:
		case Types.NULL:
		case Types.NVARCHAR:
		case Types.OTHER:
		case Types.REAL:
		case Types.REF:
		case Types.ROWID:
		case Types.SMALLINT:
		case Types.SQLXML:
		case Types.STRUCT:
		case Types.TIME:
		case Types.TINYINT:
			return rs.getObject(index);
		default:
			return rs.getObject(index);
		}

	}

	/**
	 * �����¼
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param dataMap
	 *            �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String, Object> dataMap) throws Exception {
		return this.insert(conn, table, dataMap, false);
	}

	/**
	 * �����¼
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param dataMap
	 *            �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param isAutoGenKey
	 *            �Ƿ�����
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String, Object> dataMap, boolean isAutoGenKey) throws Exception
	{
		return this.insert(conn, table, dataMap, null, isAutoGenKey);
	}
	/**
	 * �����¼
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param dataMap
	 *            �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param colList
	 * 			     ������ɺ���Ҫ���ص��ֶ�����
	 * @param isAutoGenKey
	 *            �Ƿ�����
	 * @throws Exception
	 */
	public int insert(Connection conn, String table, Map<String, Object> dataMap, String[] colList, boolean isAutoGenKey)
			throws Exception {
		// ����������
		if (conn == null || table == null || dataMap == null) {
			String err = "����insert��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		// ���������䣬���磺insert into tb_test (name, type, id) values (?, ?, ?)
		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		StringBuffer sql = new StringBuffer(64 + entrySet.size() * 8);
		sql.append("insert into " + table + " (");

		List<Object> values = new ArrayList<Object>(entrySet.size());
		for (Map.Entry<String, Object> entry : entrySet) {
			if (values.size() > 0)
				sql.append(",");

			sql.append(entry.getKey());
			values.add(entry.getValue());
		}
		sql.append(") values (");

		for (int i = 0; i < entrySet.size(); i++) {
			if (i > 0)
				sql.append(",");
			sql.append("?");
		}
		sql.append(")");

		if (log.isDebugEnabled())
			log.debug("insert SQL: " + sql);
		// ���ò���
		PreparedStatement pstmt = null;
		if (isAutoGenKey) {
			if (colList != null)
				pstmt = conn.prepareStatement(sql.toString(), colList);
			else
			{
				if (getDbType(conn).toLowerCase().contains("oracle"))
					pstmt = conn.prepareStatement(sql.toString(), new String[]{getTableId(conn, table)});
				else
					pstmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			}
		} else {
			pstmt = conn.prepareStatement(sql.toString());
		}

		preparedStatementSetValues(pstmt, values);

		// ִ��
		ResultSet rs = null;

		int autoIncreaseId = 0;
		try {
			pstmt.executeUpdate();
			if (isAutoGenKey)
			{
				rs = pstmt.getGeneratedKeys();
				if (rs.next())
				{
					autoIncreaseId = rs.getInt(1);
				}
			}
		} finally {
			if (rs != null)
			{
				rs.close();
			}
			pstmt.close();
		}

		return autoIncreaseId;
	}

	/**
	 * ���±��¼�Ķ���ֶ�
	 *
	 * @param conn
	 *            ���ݿ�����
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
	public void update(Connection conn, String table, Map<String, Object> dataMap, String condition,
			List<Object> condValues) throws Exception {
		// ����������
		if (conn == null || table == null || dataMap == null) {
			String err = "����update��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		// ���������䣬���磺update tb_test set name=?, type=?, id=? where xx
		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		StringBuffer sql = new StringBuffer(64 + entrySet.size() * 8);
		sql.append("update " + table + " set ");

		List<Object> values = new ArrayList<Object>(entrySet.size());
		for (Map.Entry<String, Object> entry : entrySet) {
			if (values.size() > 0)
				sql.append(",");
			sql.append(entry.getKey() + "=?");
			values.add(entry.getValue());
		}

		if (condition != null)
			sql.append(" where " + condition);

		if (log.isDebugEnabled())
			log.debug("update SQL: " + sql);

		// ���ò���
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		if (condition != null && condValues != null) {
			// ������������������ں���
			values.addAll(condValues);
		}
		preparedStatementSetValues(pstmt, values);

		// ִ��
		try {
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	/**
	 * ���±��¼��һ���ֶ�
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param name
	 *            �������ֶ���
	 * @param value
	 *            ����������
	 * @param condition
	 *            �޲������������
	 * @throws Exception
	 */
	public void update(Connection conn, String table, String name, Object value, String condition)
			throws Exception {
		// ����������
		if (conn == null || table == null || name == null || value == null || condition == null) {
			String err = "����update��������";
			log.error(err);
			throw new IllegalArgumentException("����update��������");
		}

		// ���������䣬���磺update tb_test set name=?
		StringBuffer sql = new StringBuffer(64);
		sql.append("update " + table + " set " + name + "=?");
		sql.append(" where " + condition);

		if (log.isDebugEnabled())
			log.debug("update SQL: " + sql);

		// ���ò���
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		// pstmt.setObject(1, value);
		// TimeStamp���ͣ�����ʹ��new Date����
		List<Object> values = new ArrayList<Object>();
		values.add(value);
		preparedStatementSetValues(pstmt, values);

		// ִ��
		try {
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	/**
	 * ɾ����¼
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @throws Exception
	 */
	public void delete(Connection conn, String table, String condition, List<Object> condValues)
			throws Exception {
		if (conn == null || table == null) {
			String err = "����delete��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		String sql;
		if (condition == null)
			sql = "delete from " + table;
		else
			sql = "delete from " + table + " where " + condition;

		if (log.isDebugEnabled())
			log.debug("delete SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);

		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}
		try {
			pstmt.executeUpdate();
		} finally {
			pstmt.close();
		}
	}

	/**
	 * ��ȡһ����¼����map����
	 *
	 * @param conn
	 *            ���ݿ�����
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
	public Map<String, Object> getOneRowAsMap(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, boolean mustUnique) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		getOneRow(conn, table, cols, condition, condValues, mustUnique, map);
		return map;
	}

	/**
	 * ��ȡһ����¼�������鷽ʽ���ء�
	 *
	 * @param conn
	 *            ���ݿ�����
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
	public Object[] getOneRowAsArray(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, boolean mustUnique) throws Exception {
		return getOneRow(conn, table, cols, condition, condValues, mustUnique, null);
	}

	/**
	 * ��ȡһ�����ݵ�ʵ�֣�����map����������map�������鷽ʽ����
	 *
	 * @param conn
	 *            ���ݿ�����
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
	 * @param map
	 *            ����Ϊnull�����÷������ݵ����У����������鷵��
	 * @return ��map��Ϊnull�����÷������ݵ�map������null�����������鷵��
	 * @throws Exception
	 */
	protected Object[] getOneRow(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, boolean mustUnique, Map<String, Object> map) throws Exception {
		if (conn == null || table == null) {
			String err = "����getOneRow��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		StringBuffer sql = new StringBuffer(32);

		int i;
		if (cols == null)
			sql.append("select * from ");
		else {
			sql.append("select ");
			for (i = 0; i < cols.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append(cols.get(i));
			}
			sql.append(" from ");
		}
		sql.append(table);

		if ((condition != null) && (condition.length() > 0)) {
			sql.append(" where " + condition);
		}

		if (log.isDebugEnabled())
			log.debug("getOneRow SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		try {
			if (condValues != null) {
				preparedStatementSetValues(pstmt, condValues);
			}

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				// ������
				String err = "δ����Ҫ���ҵ����ݡ�[" + table + ":" + sql;
				log.error(err);
				throw new Exception(err);
			}

			Object[] resArray = null;
			if (map != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				for (i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), getRsObj(i, rs, rsmd));
				}
			} else {
				ResultSetMetaData rsmd = rs.getMetaData();
				resArray = new Object[rsmd.getColumnCount()];
				for (i = 1; i <= rsmd.getColumnCount(); i++) {
					resArray[i - 1] = getRsObj(i, rs, rsmd);
				}
			}

			if (mustUnique) {
				if (rs.next()) {
					String err = "�����ҵ������Ψһ��[" + table + ":" + sql;
					log.error(err);
					throw new Exception(err);
				}
			}
			if (map != null) {
				return null;
			} else {
				return resArray;
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * ͨ�ò�ѯ����List[map1, map2...]��ʽ����
	 *
	 * @param conn
	 *            ���ݿ�����
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
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchAsMapList(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, int start, int max, String orderBy, String groupBy,
			boolean bAsc) throws Exception {
		return (List<Map<String, Object>>) search(conn, table, cols, condition, condValues, start, max,
				orderBy, groupBy, bAsc, true);
	}

	//���һ�������ݿ�LabelΪkey�ķ���,�����޸Ļ�Ӱ��������������д��һ������,ϣ������Ŀ�մ��ںϲ�����  by maj 2016-8-8
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchAsMapListInLabel(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, int start, int max, String orderBy, String groupBy,
			boolean bAsc) throws Exception {
		return (List<Map<String, Object>>) searchInLabel(conn, table, cols, condition, condValues, start, max,
				orderBy, groupBy, bAsc, true);
	}
	/**
	 * ͨ�ò�ѯ����List[array1, array2...]��ʽ����
	 *
	 * @param conn
	 *            ���ݿ�����
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
	 * @return ��List[array1, array2...]��ʽ���أ�colsָ�����ֶ�����
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> searchAsArrayList(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, int start, int max, String orderBy, String groupBy,
			boolean bAsc) throws Exception {
		return (List<Object[]>) search(conn, table, cols, condition, condValues, start, max, orderBy,
				groupBy, bAsc, false);
	}

	/**
	 * ��ѯ�ľ���ʵ�֣�������������������map-list����array-list����
	 *
	 * @param conn
	 *            ���ݿ�����
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
	 * @param bMap
	 *            true: ��List[map1, map1...]��ʽ����; false: ��List[array1,
	 *            array2...]��ʽ����
	 * @return ��bMap��������
	 * @throws Exception
	 */
	protected List<?> search(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc,
			boolean bMap) throws Exception {
		if (conn == null || table == null) {
			String err = "����search��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		// 1 �������
		StringBuffer sql = new StringBuffer(64);
		int i;
		if (cols == null)
			sql.append("select * from ");
		else {
			sql.append("select ");
			for (i = 0; i < cols.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append(cols.get(i));
			}
			sql.append(" from ");
		}
		sql.append(table);

		if ((condition != null) && (condition.length() > 0)) {
			sql.append(" where " + condition);
		}

		if (groupBy != null) {
			sql.append(" group by " + groupBy);
		}

		if (orderBy != null) {
			sql.append(" order by " + orderBy);
			if (!bAsc)
				sql.append(" desc");
		}

		if (log.isDebugEnabled())
			log.debug("search SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}

		// 2 ִ�в�ѯ
		List<Object> ret = new ArrayList<Object>();
		try {
			ResultSet rs = pstmt.executeQuery();

			// 3 ������
			int colCount = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();

			int startCounter = 0;
			int fetchCount = 0;
			if (bMap) {
				// map���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Map<String, Object> oneRec = new HashMap<String, Object>();
						for (i = 1; i <= colCount; i++) {
							oneRec.put(rsmd.getColumnName(i), getRsObj(i, rs, rsmd));
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			} else {
				// array���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Object[] oneRec = new Object[colCount];
						for (i = 0; i < colCount; i++) {
							oneRec[i] = getRsObj(i + 1, rs, rsmd);
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
		}

		return ret;
	}

	//���һ�������ݿ�LabelΪkey�ķ���,�����޸Ļ�Ӱ��������������д��һ������,ϣ������Ŀ�մ��ںϲ�����  by maj 2016-8-8
	protected List<?> searchInLabel(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc, boolean bMap)
			throws Exception {
		if (conn == null || table == null) {
			String err = "����search��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		// 1 �������
		StringBuffer sql = new StringBuffer(64);
		int i;
		if (cols == null)
			sql.append("select * from ");
		else {
			sql.append("select ");
			for (i = 0; i < cols.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append(cols.get(i));
			}
			sql.append(" from ");
		}
		sql.append(table);

		if ((condition != null) && (condition.length() > 0)) {
			sql.append(" where " + condition);
		}

		if (groupBy != null) {
			sql.append(" group by " + groupBy);
		}

		if (orderBy != null) {
			sql.append(" order by " + orderBy);
			if (!bAsc)
				sql.append(" desc");
		}

		if (log.isDebugEnabled())
			log.debug("search SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}

		// 2 ִ�в�ѯ
		List<Object> ret = new ArrayList<Object>();
		try {
			ResultSet rs = pstmt.executeQuery();

			// 3 ������
			int colCount = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();

			int startCounter = 0;
			int fetchCount = 0;
			if (bMap) {
				// map���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Map<String, Object> oneRec = new HashMap<String, Object>();
						for (i = 1; i <= colCount; i++) {
							oneRec.put(rsmd.getColumnLabel(i), getRsObj(i, rs, rsmd));
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			} else {
				// array���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Object[] oneRec = new Object[colCount];
						for (i = 0; i < colCount; i++) {
							oneRec[i] = getRsObj(i + 1, rs, rsmd);
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
		}

		return ret;

	}
	/**
	 * �õ���¼����
	 *
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ��������б�
	 * @param distinct
	 *            ���ݿ�distinct
	 * @return ���������ļ�¼����
	 * @throws Exception
	 */
	public int getCount(Connection conn, String table, String condition, List<Object> condValues,
			String distinct) throws Exception {
		if (conn == null || table == null) {
			String err = "����getCount��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		StringBuffer sql = new StringBuffer(32);
		if (distinct == null)
			sql.append("select count(*) from " + table);
		else
			sql.append("select count(distinct " + distinct + ") from " + table);

		if (condition != null)
			sql.append(" where " + condition);

		if (log.isDebugEnabled())
			log.debug("getCount SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}
		try {

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) // ������
				return 0;

			return rs.getInt(1);
		} finally {
				pstmt.close();
		}
	}

	/**
	 * �õ���¼����
	 *
	 * @param type
	 *            ��������
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ��������б�
	 * @param distinct
	 *            ���ݿ�distinct
	 * @return ���������ļ�¼����
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMax(Class<T> type, Connection conn, String table, String col, String condition,
			List<Object> condValues) throws Exception {
		if (conn == null || table == null) {
			String err = "����getCount��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		StringBuffer sql = new StringBuffer(32);
		sql.append("select max(" + col + ") from " + table);

		if (condition != null)
			sql.append(" where " + condition);

		if (log.isDebugEnabled())
			log.debug("getMax SQL: " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}
		try {
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) // ������
				return null;
			return ((T) rs.getObject(1));
		} finally {
				pstmt.close();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getMin(Class<Integer> type, Connection conn, String table, String col, String condition,
			List<Object> condValues) throws Exception {
		if (conn == null || table == null) {
			String err = "����getCount��������";
			log.error(err);
			throw new IllegalArgumentException(err);
		}

		StringBuffer sql = new StringBuffer(32);
		sql.append("select min(" + col + ") from " + table);

		if (condition != null) {
			sql.append(" where " + condition);
		}

		if (log.isDebugEnabled()) {
			log.debug("getMin SQL: " + sql);
		}

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}
		try {
			ResultSet rs = pstmt.executeQuery();
			// ������
			if (!rs.next()) {
				return null;
			}
			return ((T) rs.getObject(1));
		} finally {
				pstmt.close();
		}
	}

	/**
	 * ֱ������ִ��sql��䣬�������κ�ֵ
	 */
	public void execSQL(Connection conn, String[] sqls) throws Exception {
		Statement statement = conn.createStatement();
		try {
			// ������ִ��
			// һ��ִ�е�������
			int linePerTime = 30;
			int remainder = sqls.length % linePerTime;
			int loopTime = (sqls.length / linePerTime) + (remainder > 0 ? 1 : 0);
			for (int i = 0; i < loopTime; i++) {
				statement.clearBatch();
				String[] childSqls = Arrays.copyOfRange(sqls, 0 + linePerTime * i, linePerTime * i
						+ (i == (loopTime - 1) ? remainder : linePerTime));
				for (String sql : childSqls) {
					statement.addBatch(sql);
				}
				statement.executeBatch();
			}

		} finally {
				statement.close();
		}
	}

	public int insertIfNotExists(Connection conn, String table, Map<String, Object> dataMap,
			String condition, List<Object> condValues) throws Exception {
		if (condition != null && (getCount(conn, table, condition, condValues, null) > 0)) {
			throw new Exception("��¼�Ѿ�����");
		}
		return insert(conn, table, dataMap, true);
	}


	private String getTableId(Connection conn, String tableName) throws SQLException
	{
		String result = null;
		String userName = conn.getMetaData().getUserName();
		String dbType = getDbType(conn).toLowerCase();
		ResultSet rs = conn.getMetaData().getPrimaryKeys(null, getLegalUserName(userName, dbType), tableName);
		while(rs.next())
		{
			result = (String) rs.getObject("COLUMN_NAME");
		}
		return result;
	}

	private String getDbType(Connection conn) throws SQLException
	{
		return conn.getMetaData().getDatabaseProductName();
	}

	private String getLegalUserName(String user, String type)
	{
		String result;
        if (user != null)
        {
            if (type.equals("oracle"))
                result = user.toUpperCase();
            else
                result = user;
        }
        else
            result = "public";
        return result;
	}

}
