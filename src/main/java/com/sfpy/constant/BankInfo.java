package com.sfpy.constant;

public enum BankInfo {
	BC(10, "中国银行"), 
	ICBC(20, "中国工商银行"),
	CBC(30, "中国建设银行"),
	ABC(40, "中国农业银行"),
	UNKNOWN(50, "未知地区");
	
	
	public int id;
	public String desc;
	
	private BankInfo(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public static BankInfo valueOf(int i) {
		BankInfo bank = null;
		for (BankInfo t : BankInfo.values()) {
			if (t.getId() == i) {
				bank = t;
				break;
			}
		}
		if (bank == null) {   
			bank = UNKNOWN;
		}
		return bank;
	}
	
	/**
	 * 获取银行名字
	 * @param id
	 * @return
	 */
	public static String getBankName(int id) {
		BankInfo bankInfo = BankInfo.valueOf(id);
		return bankInfo.getDesc();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

}
