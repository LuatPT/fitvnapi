package com.api.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.api.common.Config;
import com.api.common.ResponseCheckout;
import com.api.entity.MoMoInfo;
import com.api.entity.VNPayInfo;
import com.api.model.MoMoRequest;
import com.api.model.MoMoRequestFromClient;
import com.api.model.MoMoResponse;
import com.api.model.VNPay;

@Repository
@Transactional
public class PaymentRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public String paymentWithVnPayMethod(VNPay vnPay, HttpServletRequest request) {

		String bank_code = vnPay.getVnp_BankCode();
		String locate = vnPay.getVnp_Locale();

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", "2");
		vnp_Params.put("vnp_Command", "pay");
		vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(vnPay.getVnp_Amount() * 100));
		vnp_Params.put("vnp_CurrCode", "VND");
		// Set bank code
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bank_code);
		}
		vnp_Params.put("vnp_TxnRef", Config.getRandomNumber(8));
		vnp_Params.put("vnp_OrderInfo", vnPay.getVnp_OrderInfo());
		vnp_Params.put("vnp_OrderType", vnPay.getVnp_OrderType());

		// Set language
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_Merchant", Config.vnp_Merchant);
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));

		vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

		return buildQueryString(vnp_Params);

	}

	public static String buildQueryString(Map<String, String> vnp_Params) {
		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();

		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(fieldValue);
				try {
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret + hashData.toString());

		queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;

		return Config.vnp_PayUrl + "?" + queryUrl;
	}

	public ResponseCheckout saveInfoVnPayToDBMethod(HttpServletRequest req) {
		ResponseCheckout response = ResponseCheckout.OK;

		Map<String, String> fields = new HashMap<String, String>();

		// Check transaction of payment
		for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
			String fieldName = (String) params.nextElement();
			String fieldValue = req.getParameter(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				fields.put(fieldName, fieldValue);
			}
		}
		String vnp_SecureHash = req.getParameter("vnp_SecureHash");

		if (fields.containsKey("vnp_SecureHashType")) {
			fields.remove("vnp_SecureHashType");
		}
		if (fields.containsKey("vnp_SecureHash")) {
			fields.remove("vnp_SecureHash");
		}
		String signValue = null;
		try {
			signValue = Config.hashAllFields(fields);
		} catch (UnsupportedEncodingException e) {
			response = ResponseCheckout.INVALID;
		}

		// Check signature and order status
		if (signValue.equals(vnp_SecureHash)) {
			boolean checkOrderStatus = true;
			if (checkOrderStatus) {
				if ("00".equals(req.getParameter("vnp_ResponseCode"))) {
					// Save to DB
					saveToDBVnPay(req);
				} else {
					// Not success
					response = ResponseCheckout.NOTOK;
				}
			} else {
				// Duplicate existed => res 02
				response = ResponseCheckout.EXISTED;
			}
		} else {
			response = ResponseCheckout.INVALID;
		}

		return response;

	}

	public void saveToDBVnPay(HttpServletRequest req) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		// Save to db
		VNPayInfo obj = new VNPayInfo();
		obj.setVnp_Amount(req.getParameter("vnp_Amount"));
		obj.setVnp_BankCode(req.getParameter("vnp_BankCode"));
		obj.setVnp_BankTranNo(req.getParameter("vnp_BankTranNo"));
		obj.setVnp_CardType(req.getParameter("vnp_CardType"));
		obj.setVnp_PayDate(req.getParameter("vnp_PayDate"));
		obj.setVnp_ResponseCode(req.getParameter("vnp_ResponseCode"));
		obj.setVnp_TmnCode(req.getParameter("vnp_TmnCode"));
		obj.setVnp_TransactionNo(req.getParameter("vnp_TransactionNo"));
		obj.setVnp_TxnRef(req.getParameter("vnp_TxnRef"));
		obj.setVnp_SecureHashType(req.getParameter("vnp_SecureHashType"));
		obj.setVnp_SecureHash(req.getParameter("vnp_SecureHash"));
		try {
			tx.begin();
			entityManager.persist(obj);
			tx.commit();
		} catch (RuntimeException e1) {
			tx.rollback();
		} finally {
			entityManager.close();
		}
	}

	public MoMoResponse getDataFromMoMoWeb(MoMoRequestFromClient moMoRequestFromClient) {
		RestTemplate restTemplate = new RestTemplate();	
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = String.valueOf(System.currentTimeMillis());
		
		String signature = Config.createPaymentCreationRequest(orderId, requestId, moMoRequestFromClient.getAmount(), moMoRequestFromClient.getOrderInfo(), 
				Config.momo_ReturnURL, Config.momo_NotifyURL, Config.momo_ExtraData, Config.momo_PartnerCode, Config.momo_AccessKey, Config.momo_SecretKey);
		
		MoMoRequest requestMoMo = new MoMoRequest(Config.momo_PartnerCode, orderId, moMoRequestFromClient.getOrderInfo(), Config.momo_AccessKey, moMoRequestFromClient.getAmount(),
				signature, Config.momo_ExtraData, requestId, Config.momo_NotifyURL, Config.momo_ReturnURL, moMoRequestFromClient.getRequestType());
			
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MoMoRequest> request = new HttpEntity<MoMoRequest>(requestMoMo, headers);
		MoMoResponse response = restTemplate.postForObject(Config.momo_EndPoint, request, MoMoResponse.class);
		
		return response;
	}

	public ResponseCheckout saveInfoMoMoToDBMethod(HttpServletRequest req) {
		ResponseCheckout response = ResponseCheckout.OK;
		System.out.println(req.getParameter("errorCode"));
		if(!("0").equals(req.getParameter("errorCode"))) {
			response = ResponseCheckout.NOTOK;
		}else {
			saveToDBMoMo(req);
		}
		return response;
	}
	
	public void saveToDBMoMo(HttpServletRequest req) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		// Save to db
		MoMoInfo obj = new MoMoInfo();
		obj.setPartnerCode(req.getParameter("partnerCode"));
		obj.setRequestId(req.getParameter("requestId"));
		obj.setAmount(req.getParameter("amount"));
		obj.setOrderId(req.getParameter("orderId"));
		obj.setOrderInfo(req.getParameter("orderInfo"));
		obj.setOrderType(req.getParameter("orderType"));
		obj.setTransId(req.getParameter("transId"));
		obj.setMessage(req.getParameter("message"));
		obj.setLocalMessage(req.getParameter("localMessage"));
		obj.setResponseTime(req.getParameter("responseTime"));
		obj.setErrorCode(req.getParameter("errorCode"));
		obj.setPayType(req.getParameter("payType"));
		obj.setExtraData(req.getParameter("extraData"));
		obj.setSignature(req.getParameter("signature"));
		try {
			tx.begin();
			entityManager.persist(obj);
			tx.commit();
		} catch (RuntimeException e1) {
			tx.rollback();
		} finally {
			entityManager.close();
		}
	}
}
