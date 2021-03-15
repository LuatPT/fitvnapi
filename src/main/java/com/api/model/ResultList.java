package com.api.model;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResultList {

	private List<?> data;
	private String message;
	
	public ResultList() {
	}
	
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
