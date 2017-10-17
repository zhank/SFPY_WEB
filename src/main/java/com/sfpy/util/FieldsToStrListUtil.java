package com.sfpy.util;

import java.util.ArrayList;
import java.util.List;

import com.sfpy.field.Field;

/**
 * 将Field数组转换为cols[List字符串]。 用于带事务的数据库查询
 * 
 * @author sfpy
 *
 */
public class FieldsToStrListUtil {
	public static List<String> convert(Field[] fields) {
		List<String> cols = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			cols.add(field.name);
		}
		return cols;
	}
}
