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
@Table(name="momo_info")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MoMoInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="moMo_id")
	private int vnPay_id ;
	
	@Column(name="partnerCode")
	private String partnerCode ; 
	
	@Column(name="requestId")
	private String requestId;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name="orderId")
	private String orderId;
	
	@Column(name="orderInfo")
	private String orderInfo; 
	
	@Column(name="orderType")
	private String orderType;
	
	@Column(name="transId")
	private String transId; 
	
	@Column(name="message")
	private String message; 
	
	@Column(name="localMessage")
	private String localMessage; 
	
	@Column(name="responseTime")
	private String responseTime;
	
	@Column(name="errorCode")
	private String errorCode; 
	
	@Column(name="payType")
	private String payType; 
	
	@Column(name="extraData")
	private String extraData; 
	
	@Column(name="signature")
	private String signature; 
}
