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
import com.api.model.MoMoRefundRequestFromClient;
import com.api.model.MoMoRefundResponse;
import com.api.model.MoMoRequestFromClient;
import com.api.model.MoMoResponse;
import com.api.model.VNPay;
import com.api.service.PaymentService;

@RestController
@CrossOrigin(origins = "http://fitvn.herokuapp.com, http://localhost:3000, https://fitvn.herokuapp.com")
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
	@RequestMapping(method = RequestMethod.POST, value = "/paymentMoMo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MoMoResponse> callMoMoApi(@RequestBody MoMoRequestFromClient moMoRequestFromClient, HttpServletRequest request ) {
		MoMoResponse responseMoMo = paymentService.getDataFromMoMo(moMoRequestFromClient);
		return new ResponseEntity<MoMoResponse>(responseMoMo ,HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/saveInfoMoMo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveInfoMoMo(HttpServletRequest request) {
		String res = paymentService.saveInfoMoMoToDB(request);
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/refundMoMo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MoMoRefundResponse> getRefundMoMo(@RequestBody MoMoRefundRequestFromClient moMoRefundRequestFromClient, HttpServletRequest request) {
		MoMoRefundResponse res = paymentService.getRefundMoMo(moMoRefundRequestFromClient);
		return new ResponseEntity<MoMoRefundResponse>(res,HttpStatus.OK);
	}
}