package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VNPay {
//	private String vnp_Version; //version of api
//	private String vnp_Command; //code of api
//	private String vnp_TmnCode; //code of website in vnpay ex: 2QXUI4J4
//	private String vnp_CreateDate; 	//create date
//	private String vnp_CurrCode; //current money ex: VND
//	private String vnp_IpAddr; // ip of customer
//	private String vnp_ReturnUrl; // url return
//	private String vnp_TxnRef; // Code per transaction
//	private String vnp_SecureHashType; //Hash type ex: MD5,SHA256
//	private String vnp_SecureHash; // Code to check keep data dont change when change to VN pay
//	private String vnp_TransactionNo; //no of transaction
//	private String vnp_hashSecret;
	
	private int vnp_Amount ; // amount of money to pay
	private String vnp_BankCode;	// code of bank
	private String vnp_Locale; //Language ex:vn, en 
	private String vnp_OrderInfo; // Message
	private String vnp_OrderType; // Code of catalogue
}
