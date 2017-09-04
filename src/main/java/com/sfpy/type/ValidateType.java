package com.sfpy.type;

import java.io.Serializable;
import java.util.Map;

public abstract class ValidateType implements Serializable{
		
	private static final long serialVersionUID = 1L;

	// fValidateУ�����ʱʹ�õ���ʾ��Ϣ,��������ø��ֶ�,��ʹ��Ĭ�ϵ���ʾ��Ϣ
	protected String message = null;

	// �Ƿ�����Ϊ�յı�־
	protected boolean notNull = false;

	// �����������󳤶�
	protected int maxLength = 65535;

	/**
	 * ȱʡ���캯��
	 * 
	 */
	public ValidateType() {
		super();
	}

	public ValidateType(String message) {
		super();
		this.message = message;
	}

	/**
	 * ��ȡ��ǰ���͵����ֱ�ʶ����ֵ��java.sql.Type�ж��������
	 * 
	 * @return
	 */
	abstract public int getSqlType();

	/**
	 * ��ȡ��֤���ԣ��������ظ÷������ؾ����У����룬Ŀǰ�ͻ���ʹ��jQuery Validate���������඼���js�����
	 * 
	 * @return
	 */
	public Map<String, String> getValidateAttrs() {
		return null;
	}

	/**
	 * ��ȡ�ͻ���У����룬�������ظ÷������ؾ����У����룬Ŀǰ�ͻ���ʹ��fValidate��һ��js�⣩���������඼���js�����
	 * 
	 * @param notNull
	 *            �Ƿ�ǿ�
	 * @return У�����
	 */
	abstract public String getValidateCode(boolean notNull);

	/**
	 * ��ȡ��fValidateУ�����ʱʹ�õ���ʾ��Ϣ
	 * 
	 * @return
	 */
	public String getValideteMsg() {
		return message;
	}

	/**
	 * ��ȡ�Ƿ�����Ϊ��
	 * 
	 * @return ������Ϊ��ʱ����true,���򷵻�false
	 */
	public boolean notNull() {
		return notNull;
	}

	/**
	 * �����Ƿ�����Ϊ��
	 * 
	 * @param ������Ϊ��ʱ��Ϊtrue,������Ϊfalse
	 * @return
	 */

	public final ValidateType setNotNull(boolean notNull) {
		this.notNull = notNull;
		return this;
	}

	/**
	 * ���������������󳤶�
	 */

	public final ValidateType setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		return this;
	}

	/**
	 * ��ȡ�����������󳤶�
	 */
	public int getMaxLength() {
		return maxLength;
	}
}
