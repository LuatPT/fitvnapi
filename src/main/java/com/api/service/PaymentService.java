package com.api.service;

import javax.servlet.http.HttpServletRequest;

import com.api.model.VNPay;

public interface PaymentService {
	public String paymentWithVNPay(VNPay vnPay, HttpServletRequest request);
}
