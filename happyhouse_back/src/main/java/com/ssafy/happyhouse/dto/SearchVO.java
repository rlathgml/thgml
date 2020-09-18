package com.ssafy.happyhouse.dto;

public class SearchVO {
	private int page = 1;
	private int range = 1;
	private String keyword = "apt";
	private String word;
	public SearchVO() {
		super();
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public String toString() {
		return "SearchVO [page=" + page + ", range=" + range + ", keyword=" + keyword + ", word=" + word + "]";
	}
	
	
}
