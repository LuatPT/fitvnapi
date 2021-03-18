package com.api.security.payload;

import lombok.Data;

import com.sun.istack.NotNull;

@Data
public class LoginRequest {
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	
}
