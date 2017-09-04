package com.sfpy.type;

import java.util.HashMap;
import java.util.Map;

public class DateType extends ValidateType {

	private static final long serialVersionUID = 1L;

	private static final Map<String, String> VAL_ATTR_MAP = new HashMap<String, String>();

	static {
		VAL_ATTR_MAP.put("type", "dateISO");
		// ��������ͨ���ؼ�ѡ������
		VAL_ATTR_MAP.put("readonly", "true");
	}

	// ���ڵĸ�ʽ
	// mm/dd/yyyy (US et al date format)
	// dd/mm/yyyy (European date format)
	// yyyy/mm/dd (Universal date format)
	private static final String DEF_FMT = "yyyy/mm/dd";

	private String formatString = null;

	// ���ڷָ��
	private String delimeter = "-";

	// У�����
	private String vcode = null;

	public DateType() {
		this(DEF_FMT);
		updateCode();
	}

	public DateType(String formatString) {
		super();
		this.formatString = formatString;
		updateCode();
	}

	public DateType(String formatString, String delimeter) {
		super();
		this.formatString = formatString;
		this.delimeter = delimeter;
		updateCode();
	}

	public DateType(String formatString, String delimeter, String message) {
		super(message);
		this.delimeter = delimeter;
		this.formatString = formatString;
		updateCode();
	}

	@Override
	public int getSqlType() {
		return java.sql.Types.DATE;
	}

	/**
	 * ��ȡfValidateʹ�õ�У����
	 * 
	 * @return
	 */
	@Override
	public String getValidateCode(boolean notNull) {
		/*
		 * ��Ӧ��������ʱ��֤�����Կ����ڹ���ʱ��֤����ϧҪ�׳��쳣 if(!formatString.equals("mm/dd/yyyy") &&
		 * !formatString.equals("dd/mm/yyyy") &&
		 * !formatString.equals("yyyy/mm/dd")) { return null; }
		 */

		return notNull ? vcode : vcode + "|bok";
	}

	private void updateCode() {
		vcode = "date|" + formatString + "|" + delimeter;
	}

	@Override
	public Map<String, String> getValidateAttrs() {
		Map<String, String> valAttrMap = new HashMap<String, String>();
		valAttrMap.put("class", "dateISO");
		valAttrMap.put("readonly", "true");
		// TODO
		if (formatString != null) {
			valAttrMap.put("pattern", formatString);
		}
		return VAL_ATTR_MAP;
	}
}
