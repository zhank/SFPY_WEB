package com.sfpy.util;

import java.util.ArrayList;
import java.util.List;

import com.sfpy.field.Field;

/**
 * ��Field����ת��Ϊcols[List�ַ���]�� ���ڴ���������ݿ��ѯ
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
