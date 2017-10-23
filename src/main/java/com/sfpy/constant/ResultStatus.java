package com.sfpy.constant;

public enum ResultStatus {
	SUCCESS(10, "���سɹ�"), FAIL(-1, "����ʧ��");

	int id;
	String desc;

	private ResultStatus(int id, String desc) {
		this.id = id;
		this.desc = desc;
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
