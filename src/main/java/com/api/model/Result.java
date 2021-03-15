package com.api.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Result {
	private Object data;
	private String message;
	public Result() {
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
