package com.api.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
