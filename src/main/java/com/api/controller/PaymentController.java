package com.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.ResponseCheckout;
import com.api.model.VNPay;
import com.api.service.PaymentService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value = "/v1")
public class PaymentController {

	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/paymentVNPay", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentVNPay(@RequestBody VNPay vnPay, HttpServletRequest request ) {
		String paymentUrl = paymentService.paymentWithVNPay(vnPay,request);
		return new ResponseEntity<String>(paymentUrl,HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/saveInfoVnPay", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCheckout> saveInfoVnPay(HttpServletRequest request) {
		ResponseCheckout res = paymentService.saveInfoVnPayToDB(request);
		return new ResponseEntity<ResponseCheckout>(res,HttpStatus.OK);
	}
}