package com.sfpy.constant;

public enum BankInfo {
	BC(10, "�й�����"), 
	ICBC(20, "�й���������"),
	CBC(30, "�й���������"),
	ABC(40, "�й�ũҵ����"),
	UNKNOWN(50, "δ֪����");
	
	
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
	 * ��ȡ��������
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
