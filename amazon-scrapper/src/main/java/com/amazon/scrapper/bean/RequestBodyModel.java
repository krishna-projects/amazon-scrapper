package com.amazon.scrapper.bean;

public class RequestBodyModel {
	private String keyWord;
	private String page;
	private String isIndia;
	private String start;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getIsIndia() {
		return isIndia;
	}

	public void setIsIndia(String isIndia) {
		this.isIndia = isIndia;
	}

}
