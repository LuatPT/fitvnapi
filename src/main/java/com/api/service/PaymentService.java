package com.api.service;

import javax.servlet.http.HttpServletRequest;

import com.api.common.ResponseCheckout;
import com.api.model.MoMoRequestFromClient;
import com.api.model.MoMoResponse;
import com.api.model.VNPay;

public interface PaymentService {
	public String paymentWithVNPay(VNPay vnPay, HttpServletRequest request);

	public ResponseCheckout saveInfoVnPayToDB(HttpServletRequest req);

	public MoMoResponse getDataFromMoMo(MoMoRequestFromClient moMoRequestFromClient);
}
