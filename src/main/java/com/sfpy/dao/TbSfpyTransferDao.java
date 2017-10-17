package com.sfpy.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sfpy.db.BizDB;
import com.sfpy.db.IBaseTbDao;
import com.sfpy.db.IDbOp;
import com.sfpy.entity.TbSfpyTransfer;
import com.sfpy.field.Field;
import com.sfpy.util.FieldsToStrListUtil;

public class TbSfpyTransferDao extends TbSfpyTransfer implements IBaseTbDao {

	private static TbSfpyTransferDao instance = new TbSfpyTransferDao();

	public static TbSfpyTransferDao getInstance() {
		if (instance == null) {
			instance = new TbSfpyTransferDao();
		}
		return instance;
	}

	/**
	 * 数据库操作实例
	 * 
	 * @return
	 */
	private BizDB getBizDB() {
		return BizDB.getInstance();
	}

	/**
	 * 数据库事务操作实例
	 * 
	 * @return
	 */
	private IDbOp getIDbOp() {
		return BizDB.getInstance().getOptimizeDbOp();
	}

	@Override
	public Map<String, Object> getDataById(Object id) throws Exception {
		return getDataById(ALL_FIELDS, id);
	}

	@Override
	public Map<String, Object> getDataById(Field[] fields, Object id) throws Exception {
		String condition = OBMS_ID.toSqlEQ(id);
		Map<String, Object> adminMap = getBizDB().getOneRowAsMap(TABLE, fields, condition);
		return adminMap;
	}

	@Override
	public Map<String, Object> getDataByIdWithConn(Connection conn, Object id) throws Exception {
		return getDataByIdWithConn(conn, ALL_FIELDS, id);
	}

	@Override
	public Map<String, Object> getDataByIdWithConn(Connection conn, Field[] fields, Object id) throws Exception {
		String condition = OBMS_ID.toSqlEQ(id);
		List<String> cols = FieldsToStrListUtil.convert(fields);
		Map<String, Object> adminMap = getIDbOp().getOneRowAsMap(conn, TABLE, cols, condition, null, true);
		return adminMap;
	}

	@Override
	public List<Map<String, Object>> searchByCond(String condition) throws Exception {
		return searchByCond(ALL_FIELDS, condition);
	}

	@Override
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition) throws Exception {
		return searchByCond(fields, condition, 0, Integer.MAX_VALUE, null, true);
	}

	@Override
	public List<Map<String, Object>> searchByCond(String condition, String orderBy, boolean bAsc) throws Exception {
		return searchByCond(ALL_FIELDS, condition, 0, Integer.MAX_VALUE, orderBy, bAsc);
	}

	@Override
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, String orderBy, boolean bAsc)
			throws Exception {
		return searchByCond(fields, condition, 0, Integer.MAX_VALUE, orderBy, bAsc);
	}

	@Override
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, int start, int max, String orderBy,
			boolean bAsc) throws Exception {
		return getBizDB().searchAsMapList(TABLE, fields, condition, null, start, max, orderBy, bAsc);
	}

	@Override
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition) throws Exception {
		return searchByCondWithConn(conn, ALL_FIELDS, condition);
	}

	@Override
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition)
			throws Exception {
		return searchByCondWithConn(conn, fields, condition, 0, Integer.MAX_VALUE, null, true);
	}

	@Override
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition, String orderBy,
			boolean bAsc) throws Exception {
		return searchByCondWithConn(conn, ALL_FIELDS, condition, 0, Integer.MAX_VALUE, orderBy, bAsc);
	}

	@Override
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition,
			String orderBy, boolean bAsc) throws Exception {
		return searchByCondWithConn(conn, fields, condition, 0, Integer.MAX_VALUE, orderBy, bAsc);
	}

	@Override
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition, int start,
			int max, String orderBy, boolean bAsc) throws Exception {
		List<String> cols = FieldsToStrListUtil.convert(fields);
		return getIDbOp().searchAsMapList(conn, TABLE, cols, condition, null, start, max, orderBy, null, bAsc);
	}

	@Override
	public void update(Field updateField, Object updateValue, String condition) throws Exception {
		getBizDB().update(TABLE, updateField.name, updateValue, condition);
	}

	@Override
	public void updateWithConn(Connection conn, Field updateField, Object updateValue, String condition)
			throws Exception {
		getIDbOp().update(conn, TABLE, updateField.name, updateValue, condition);
	}

	@Override
	public void insert(Map<String, Object> dataMap) throws Exception {
		getBizDB().insert(TABLE, dataMap);
	}

	@Override
	public void insertWithConn(Connection conn, Map<String, Object> dataMap) throws Exception {
		// 不存在则插入
		getIDbOp().insert(conn, TABLE, dataMap);
	}

	@Override
	public void update(Map<String, Object> dataMap, String condition) throws Exception {
		getBizDB().update(TABLE, dataMap, condition);
	}

	@Override
	public void updateWithConn(Connection conn, Map<String, Object> dataMap, String condition) throws Exception {
		getIDbOp().update(conn, TABLE, dataMap, condition, null);
	}

	@Override
	public int getCount(String condition, List<Object> condValues) throws Exception {
		return getBizDB().getCount(TABLE, condition, condValues);
	}

	@Override
	public int getCountWithConn(Connection conn, String condition, List<Object> condValues) throws Exception {
		return getIDbOp().getCount(conn, TABLE, condition, condValues, null);
	}

}
