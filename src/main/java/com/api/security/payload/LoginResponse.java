package com.api.security.payload;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {

	private String accessToken;
	
	private String tokenType = "Bearer";
	
	private String refreshToken;
	
	private Long id;
	
	private String username;
	
	private String email;
	
	private List<String> roles;

	public LoginResponse(String accessToken, String tokenType, String refreshToken, Long id, String username,
			String email, List<String> roles) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	
	public LoginResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public LoginResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
}
