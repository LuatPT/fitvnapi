package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoMoRequestFromClient {
	private String orderInfo;
	private String amount;
	private String requestType;
}
