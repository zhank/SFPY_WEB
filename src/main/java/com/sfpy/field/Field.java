package com.sfpy.field;


import java.io.Serializable;
import java.sql.Types;

import com.sfpy.type.ValidateType;

/**
 * Field����ȷ�����Ե����ݵĶ��壬���԰��������ݵ��ڲ���ʶ��name�������ͣ�type����������ʾ���ƣ�caption�����Ƿ�ǿգ�notNull���ȡ�
 * 
 * <p>
 * Field����������������������ݿ��е��ֶΣ�������name���ֶ�������caption��������ʾ�����ƣ�������VCL�е�caption����notnull���Ƿ�ǿգ���
 * �Լ�Type���������ͣ������ݿ����͵���չ�������ַ������������ʼ���IP��ַ���˿ڵȣ����������ò����Լ�һЩ��ʱ����Ҳ��Field����������������
 * �������ڴ������ݿ��ֶΣ���Ӧ���Ǳ�ʾ���ݱ�ʶ(name)����ʾ����(caption)�����ͣ�������֤�����࣬��Щ���Ծ���һͬ���֣�������һ����������
 * 
 * <p>
 * �����Field������һ��ԭ����Ϊ����ϵͳ����һ�����еĵط�ͳһ����ϵͳ�����õ���ȷ���������ͣ������������ֵ�������Щ���ݿ��������ݿ��ֶΣ�
 * Ҳ�����������ļ��е����ݣ���������������ϵͳ��Ӧ��<b>Ψһ</b>�ض��壬�κ���Ҫʹ�õĵط������øö��壬�����ظ����塣
 * 
 * <p>
 * һ����ʹ��ʱ��Ӧ�ð�Field��Ϊ��̬��ľ�̬��Ա���綨��һ��Admin���ݱ���ʱ��
 * 
 * <pre>
 *    public class TbAdmin {
 *        public static final Field SUBJECT_CN = new Field(&quot;��Ա��ʶ&quot;, &quot;SUBJECT_CN&quot;, new StringType(32), true, true);
 *        ...
 *    };
 * </pre>
 * 
 * @author zhangk
 * @version 1.0
 * @since 17
 */
public class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���ڽ�����ʾ�ַ���
	 */
	public final String caption;

	/**
	 * �ڲ�ʹ�õı�ʶ���������ݿ��ֶΣ�Ҳ���ֶ�����
	 */
	public String name;

	/**
	 * �Ƿ�ǿ�
	 */
	public final boolean notNull;

	/**
	 * �Ƿ�Ϊ����
	 */
	public final boolean isPrimaryKey;

	/**
	 * �ֶζ�Ӧ��У������
	 */
	private ValidateType type;
	
	/**
	 * ���캯��
	 * 
	 * @param caption	������ʾ������
	 * @param name		�ڲ����ݱ�ʶ���������ݿ��ֶ����ֶ�����
	 * @param type		����֤����������
	 */
	public Field(String caption, String name, ValidateType type) {
		this(caption, name, type, false, false);
	}

	/**
	 * ���캯��
	 * 
	 * @param caption	������ʾ������
	 * @param name		�ڲ����ݱ�ʶ���������ݿ��ֶ����ֶ�����
	 * @param type		����֤����������
	 * @param notNull	�Ƿ�ǿ�
	 */
	public Field(String caption, String name, ValidateType type, boolean notNull) {
		this(caption, name, type, notNull, false);
	}

	/**
	 * ���캯��
	 * 
	 * @param caption		������ʾ������
	 * @param name			�ڲ����ݱ�ʶ���������ݿ��ֶ����ֶ�����
	 * @param type			����֤����������
	 * @param notNull		�Ƿ�ǿ�
	 * @param isPrimaryKey	�Ƿ�Ϊ������һ�㶨�����ݿ��ʱ�õ�
	 */
	public Field(String caption, String name, ValidateType type, boolean notNull, boolean isPrimaryKey) {
		this.caption = caption;
		this.name = name;
		this.notNull = notNull;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;

		if (type != null) {
			type.setNotNull(notNull);
		}
	}

	/**
	 * ��ȡ��Ӧ����֤����
	 */
	public ValidateType getType() {
		return type;
	}

	/**
	 * ����field�����ͣ���valת��Ϊname=val��sql��ʽ��Ŀǰֻ֧��string��integer���� ��Ϊ�˷���д�򵥵Ĳ�ѯ����
	 * 
	 * @param val ��Ҫת��Ϊsql��������
	 * @return ת�����sql�ַ���
	 */
	public String toSqlEQ(Object val) {
		if (val == null)
			return null;

		switch (getType().getSqlType()) {
		case Types.VARCHAR:
			return name + "='" + val + "'";
		case Types.INTEGER:
		case Types.NUMERIC:
			return name + "=" + val;
		default:
			throw new UnsupportedOperationException("toSqlEQ��֧�ָ�����");
		}
	}
	
	

}

