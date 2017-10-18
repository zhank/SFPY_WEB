package com.sfpy.type;

import java.util.HashMap;
import java.util.Map;

public class NumberStringType extends ValidateType {

	private static final long serialVersionUID = 1L;

	// ��С����
	private int minLength = 0;

	// У�����
	private String vcode = null;

	public NumberStringType() {
		super();
		updateCode();
	}

	public NumberStringType(int maxLength) {
		super();
		this.maxLength = maxLength;
		updateCode();
	}

	public NumberStringType(int minLength, int maxLength) {
		super();
		this.minLength = minLength;
		this.maxLength = (maxLength < minLength) ? minLength : maxLength;
		updateCode();
	}

	public NumberStringType(int minLength, int maxLength, String message) {
		super(message);
		this.minLength = minLength;
		this.maxLength = (maxLength < minLength) ? minLength : maxLength;
		updateCode();
	}

	@Override
	public int getSqlType() {
		return java.sql.Types.VARCHAR;
	}

	/**
	 * ��ȡfValidateʹ�õ�У����
	 * 
	 * @return
	 */
	@Override
	public String getValidateCode(boolean notNull) {
		return notNull ? vcode : vcode + "|bok";
	}

	@Override
	public Map<String, String> getValidateAttrs() {
		Map<String, String> valAttrMap = new HashMap<String, String>();
		valAttrMap.put("type", "numeric");
		if(minLength > 0) {
			valAttrMap.put("minLength", Integer.toString(minLength));
		}
		return valAttrMap;
	}
	
	private void updateCode() {
		StringBuffer code = new StringBuffer(16);
		code.append("numeric");
		if (minLength > 0) {
			code.append("|" + minLength);
		}
		vcode = code.toString();
	}

}
