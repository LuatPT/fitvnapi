package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VNPay {
	private int vnp_Amount ; // amount of money to pay
	private String vnp_BankCode;	// code of bank
	private String vnp_Locale; //Language ex:vn, en 
	private String vnp_OrderInfo; // Message
	private String vnp_OrderType; // Code of catalogue
}
