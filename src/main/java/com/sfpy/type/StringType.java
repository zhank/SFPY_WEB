package com.sfpy.type;

import java.util.HashMap;
import java.util.Map;

public class StringType extends ValidateType {

	private static final long serialVersionUID = 1L;

	// �������С����
	private int minLength = 0;

	// У�����
	private String vcode = null;

	private String vcodeBok = null;

	public StringType() {
		super();
		updateCode();
	}

	public StringType(String message) {
		super(message);
		updateCode();
	}

	public StringType(int maxLength) {
		super();
		this.maxLength = maxLength;
		updateCode();
	}

	public StringType(int minLength, int maxLength) {
		super();
		this.minLength = minLength;
		this.maxLength = maxLength;
		updateCode();
	}

	public StringType(int minLength, int maxLength, String message) {
		super(message);
		this.minLength = minLength;
		this.maxLength = maxLength;
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
		return notNull ? vcode : vcodeBok;
	}

	private void updateCode() {
		if (minLength <= 0) {
			vcode = "blank";
		} else if (minLength > 0 && maxLength > 0 && minLength <= maxLength) {
			vcode = "length|" + minLength + "|" + maxLength;
			vcodeBok = vcode + "|bok";
		} else {
			vcode = "length|1";
		}
	}

	@Override
	public Map<String, String> getValidateAttrs() {
		Map<String, String> valAttrMap = new HashMap<String, String>();
		valAttrMap.put("minlength", Integer.toString(minLength));
		valAttrMap.put("maxlength", Integer.toString(maxLength));

		return valAttrMap;
	}

}
