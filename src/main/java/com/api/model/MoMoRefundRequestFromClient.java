package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoMoRefundRequestFromClient {
	private String transId;
	private String amount;
	private String requestType;
}
