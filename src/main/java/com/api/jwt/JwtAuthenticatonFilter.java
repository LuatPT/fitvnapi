package com.api.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.payload.LoginRequest;
import com.api.payload.LoginResponse;
import com.api.service.UserService;

import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;

@Slf4j
public class JwtAuthenticatonFilter extends OncePerRequestFilter{
	@Autowired 
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	private String getJwtFromRequest (HttpServletRequest request) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		//Get header with Authorization
		String bearerToken = request.getHeader("Authorization");
		Class c = Class.forName("com.api.payload.LoginResponse");
		Field tokenType = c.getDeclaredField("tokenType");
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenType.toString())) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	
}
