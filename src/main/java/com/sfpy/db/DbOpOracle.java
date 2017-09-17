/*
 * Created on 2006-11-6
 *
 * @author renwei
 * 
 * Copyright (C) 2006 KOAL SOFTWARE.
 */
package com.sfpy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Oracle数据库优化，优化分页查询语句。
 * @version 1.0
 * @see IDbOp
 * @since 1.0
 */
public class DbOpOracle extends DbOpBase {

	private static final long serialVersionUID = 1L;
	
	final Logger log = LoggerFactory.getLogger(DbOpBase.class);
	
	public static void register(){
		IDbOp self = new DbOpOracle();
		// 注册自己
		DbOpMgr.register("oracle.jdbc.driver.OracleDriver", 0, self);
		DbOpMgr.register("oracle.jdbc.driver.OracleDriver", 8, self);
		DbOpMgr.register("oracle.jdbc.driver.OracleDriver", 9, self);
		DbOpMgr.register("oracle.jdbc.driver.OracleDriver", 10, self);
	}
	
	@Override
	public List<?> search(Connection conn, String table, List<String> cols,
			String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc, boolean bMap)
			throws Exception {
		if (conn == null || table == null) {
			String err = "调用search参数错误";
			log.error(err);
			throw new IllegalArgumentException(err);
		}
		
		// 1 构造语句
		StringBuffer sql = new StringBuffer(160);
		int i;
		
		// 构造分页查询 {{
		// 参见：google “Oracle分页查询语句”
		if (start > 0) {
			sql.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			sql.append("select * from ( ");
		}
		// }}
			
		if (cols == null) {
			sql.append("select * from ");
		} else {
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
		
		// 构造分页查询 {{
		if (start > 0) {
			sql.append(" ) row_ where rownum <= " + (start + max) + ") where rownum_ > " + start);
		} else {
			sql.append(" ) where rownum <= " + max);
		}
		// }}
		
		if (log.isDebugEnabled())
			log.debug("search SQL: " + sql);
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		if (condValues != null) {
		    preparedStatementSetValues(pstmt, condValues);
		}
			
		// 2 执行查询
		List<Object> ret = new ArrayList<Object>();
		try {
			ResultSet rs = pstmt.executeQuery();
			
			// 3 构造结果
			int colCount = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();
				
			int startCounter = start;
			int fetchCount = 0;
			if (bMap) {
				// map结果
				while (rs.next()) {
					if (fetchCount == max)
						break;
						
					if (startCounter >= start) {
						Map<String,Object> oneRec = new HashMap<String,Object>();
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
				// array结果
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

}
