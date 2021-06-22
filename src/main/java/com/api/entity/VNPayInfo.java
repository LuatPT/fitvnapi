package com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="vnPayInfo")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VNPayInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vnPay_id")
	private int vnPay_id ;
	
	@Column(name="vnp_Amount")
	private String vnp_Amount ; // amount of money to pay
	
	@Column(name="vnp_BankCode")
	private String vnp_BankCode;// code of bank
	
	@Column(name="vnp_BankTranNo")
	private String vnp_BankTranNo;
	
	@Column(name="vnp_CardType")
	private String vnp_CardType;
	
	@Column(name="vnp_PayDate")
	private String vnp_PayDate; // Date of pay
	
	@Column(name="vnp_ResponseCode")
	private String vnp_ResponseCode; //Ex: 00
	
	@Column(name="vnp_TmnCode")
	private String vnp_TmnCode; //code of website in vnpay ex: 2QXUI4J4
	
	@Column(name="vnp_TransactionNo")
	private String vnp_TransactionNo; //no of transaction
	
	@Column(name="vnp_TxnRef")
	private String vnp_TxnRef; // Code per transaction
	
	@Column(name="vnp_SecureHashType")
	private String vnp_SecureHashType; //Hash type ex: MD5,SHA256
	
	@Column(name="vnp_SecureHash")
	private String vnp_SecureHash; // Code to check keep data dont change when change to VN pay
}
