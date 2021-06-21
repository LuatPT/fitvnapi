package com.api.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.api.common.Config;
import com.api.model.VNPay;

@Repository
@Transactional
public class PaymentRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public String paymentWithVnPayMethod (VNPay vnPay, HttpServletRequest request) {
		
		String bank_code = vnPay.getVnp_BankCode();
		String locate = vnPay.getVnp_Locale();
		
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", "2.0.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnPay.getVnp_Amount() * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        // Set bank code
        if (bank_code != null && bank_code.isEmpty()) {
        	vnp_Params.put("vnp_BankCode", bank_code);
		}
        vnp_Params.put("vnp_TxnRef", Config.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", vnPay.getVnp_OrderInfo());
        vnp_Params.put("vnp_OrderType", vnPay.getVnp_OrderType());
        
        //Set language
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));
        
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        
		return buildQueryString(vnp_Params);
		
	}
	public static String buildQueryString (Map<String, String> vnp_Params) {
		//Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                try {
                	//Build query
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
        
        //System.out.println("HashData=" + hashData.toString());
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        
        return Config.vnp_PayUrl + "?" + queryUrl;
	}
}
