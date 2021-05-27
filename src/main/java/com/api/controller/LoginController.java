package com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.User;
import com.api.model.CustomUserDetail;
import com.api.security.jwt.JwtTokenProvider;
import com.api.security.payload.LoginRequest;
import com.api.security.payload.LoginResponse;
import com.api.service.UserService;

@CrossOrigin(origins = "http://fitvn.herokuapp.com")
@RestController
@RequestMapping(value="/v1")
public class LoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	UserService userService;
	
	@PostMapping( "/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Authentication with username and password
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( loginRequest.getUsername(), loginRequest.getPassword() ));
		// If ok then
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		//create jwt
		String jwt = tokenProvider.generateToken((CustomUserDetail) auth.getPrincipal());
		
		//create response and put jwt 
		return new LoginResponse(jwt);
	}
	
	@PostMapping( "/register")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody User user) {
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.registerUser(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	
}
