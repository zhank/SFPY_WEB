package com.sfpy.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sfpy.field.Field;

/**
 * 数据库操作视图DAO基础接口，视图只有查询接口
 * @author sfpy
 *
 */
public interface IBaseViewDao {
	
	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataById(Object id) throws Exception;
	
	/**
	 * 根据主键查询
	 * @param fields 查询结果项Field
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataById(Field[] fields, Object id) throws Exception;
	
	/**
	 * 带事务：根据主键查询
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataByIdWithConn(Connection conn, Object id) throws Exception; 
	
	/**
	 * 带事务：根据主键查询
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param fields 查询结果项Field
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDataByIdWithConn(Connection conn, Field[] fields, Object id) throws Exception; 
	
	/**
	 * 根据条件查询数据
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(String condition) throws Exception;
	
	/**
	 * 根据条件查询数据
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition) throws Exception;
	
	/**
	 * 根据条件查询数据
	 * @param condition 查询条件
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 根据条件查询数据
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 根据条件分页查询数据
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @param start 查询记录的开始位置
	 * @param max 每页记录数
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCond(Field[] fields, String condition, int start, int max, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 带事务：根据条件查询数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition) throws Exception;
	
	/**
	 * 带事务：根据条件查询数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition) throws Exception;

	/**
	 * 带事务：根据条件查询数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 带事务：根据条件查询数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 带事务：根据条件分页查询数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param fields 查询结果项Field
	 * @param condition 查询条件
	 * @param start 查询记录的开始位置
	 * @param max 每页记录数
	 * @param orderBy 排序字段
	 * @param bAsc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchByCondWithConn(Connection conn, Field[] fields, String condition, int start, int max, String orderBy, boolean bAsc) throws Exception;
	
	/**
	 * 根据条件查询数据数量
	 * @param condition 查询条件
	 * @param condValues 条件值
	 * @return
	 * @throws Exception
	 */
	public int getCount(String condition, List<Object> condValues) throws Exception;
	
	/**
	 * 带事务：根据条件查询数据数量
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param condition 查询条件
	 * @param condValues 条件值
	 * @return
	 * @throws Exception
	 */
	public int getCountWithConn(Connection conn, String condition, List<Object> condValues) throws Exception;
}
