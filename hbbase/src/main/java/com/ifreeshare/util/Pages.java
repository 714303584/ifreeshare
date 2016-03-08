package com.ifreeshare.util;

import java.util.List;

public class Pages<T> {
	
	private int pageSize;
	
	private List<T> list ;
	
	private String startRow;
	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getStartRow() {
		return startRow;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	@Override
	public String toString() {
		return "Pages [pageSize=" + pageSize + ", list=" + list + ", startRow=" + startRow + "]";
	}

	
	

}
