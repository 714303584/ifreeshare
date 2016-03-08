package com.ifreeshare.entity;

public class Dictionary {
	
	private String  key;
	
	private String displayName;
	
	private int type;
	
	private String parentKey;
	
	private String desc;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Dictionary [key=" + key + ", displayName=" + displayName + ", type=" + type + ", parentKey=" + parentKey
				+ ", desc=" + desc + "]";
	}
	
	

}
