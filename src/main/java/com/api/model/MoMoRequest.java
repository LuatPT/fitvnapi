package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoMoRequest {
	private String partnerCode;
	private String orderId;
	private String orderInfo;
	private String accessKey;
	private String amount;
	private String signature;
	private String extraData;
	private String requestId;

	private String notifyUrl;
	private String returnUrl;
	private String requestType;

	@Override
	public String toString() {
		return "Request {" + "partnerCode='" + partnerCode + '\'' + ", orderId='" + orderId + '\'' + ", orderInfo='"
				+ orderInfo + '\'' + ", accessKey='" + accessKey + '\'' + ", amount='" + amount + '\'' + ", signature='"
				+ signature + '\'' + ", extraData='" + extraData + '\'' + ", requestId='" + requestId + '\''
				+ ", notifyUrl='" + notifyUrl + '\'' + ", returnUrl='" + returnUrl + '\'' + ", requestType='"
				+ requestType + '\'' + '}';
	}
}
