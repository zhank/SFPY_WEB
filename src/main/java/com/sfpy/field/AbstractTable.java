package com.sfpy.field;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ����Ļ����࣬�����Զ����� Field[] ALL_FIELDS ����
 * 
 * @author sunkn
 *
 */
public class AbstractTable{

	/**
	 * �÷���ֻ������ʹ�ø÷���֮ǰ�Ѿ���ʼ���� {@link Field.common.field.Field}����
	 * 
	 * @return
	 */
	public List<Field> getListTableFields() {
		return getListTableFields(new String[]{});
	}
	
	/**
	 * ��ȡ��ǰ�ඨ���Field����
	 * 
	 * @param excludes
	 *            �ų�����Ҫ��ӵ�
	 * @return
	 */
	public List<Field> getListTableFields(String... excludes) {
		List<Field> listFields = new ArrayList<Field>();
		try {
			java.lang.reflect.Field[] declaredFields = this.getClass()
					.getDeclaredFields();
			for (java.lang.reflect.Field field : declaredFields) {

				if (field.getType().equals(Field.class)) {
					Field fieldInstance = (Field) field.get(this);
					if (fieldInstance == null) {
						// Field��δ��ʼ���� ������List
					} else {
						if (excludes != null
								&& Arrays.binarySearch(excludes,
										fieldInstance.name) > -1) {
							continue;
						}
						listFields.add(fieldInstance);
					}
				}
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return listFields;
	}

	/**
	 * 
	 * �÷���ֻ������ʹ�ø÷���֮ǰ�Ѿ���ʼ���� {@link Field.common.field.Field}����
	 * 
	 * @return
	 */
	public Field[] getArrayTableFields() {
		List<Field> listTableFields = getListTableFields();
		return listTableFields.toArray(new Field[listTableFields.size()]);
	}
	
	public Field[] getArrayTableFields(String... excludes) {
		List<Field> listTableFields = getListTableFields(excludes);
		return listTableFields.toArray(new Field[listTableFields.size()]);
	}
}
