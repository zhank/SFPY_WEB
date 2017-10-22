package com.sfpy.constant;

public enum ResultStatus {
	SUCCESS(10, "返回成功"), FAIL(-1, "返回失败");

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
