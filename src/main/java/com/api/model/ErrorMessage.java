package com.api.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

	private int httpStatus;
	private Date refreshTokenTime;
	private String message;
	private String description;
}
