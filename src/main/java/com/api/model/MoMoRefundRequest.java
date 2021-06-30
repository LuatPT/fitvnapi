package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoMoRefundRequest {
	private String partnerCode;
	private String accessKey;
	private String requestId;
	private String amount;
	private String orderId;
    private String transId;
    private String requestType;
    private String signature;
}
