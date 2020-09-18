package com.ssafy.happyhouse.dto;

import java.util.List;

import com.ssafy.happyhouse.util.Search;

public class Result {
	private List<HouseDeal> list;
	private Search search;
	public Result() {
		super();
	}
	public List<HouseDeal> getList() {
		return list;
	}
	public void setList(List<HouseDeal> list) {
		this.list = list;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	
	
}
