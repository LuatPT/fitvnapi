package com.api.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.ResponseCheckout;
import com.api.model.MoMoRequestFromClient;
import com.api.model.MoMoResponse;
import com.api.model.VNPay;
import com.api.repository.PaymentRepository;
import com.api.service.PaymentService;;

@Service
public class PaymentServiceImpl implements PaymentService{

	private PaymentRepository paymentRepository;
	
	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		super();
		this.paymentRepository = paymentRepository;
	}

	public String paymentWithVNPay(VNPay vnPay, HttpServletRequest request) {
		return paymentRepository.paymentWithVnPayMethod(vnPay, request);
	}

	@Override
	public ResponseCheckout saveInfoVnPayToDB(HttpServletRequest req) {
		return paymentRepository.saveInfoVnPayToDBMethod(req);
	}

	@Override
	public MoMoResponse getDataFromMoMo(MoMoRequestFromClient moMoRequestFromClient) {
		return paymentRepository.getDataFromMoMoWeb(moMoRequestFromClient);
	}	
}
