package com.sfpy.db;

import java.sql.Connection;
import java.util.Map;

import com.sfpy.field.Field;

/**
 * 数据库操作DAO基础接口
 * @author sfpy
 *
 */
public interface IBaseTbDao extends IBaseViewDao {
	
	/**
	 * 添加数据
	 * @param dataMap 数据Map
	 * @throws Exception
	 */
	public void insert(Map<String, Object> dataMap) throws Exception;
	
	/**
	 * 带事务：添加数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param dataMap 数据Map
	 * @throws Exception
	 */
	public void insertWithConn(Connection conn, Map<String, Object> dataMap) throws Exception;
	
	/**
	 * 根据主键更新数据
	 * @param updateField 更新字段Field
	 * @param updateValue 更新值
	 * @param id 主键ID
	 * @throws Exception
	 */
	public void update(Field updateField, Object updateValue, String condition) throws Exception;
	
	/**
	 * 带事务：根据主键更新数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param updateField 更新字段Field
	 * @param updateValue 更新值
	 * @param id 主键ID
	 * @throws Exception
	 */
	public void updateWithConn(Connection conn, Field updateField, Object updateValue, String condition) throws Exception;
	
	/**
	 * 更新数据
	 * @param id 主键ID
	 * @param dataMap 更新的Map
	 * @throws Exception
	 */
	public void update(Map<String, Object> dataMap, String condition) throws Exception;
	
	/**
	 * 带事务：更新数据
	 * @param conn 数据库连接，谁创建，谁关闭
	 * @param id 主键ID
	 * @param dataMap 更新的Map
	 * @throws Exception
	 */
	public void updateWithConn(Connection conn, Map<String, Object> dataMap, String condition) throws Exception;

}
