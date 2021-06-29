package com.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoMoResponse {

	private String requestId;
    private int errorCode;
    private String orderId;
    private String message;
    private String localMessage;
    private String requestType;
    private String payUrl;
    
    private String signature;
    private String qrCodeUrl;
    private String deeplink;
    private String deeplinkWebInApp;
    
    
    
    public MoMoResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String payUrl, String deeplink, String deeplinkWebInApp, String qrCodeUrl) {
    	this.payUrl = payUrl;
        this.deeplink = deeplink;
        this.deeplinkWebInApp = deeplinkWebInApp;
        this.qrCodeUrl = qrCodeUrl;
    }


	public MoMoResponse(String requestId, int errorCode, String orderId, String message, String localMessage,
			String requestType, String payUrl, String signature, String qrCodeUrl, String deeplink,
			String deeplinkWebInApp) {
		super();
		this.requestId = requestId;
		this.errorCode = errorCode;
		this.orderId = orderId;
		this.message = message;
		this.localMessage = localMessage;
		this.requestType = requestType;
		this.payUrl = payUrl;
		this.signature = signature;
		this.qrCodeUrl = qrCodeUrl;
		this.deeplink = deeplink;
		this.deeplinkWebInApp = deeplinkWebInApp;
	}


	public MoMoResponse() {
	}

}
